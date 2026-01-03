package com.nikahtech.muslimnikah.Backend.dto.response;

import com.nikahtech.muslimnikah.Backend.dto.eto.UserDto;

public class AuthResponse {
    private UserDto user;
    private String accessToken;
    private String refreshToken;
    private String message;

    public UserDto getUser() {
        return user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "user=" + user.toString() +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
