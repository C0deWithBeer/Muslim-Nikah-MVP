package com.nikahtech.muslimnikah.Backend.Api;

import com.nikahtech.muslimnikah.Backend.ApiResponse;
import com.nikahtech.muslimnikah.Backend.dto.request.LoginRequest;
import com.nikahtech.muslimnikah.Backend.dto.request.OtpVerifyRequest;
import com.nikahtech.muslimnikah.Backend.dto.request.RefreshAccessTokenRequest;
import com.nikahtech.muslimnikah.Backend.dto.response.AuthResponse;
import com.nikahtech.muslimnikah.Backend.dto.response.JwtTokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("api/auth/login")
    Call<ApiResponse<AuthResponse>> login(
            @Body LoginRequest request
    );

    // ---------------- VERIFY OTP ----------------
    @POST("api/auth/verify-otp")
    Call<ApiResponse<AuthResponse>> verifyOtp(
            @Body OtpVerifyRequest request
    );

    // ---------------- REFRESH TOKEN ----------------
    @POST("api/auth/refresh-token")
    Call<ApiResponse<JwtTokenResponse>> refreshToken(
            @Body RefreshAccessTokenRequest request
    );

}
