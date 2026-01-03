package com.nikahtech.muslimnikah.Backend.dto.response;

public class JwtTokenResponse {
    private String accessToken;
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}