package com.nikahtech.muslimnikah.Backend.dto.request;

import com.nikahtech.muslimnikah.Backend.dto.pojo.DeviceInfo;

public class OtpVerifyRequest {
    private String phoneNumber;
    private String countryCode;
    private String otp;
    private DeviceInfo deviceInfo;

    public OtpVerifyRequest(String phoneNumber, String countryCode, String otp, DeviceInfo deviceInfo) {
        this.phoneNumber = phoneNumber;
        this.countryCode = countryCode;
        this.otp = otp;
        this.deviceInfo = deviceInfo;
    }
}