package com.nikahtech.muslimnikah.Backend;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.nikahtech.muslimnikah.Backend.enums.AppCode;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ✅ Global, safe, and production-ready Retrofit API caller.
 * Handles all HTTP, network, and backend-level errors consistently.
 */
public class ApiCaller {

    private static final String TAG = "ApiCaller";

    /**
     * Callback interface for Retrofit API results.
     * @param <T> The type of data inside ApiResponse.
     */
    public interface ApiCallback<T> {
        void onSuccess(ApiResponse<T> response);
        void onError(ApiResponse<T> errorResponse);
    }

    /**
     * Executes a Retrofit call safely with full error protection.
     *
     * @param call Retrofit Call object returning ApiResponse<T>
     * @param callback Callback with onSuccess and onError
     * @param <T> Type of data inside ApiResponse
     */
    public static <T> void call(Call<ApiResponse<T>> call, ApiCallback<T> callback) {
        call.enqueue(new Callback<ApiResponse<T>>() {
            @Override
            public void onResponse(
                    @NonNull Call<ApiResponse<T>> call,
                    @NonNull Response<ApiResponse<T>> response
            ) {
                // ✅ Case 1: HTTP 2xx (Success)
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<T> apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        callback.onSuccess(apiResponse);
                    } else {
                        Log.w(TAG, "⚠️ API responded with success=false: " + apiResponse.getMessage());
                        callback.onError(apiResponse);
                    }
                    return;
                }

                // ❌ Case 2: Non-2xx (400, 401, 404, 500...)
                ApiResponse<T> errorResponse;
                try {
                    if (response.errorBody() != null) {
                        String errorJson = response.errorBody().string();
                        Gson gson = new Gson();
                        //noinspection unchecked
                        errorResponse = gson.fromJson(errorJson, ApiResponse.class);

                        if (errorResponse.getCode() == 0) {
                            errorResponse.setCode(response.code());
                        }

                        if (errorResponse.getAppCode() == null) {
                            errorResponse.setAppCode(AppCode.UNKNOWN_ERROR);
                        }

                    } else {
                        errorResponse = new ApiResponse<>(
                                false,
                                "Unknown server error",
                                response.code(),
                                AppCode.UNKNOWN_ERROR,
                                null
                        );
                    }
                } catch (Exception e) {
                    Log.e(TAG, "❌ Failed to parse error body", e);
                    errorResponse = new ApiResponse<>(
                            false,
                            "Error parsing error response",
                            response.code(),
                            AppCode.UNKNOWN_ERROR,
                            null
                    );
                }

                Log.e(TAG, "HTTP error: " + response.code() + " - " + response.message());
                callback.onError(errorResponse);
            }

            @Override
            public void onFailure(
                    @NonNull Call<ApiResponse<T>> call,
                    @NonNull Throwable t
            ) {
                String message;
                int code = -1;

                if (t instanceof SocketTimeoutException || t instanceof ConnectException) {
                    message = "Server is not responding. Please try again later.";
                } else if (t instanceof UnknownHostException) {
                    message = "Unable to reach the server. Check your internet connection.";
                } else if (t instanceof IOException) {
                    message = "Network error. Please check your connection.";
                } else {
                    message = "Internal error: " + t.getMessage();
                }

                Log.e(TAG, "🚨 API call failed: " + message, t);

                ApiResponse<T> errorResponse = new ApiResponse<>(
                        false,
                        message,
                        code,
                        AppCode.UNKNOWN_ERROR,
                        null
                );

                callback.onError(errorResponse);
            }
        });
    }
}
