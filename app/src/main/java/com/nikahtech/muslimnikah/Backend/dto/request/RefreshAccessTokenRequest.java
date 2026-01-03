package com.nikahtech.muslimnikah.Backend.dto.request;

public class RefreshAccessTokenRequest {
    private String refreshToken;

    public RefreshAccessTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
