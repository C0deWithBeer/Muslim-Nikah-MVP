package com.nikahtech.muslimnikah.Backend;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nikahtech.muslimnikah.Backend.Api.AuthApi;
import com.nikahtech.muslimnikah.Backend.dto.request.RefreshAccessTokenRequest;
import com.nikahtech.muslimnikah.Backend.dto.response.JwtTokenResponse;
import com.nikahtech.muslimnikah.Backend.enums.AppCode;
import com.nikahtech.muslimnikah.Backend.helper.JwtTokenHelper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Global Retrofit client builder with automatic JWT handling and token refresh support.
 */
public class RetrofitClient {

//    private static final String BASE_URL = "http://10.175.174.152:8085/";
    private static final String BASE_URL = "http://10.0.2.2:8085/";

    private static final String TAG = "RetrofitClientLogger";

    private static Retrofit retrofitNoAuth = null;

    // ----------------------------------------------------------------------
    // Public API
    // ----------------------------------------------------------------------

    public static <T> T createServiceWithAuth(Class<T> serviceClass, Context context) {
        Retrofit retrofit = buildRetrofit(context);
        return retrofit.create(serviceClass);
    }

    public static <T> T createService(Class<T> serviceClass) {
        if (retrofitNoAuth == null) {
            retrofitNoAuth = buildRetrofit(null);
        }
        return retrofitNoAuth.create(serviceClass);
    }

    // ----------------------------------------------------------------------
    // Retrofit Builder
    // ----------------------------------------------------------------------

    private static Retrofit buildRetrofit(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Log.d(TAG, message));
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new AndroidLoggingInterceptor());

        if (context != null) {
            clientBuilder.addInterceptor(new AuthInterceptor(context))
                    .authenticator(new TokenAuthenticator(context));
        }

        OkHttpClient client = clientBuilder.build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class,
                        (com.google.gson.JsonSerializer<LocalDate>) (src, typeOfSrc, jsonContext) ->
                                new com.google.gson.JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class,
                        (com.google.gson.JsonDeserializer<LocalDate>) (json, typeOfT, jsonContext) ->
                                LocalDate.parse(json.getAsString()))
                .registerTypeAdapter(LocalDateTime.class,
                        (com.google.gson.JsonSerializer<LocalDateTime>) (src, typeOfSrc, jsonContext) ->
                                new com.google.gson.JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDateTime.class,
                        (com.google.gson.JsonDeserializer<LocalDateTime>) (json, typeOfT, jsonContext) ->
                                LocalDateTime.parse(json.getAsString()))

                .registerTypeAdapter(java.time.Instant.class,
                        (com.google.gson.JsonSerializer<java.time.Instant>) (src, typeOfSrc, jsonContext) ->
                                new com.google.gson.JsonPrimitive(src.toString()))
                .registerTypeAdapter(java.time.Instant.class,
                        (com.google.gson.JsonDeserializer<java.time.Instant>) (json, typeOfT, jsonContext) ->
                                java.time.Instant.parse(json.getAsString()))
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    // ----------------------------------------------------------------------
    // Interceptors
    // ----------------------------------------------------------------------

    /** Adds JWT access token to all authorized requests. */
    private static class AuthInterceptor implements Interceptor {
        private final Context context;

        AuthInterceptor(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public Response intercept(Chain chain) throws IOException {
            String accessToken = JwtTokenHelper.getJwtAccessToken(context);
            Request.Builder builder = chain.request().newBuilder();

            if (accessToken != null && !accessToken.isEmpty()) {
                builder.header("Authorization", "Bearer " + accessToken);
                Log.d(TAG, "🔑 Added Authorization header");
            } else {
                Log.d(TAG, "⚠️ No access token found");
            }

            return chain.proceed(builder.build());
        }
    }

    // ----------------------------------------------------------------------
    // Authenticator (Auto Token Refresh)
    // ----------------------------------------------------------------------

    private static class TokenAuthenticator implements Authenticator {
        private final Context context;

        TokenAuthenticator(Context context) {
            this.context = context;
        }

        @Override
        public Request authenticate(Route route, @NonNull Response response) throws IOException {
            String refreshToken = JwtTokenHelper.getJwtRefreshToken(context);

            if (refreshToken == null || responseCount(response) >= 2) {
                Log.d(TAG, "🚨 Refresh token missing or retry limit exceeded");
                sendSessionExpiredBroadcast(
                        AppCode.UNKNOWN_ERROR,
                        "Session expired. Please login again.",
                        401);
                return null;
            }

            Log.d(TAG, "🔄 Attempting to refresh access token...");

            AuthApi authApi = RetrofitClient.createService(AuthApi.class);
            Call<ApiResponse<JwtTokenResponse>> refreshCall =
                    authApi.refreshToken(new RefreshAccessTokenRequest(refreshToken));

            AtomicReference<JwtTokenResponse> newTokens = new AtomicReference<>(null);
            AtomicReference<ApiResponse<JwtTokenResponse>> lastError = new AtomicReference<>(null);
            CountDownLatch latch = new CountDownLatch(1);

            ApiCaller.call(refreshCall, new ApiCaller.ApiCallback<JwtTokenResponse>() {
                @Override
                public void onSuccess(ApiResponse<JwtTokenResponse> response) {
                    if (response.isSuccess() && response.getData() != null) {
                        JwtTokenResponse tokenData = response.getData();
                        newTokens.set(tokenData);
                        JwtTokenHelper.saveJwtTokens(
                                context,
                                tokenData.getAccessToken(),
                                tokenData.getRefreshToken()
                        );
                        Log.d(TAG, "✅ Tokens refreshed successfully");
                    } else {
                        Log.d(TAG, "❌ Token refresh failed: " + response.getMessage());
                        lastError.set(response);
                    }
                    latch.countDown();
                }

                @Override
                public void onError(ApiResponse<JwtTokenResponse> errorResponse) {
                    Log.d(TAG, "❌ Token refresh error: " + errorResponse.getMessage());
                    lastError.set(errorResponse);
                    latch.countDown();
                }
            });

            try {
                latch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            JwtTokenResponse tokens = newTokens.get();
            if (tokens == null || tokens.getAccessToken() == null) {
                ApiResponse<JwtTokenResponse> error = lastError.get();
                if (error != null) {
                    sendSessionExpiredBroadcast(error.getAppCode(), error.getMessage(), error.getCode());
                } else {
                    sendSessionExpiredBroadcast(AppCode.UNKNOWN_ERROR, "Token refresh failed", 401);
                }
                return null;
            }

            return response.request().newBuilder()
                    .header("Authorization", "Bearer " + tokens.getAccessToken())
                    .build();
        }

        private void sendSessionExpiredBroadcast(AppCode appCode, String message, int code) {
            android.content.Intent intent = new android.content.Intent("SESSION_EXPIRED");
            intent.putExtra("appCode", appCode.getCode());
            intent.putExtra("message", message);
            intent.putExtra("code", code);
            context.sendBroadcast(intent);
        }

        private int responseCount(Response response) {
            int count = 1;
            while ((response = response.priorResponse()) != null) count++;
            return count;
        }
    }

    // ----------------------------------------------------------------------
    // Logger
    // ----------------------------------------------------------------------

    private static class AndroidLoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            Log.d(TAG, "➡️ Request: " + request.method() + " " + request.url());
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            Log.d(TAG, String.format(
                    "✅ Response: %s %d in %.1fms",
                    response.request().url(),
                    response.code(),
                    (t2 - t1) / 1e6d
            ));
            return response;
        }
    }
}
