package com.nikahtech.muslimnikah.Backend.dto.request;

public class LoginRequest {

    private String phoneNumber;
    private String countryCode;

    public LoginRequest(String phoneNumber, String countryCode) {
        this.phoneNumber = phoneNumber;
        this.countryCode = countryCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }
}

