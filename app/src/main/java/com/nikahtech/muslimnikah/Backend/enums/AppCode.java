package com.nikahtech.muslimnikah.Backend.enums;

/**
 * Central registry of application-level response codes.
 *
 * Code format:
 *   [MODULE]_[ACTION / RESULT]
 *
 * Ranges:
 *  0              → Global success
 *  1000–1999      → Authentication
 *  2000–2999      → User
 *  3000–3999      → JWT / Security
 *  4000–4999      → Profile
 *  5000–5999      → Storage / Media
 *  6000–6999      → Promo
 *  7000–7999      → Payment
 *  8000–8999      → Rate limiting
 *  9000–9999      → System errors
 */
public enum AppCode {

    /* =====================================================
     * GLOBAL
     * ===================================================== */
    SUCCESS(0, "Success"),
    UNKNOWN_ERROR(9999, "Unknown error occurred"),

    /* =====================================================
     * AUTH (1000–1999)
     * ===================================================== */
    AUTH_PHONE_INVALID(1000, "Invalid phone number"),
    AUTH_OTP_SENT(1001, "OTP sent successfully"),
    AUTH_OTP_INVALID(1002, "Invalid OTP"),
    AUTH_OTP_EXPIRED(1003, "OTP expired"),
    AUTH_TOO_MANY_ATTEMPTS(1004, "Too many OTP attempts"),
    AUTH_PHONE_ALREADY_REGISTERED(1005, "Phone number already registered"),
    AUTH_EMAIL_ALREADY_REGISTERED(1006, "Email already registered"),
    AUTH_PENDING_VERIFICATION(1007, "User pending verification"),
    AUTH_PENDING_USER_NOT_FOUND(1008, "Pending user not found"),
    AUTH_INVALID_CREDENTIALS(1009, "Invalid credentials"),
    AUTH_DEVICE_NOT_RECOGNIZED(1010, "New device detected"),
    AUTH_TOKEN_EXPIRED(1011, "Access token expired"),
    AUTH_REFRESH_TOKEN_EXPIRED(1012, "Refresh token expired"),
    AUTH_OTP_DAILY_LIMIT_REACHED(1013, "Daily OTP limit reached"),
    AUTH_OTP_WAIT_FOR_COOLDOWN(1014, "Please wait before requesting another OTP"),
    AUTH_SMS_PROVIDER_ERROR(1015, "Failed to send OTP"),
    AUTH_PASSWORD_INVALID(1016, "Invalid password"),
    AUTH_DEVICE_NOT_FOUND(1017, "Pending device not found"),
    AUTH_OTP_VERIFIED(1018, "OTP verified successfully"),
    AUTH_NO_OTP_SESSION(1019, "No OTP session found"),
    LOGIN_SUCCESS_OTP_REQUIRED(1020, "Login successful, OTP verification required"),
    LOGIN_SUCCESS_NO_OTP(1021, "Login successful"),

    /* =====================================================
     * USER (2000–2999)
     * ===================================================== */
    USER_NOT_FOUND(2001, "User not found"),
    USER_FETCHED(2002, "User fetched successfully"),
    USER_CREATED(2003, "User created successfully"),
    USER_SELECTED_PROFILE_NOT_SET(2004, "Selected profile not set"),

    /* =====================================================
     * JWT / SECURITY (3000–3999)
     * ===================================================== */
    JWT_TOKEN_REFRESHED(3001, "JWT tokens refreshed successfully"),
    JWT_REFRESH_TOKEN_INVALID(3002, "Invalid refresh token"),
    JWT_JTI_MISMATCH(3003, "Token mismatch detected"),

    /* =====================================================
     * PROFILE (4000–4999)
     * ===================================================== */
    PROFILE_CREATED(4000, "Profile created successfully"),
    PROFILE_NOT_FOUND(4001, "Profile not found"),
    PROFILE_INCORRECTLY_LINKED(4002, "Profile incorrectly linked"),
    PROFILES_FETCHED(4003, "Profiles fetched successfully"),
    PROFILE_PHOTO_PRIVACY_UPDATED(4004, "Profile photo privacy updated"),
    OTHER_PHOTOS_PRIVACY_UPDATED(4005, "Other photos privacy updated"),
    PROFILE_PHOTOTYPE_INVALID(4006, "Invalid photo type"),
    PROFILE_PHOTO_UPDATED(4007, "Profile photo updated"),
    PROFILE_BASIC_DETAILS_UPDATED(4008, "Basic details updated"),
    PROFILE_LOCATION_DETAILS_UPDATED(4009, "Location details updated"),
    PROFILE_PHYSICAL_DETAILS_UPDATED(4010, "Physical attributes updated"),
    PROFILE_PROFESSIONAL_DETAILS_UPDATED(4011, "Professional details updated"),
    PROFILE_RELIGIOUS_DETAILS_UPDATED(4012, "Religious details updated"),
    PROFILE_FAMILY_DETAILS_UPDATED(4013, "Family details updated"),
    PROFILE_LIFESTYLE_DETAILS_UPDATED(4014, "Lifestyle details updated"),
    PROFILE_SOCIAL_HANDLES_UPDATED(4015, "Social handles updated"),
    PROFILE_FETCHED(4016, "Profile fetched"),
    FULL_PROFILE_UPDATED(4017, "Profile updated successfully"),

    /* =====================================================
     * STORAGE / MEDIA (5000–5999)
     * ===================================================== */
    PRESIGNED_URL_GENERATED(5000, "Presigned URL generated"),
    OLD_IMAGE_DELETED(5001, "Old image deleted from storage"),

    /* =====================================================
     * PROMO (6000–6999)
     * ===================================================== */
    PROMO_CODE_INVALID(6000, "Invalid promo code"),
    PROMO_CODE_NOT_AVAILABLE(6001, "Promo code not available"),
    PROMO_CODE_INACTIVE(6002, "Promo code inactive"),
    PROMO_CODE_EXPIRED(6003, "Promo code expired"),
    PROMO_CODE_NOT_APPLICABLE(6004, "Promo code not applicable"),
    PROMO_CODE_ALREADY_EXISTS(6005, "Promo code already exists"),
    PROMO_CODE_ALREADY_CLAIMED(6006, "Promo code already claimed"),
    PROMO_CODE_NOT_ACTIVE_YET(6007, "Promo code not active yet"),

    /* =====================================================
     * PAYMENT (7000–7999)
     * ===================================================== */
    TRANSACTION_NOT_FOUND(7000, "Transaction not found"),
    PAYMENT_FAILED(7001, "Payment failed"),
    PAYMENT_ORDER_CREATED(7002, "Payment order created"),
    PAYMENT_ORDER_FAILED(7003, "Payment order creation failed"),
    PAYMENT_CONFIRMED(7004, "Payment confirmed"),
    TRANSACTION_ALREADY_CLAIMED(7005, "Transaction already claimed"),
    SUBSCRIPTION_NOT_FOUND(7006, "Subscription not found"),
    INVALID_TRANSACTION_TYPE(7007, "Invalid transaction type"),
    INVALID_SUBSCRIPTION_PLAN(7008, "Invalid subscription plan"),
    PAYMENT_VERIFICATION_FAILED(7009, "Payment verification failed"),
    PAYMENT_TRANSACTIONS_FETCHED(7010, "Payment transactions fetched"),

    /* =====================================================
     * RATE LIMIT (8000–8999)
     * ===================================================== */
    RATE_LIMIT_EXCEEDED(8000, "Too many requests"),
    OTP_RESEND_LIMIT_EXCEEDED(8001, "OTP resend limit exceeded"),

    /* =====================================================
     * SYSTEM (9000–9999)
     * ===================================================== */
    INTERNAL_SERVER_ERROR(9000, "Internal server error"),
    DATABASE_ERROR(9001, "Database error"),
    REDIS_ERROR(9002, "Redis error"),
    VALIDATION_ERROR(9003, "Validation error");

    private final int code;
    private final String message;

    AppCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
