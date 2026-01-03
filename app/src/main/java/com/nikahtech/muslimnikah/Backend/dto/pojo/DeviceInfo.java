package com.nikahtech.muslimnikah.Backend.dto.pojo;

import com.nikahtech.muslimnikah.Backend.enums.Platform;

public class DeviceInfo {
    private String deviceName;
    private String deviceId;
    private Platform platform;
    private String fcmToken;

    public DeviceInfo(String deviceName, String deviceId, Platform platform, String fcmToken) {
        this.deviceName = deviceName;
        this.deviceId = deviceId;
        this.platform = platform;
        this.fcmToken = fcmToken;
    }
}
