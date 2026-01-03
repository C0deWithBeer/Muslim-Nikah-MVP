package com.nikahtech.muslimnikah.activities;

import static com.nikahtech.muslimnikah.Backend.enums.AppCode.*;
import static com.nikahtech.muslimnikah.utils.ToastUtil.AppToastType.ERROR;
import static com.nikahtech.muslimnikah.utils.ToastUtil.AppToastType.SUCCESS;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nikahtech.muslimnikah.Backend.Api.AuthApi;
import com.nikahtech.muslimnikah.Backend.Api.ProfileApi;
import com.nikahtech.muslimnikah.Backend.ApiCaller;
import com.nikahtech.muslimnikah.Backend.ApiResponse;
import com.nikahtech.muslimnikah.Backend.RetrofitClient;
import com.nikahtech.muslimnikah.Backend.dto.eto.ProfileDto;
import com.nikahtech.muslimnikah.Backend.dto.eto.UserDto;
import com.nikahtech.muslimnikah.Backend.dto.pojo.DeviceInfo;
import com.nikahtech.muslimnikah.Backend.dto.request.LoginRequest;
import com.nikahtech.muslimnikah.Backend.dto.request.OtpVerifyRequest;
import com.nikahtech.muslimnikah.Backend.dto.response.AuthResponse;
import com.nikahtech.muslimnikah.Backend.enums.AppCode;
import com.nikahtech.muslimnikah.Backend.enums.Platform;
import com.nikahtech.muslimnikah.Backend.helper.JwtTokenHelper;
import com.nikahtech.muslimnikah.R;
import com.nikahtech.muslimnikah.databinding.ActivityOtpBinding;
import com.nikahtech.muslimnikah.keystores.LocalDBManager;
import com.nikahtech.muslimnikah.models.Profile;
import com.nikahtech.muslimnikah.utils.CursorUtil;
import com.nikahtech.muslimnikah.utils.DeviceInfoUtil;
import com.nikahtech.muslimnikah.utils.FcmTokenUtil;
import com.nikahtech.muslimnikah.utils.JSONUtil;
import com.nikahtech.muslimnikah.utils.PopupDialogUtil;
import com.nikahtech.muslimnikah.utils.SysUtil;
import com.nikahtech.muslimnikah.utils.ToastUtil;

public class OTPActivity extends AppCompatActivity {

    private static final long RESEND_INTERVAL_MS = 60_000L;

    private ActivityOtpBinding binding;

    private AuthApi authApi;
    private ProfileApi profileApi;

    private String phoneNumber;
    private String countryCode;

    private CountDownTimer resendTimer;

    // ------------------------------------------------------------
    // LIFECYCLE
    // ------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        SysUtil.changeStatusBarColor(this, R.color.colorPrimary);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );
                    return insets;
                }
        );

        authApi = RetrofitClient.createService(AuthApi.class);
        profileApi = RetrofitClient.createServiceWithAuth(ProfileApi.class, this);

        readIntent();
        setupUi();
        setupListeners();
        startResendCountdown();
    }

    @Override
    protected void onDestroy() {
        if (resendTimer != null) resendTimer.cancel();
        super.onDestroy();
    }

    // ------------------------------------------------------------
    // INIT
    // ------------------------------------------------------------

    private void readIntent() {
        phoneNumber = getIntent().getStringExtra("PHONE_NUMBER");
        countryCode = getIntent().getStringExtra("COUNTRY_CODE");

        if (phoneNumber == null || countryCode == null) {
            ToastUtil.show(this, "Invalid request", ERROR);
            finish();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setupUi() {
        binding.instTxt.setText(
                "A 6 digit code has been sent to your number\n( " +
                        countryCode + " ) " + phoneNumber
        );

        binding.otpView.requestFocus();

        // Ensure black cursor for all OTP fields
        binding.otpView.postDelayed(() -> {
            for (int i = 0; i < binding.otpView.getChildCount(); i++) {
                View child = binding.otpView.getChildAt(i);
                if (child instanceof EditText) {
                    CursorUtil.apply((EditText) child, R.drawable.cursor);
                }
            }
        }, 50);
    }

    private void setupListeners() {
        binding.submitBtn.setOnClickListener(v -> verifyOtp());
        binding.resendBtn.setOnClickListener(v -> resendOtp());
    }

    // ------------------------------------------------------------
    // OTP VERIFY
    // ------------------------------------------------------------

    private void verifyOtp() {
        String otp = binding.otpView.getOtp().trim();

        if (TextUtils.isEmpty(otp) || otp.length() != 6) {
            showError(
                    "Invalid OTP",
                    "Please enter the 6 digit OTP sent to your number."
            );
            return;
        }

        setVerifyLoading(true);

        FcmTokenUtil.fetchFcmToken((success, token, error) -> {
            if (!success) {
                setVerifyLoading(false);
                showError("Problem", "Unable to verify at the moment. Please try again.");
                return;
            }

            DeviceInfo deviceInfo = new DeviceInfo(
                    DeviceInfoUtil.getDeviceName(),
                    DeviceInfoUtil.getDeviceId(this),
                    Platform.Android,
                    token
            );

            OtpVerifyRequest request = new OtpVerifyRequest(
                    phoneNumber,
                    countryCode,
                    otp,
                    deviceInfo
            );

            ApiCaller.call(authApi.verifyOtp(request), new ApiCaller.ApiCallback<>() {
                @Override
                public void onSuccess(ApiResponse<AuthResponse> response) {
                    setVerifyLoading(false);
                    handleAuthSuccess(response.getData());
                }

                @Override
                public void onError(ApiResponse<AuthResponse> error) {
                    setVerifyLoading(false);
                    handleOtpError(error.getAppCode());
                }
            });
        });
    }

    private void handleOtpError(AppCode code) {
        switch (code) {

            case AUTH_OTP_INVALID:
                showError(
                        "Incorrect OTP",
                        "The OTP you entered is incorrect. Please check the code and try again."
                );
                break;

            case AUTH_OTP_EXPIRED:
                showError(
                        "OTP Expired",
                        "Your OTP has expired. Please request a new one."
                );
                break;

            case AUTH_TOO_MANY_ATTEMPTS:
                showError(
                        "Too Many Attempts",
                        "You have reached the maximum number of attempts. Please try again later."
                );
                break;

            case AUTH_NO_OTP_SESSION:
                showError(
                        "Session Expired",
                        "Your OTP session is no longer valid. Please start again."
                );
                break;

            default:
                showError(
                        "Problem",
                        "Something went wrong. Please try again."
                );
                break;
        }
    }

    // ------------------------------------------------------------
    // RESEND OTP
    // ------------------------------------------------------------

    private void resendOtp() {
        binding.resendBtn.setEnabled(false);

        LoginRequest request = new LoginRequest(phoneNumber, countryCode);

        ApiCaller.call(authApi.login(request), new ApiCaller.ApiCallback<>() {
            @Override
            public void onSuccess(ApiResponse<AuthResponse> response) {
                ToastUtil.show(OTPActivity.this, "OTP sent successfully", SUCCESS);
                startResendCountdown();
            }

            @Override
            public void onError(ApiResponse<AuthResponse> error) {
                binding.resendBtn.setEnabled(true);

                showError(
                        "Unable to Resend",
                        error.getMessage() != null
                                ? error.getMessage()
                                : "Please try again later."
                );
            }
        });
    }

    private void startResendCountdown() {
        if (resendTimer != null) resendTimer.cancel();

        binding.resendBtn.setEnabled(false);

        resendTimer = new CountDownTimer(RESEND_INTERVAL_MS, 1000) {

            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {
                binding.resendBtn.setText(
                        String.format("Resend in 00:%02d", millisUntilFinished / 1000)
                );
            }

            @Override
            public void onFinish() {
                binding.resendBtn.setEnabled(true);
                binding.resendBtn.setText("Resend OTP");
            }
        }.start();
    }

    // ------------------------------------------------------------
    // SUCCESS
    // ------------------------------------------------------------

    private void handleAuthSuccess(AuthResponse auth) {

        if (auth == null || auth.getUser() == null) {
            showError("Login Failed", "Invalid server response. Please try again.");
            return;
        }

        UserDto user = auth.getUser();

        // 1️⃣ Save user + tokens FIRST (critical)
        LocalDBManager.getStore(this)
                .insertOrUpdate("user", JSONUtil.toJson(user));

        JwtTokenHelper.saveJwtTokens(
                this,
                auth.getAccessToken(),
                auth.getRefreshToken()
        );

        // 2️⃣ Decide navigation
        Long selectedProfileId = user.getSelectedProfileId();

        if (selectedProfileId == null) {
            // Fresh user
            navigateToCreateProfile(user.getId(), phoneNumber);
            return;
        }

        // 3️⃣ Fetch selected profile
        fetchAndValidateProfile(selectedProfileId, user.getId());
    }

    private void fetchAndValidateProfile(Long profileId, Long userId) {

        ApiCaller.call(
                profileApi.getProfileById(profileId),
                new ApiCaller.ApiCallback<ProfileDto>() {

                    @Override
                    public void onSuccess(ApiResponse<ProfileDto> response) {

                        ProfileDto profile = response.getData();

                        if (profile == null || !isProfileComplete(profile)) {
                            // Incomplete or corrupted profile
                            navigateToCreateProfile(userId, phoneNumber);
                            return;
                        }

                        // Profile valid ✅
                        LocalDBManager.getStore(OTPActivity.this)
                                .insertOrUpdate("profile", JSONUtil.toJson(profile));

                        LocalDBManager.getStore(OTPActivity.this)
                                .insertOrUpdate("isLoggedIn", "true");

                        ToastUtil.show(
                                OTPActivity.this,
                                "Login successful",
                                SUCCESS
                        );

                        navigateToMainActivity();
                    }

                    @Override
                    public void onError(ApiResponse<ProfileDto> error) {
                        // Safe fallback
                        navigateToCreateProfile(userId, phoneNumber);
                    }
                }
        );
    }

    private boolean isProfileComplete(ProfileDto p) {

        return notEmpty(p.getName())
                && p.getGender() != null
                && p.getDateOfBirth() != null
                && p.getHeight() != null
                && notEmpty(p.getMaritalStatus())
                && notEmpty(p.getHighestEducation())
                && notEmpty(p.getEmploymentType())
                && notEmpty(p.getOccupation())
                && notEmpty(p.getIncomeRange())
                && notEmpty(p.getCity())
                && notEmpty(p.getState())
                && notEmpty(p.getCountry())
                && notEmpty(p.getMotherTongue())
                && notEmpty(p.getSect())
                && notEmpty(p.getCaste());
    }

    private boolean notEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }

    // ------------------------------------------------------------
    // NAVIGATION
    // ------------------------------------------------------------

    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void navigateToCreateProfile(Long userId, String phoneNumber) {
        Intent intent = new Intent(this, CreateProfileActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("phoneNumber", phoneNumber);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // ------------------------------------------------------------
    // UI HELPERS
    // ------------------------------------------------------------

    private void setVerifyLoading(boolean loading) {
        binding.submitBtn.setEnabled(!loading);
        binding.submitBtn.setText(loading ? "Verifying..." : "Verify OTP");
    }

    private void showError(String title, String message) {
        PopupDialogUtil.show(
                this,
                PopupDialogUtil.Type.ERROR,
                title,
                message,
                "Dismiss",
                null
        );
    }
}
