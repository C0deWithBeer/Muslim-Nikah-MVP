package com.nikahtech.muslimnikah.Backend;

import com.nikahtech.muslimnikah.Backend.enums.AppCode;

import java.time.Instant;

/**
 * ✅ Standard API response model for Android client.
 * Mirrors backend ApiResponse structure for consistent parsing.
 */
public class ApiResponse<T> {

    private boolean success;
    private int code;
    private AppCode appCode;
    private String message;
    private T data;
    private String timestamp;

    /**
     * ✅ Universal constructor for all responses (success or error).
     * Always sets timestamp automatically if not provided.
     */
    public ApiResponse(boolean success, String message, int code, AppCode appCode, T data) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.appCode = appCode;
        this.data = data;
        this.timestamp = String.valueOf(Instant.now());
    }

    // -------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public AppCode getAppCode() {
        return appCode;
    }

    public void setAppCode(AppCode appCode) {
        this.appCode = appCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", code=" + code +
                ", appCode=" + appCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
