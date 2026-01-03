package com.nikahtech.muslimnikah.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nikahtech.muslimnikah.Backend.RetrofitClient;
import com.nikahtech.muslimnikah.R;
import com.nikahtech.muslimnikah.databinding.ActivityLoginBinding;
import com.nikahtech.muslimnikah.Backend.Api.AuthApi;
import com.nikahtech.muslimnikah.Backend.ApiCaller;
import com.nikahtech.muslimnikah.Backend.ApiResponse;
import com.nikahtech.muslimnikah.Backend.dto.request.LoginRequest;
import com.nikahtech.muslimnikah.Backend.dto.response.AuthResponse;
import com.nikahtech.muslimnikah.utils.PopupDialogUtil;
import com.nikahtech.muslimnikah.utils.SysUtil;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AuthApi authApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
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



        setupTermsAndConditions();

        authApi = RetrofitClient.createService(AuthApi.class);
        setupClickListeners();
    }

    // ------------------------------------------------------------------
    // UI SETUP
    // ------------------------------------------------------------------

    private void setupClickListeners() {
        binding.MButton.setOnClickListener(v -> {
            if (validateInput()) {
                sendOtp();
            }
        });
    }

    @SuppressLint("ObsoleteSdkInt")
    private void setupTermsAndConditions() {
        String privacyUrl = "https://yourdomain.com/privacy";
        String termsUrl = "https://yourdomain.com/terms";

        String html = "By continuing, you agree to our <br>" +
                "<a href=\"" + privacyUrl + "\"><b> Privacy Policy</b></a> and " +
                "<a href=\"" + termsUrl + "\"><b> Terms & Conditions</b></a>";

        Spanned text;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            text = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            text = Html.fromHtml(html);
        }

        binding.agreementChkBox.setText(text);
        binding.agreementChkBox.setMovementMethod(LinkMovementMethod.getInstance());
        binding.agreementChkBox.setLinkTextColor(
                ContextCompat.getColor(this, R.color.colorInfo)
        );
    }

    // ------------------------------------------------------------------
    // VALIDATION
    // ------------------------------------------------------------------

    private boolean validateInput() {
        String phone = binding.etPhoneNumber.getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            showError("Phone Number", "Phone number required to continue.");
            return false;
        }

        if (phone.length() < 10) {
            showError("Invalid Number", "Phone number must be at least 10 digits.");
            return false;
        }

        if (!binding.agreementChkBox.isChecked()) {
            PopupDialogUtil.show(
                    this,
                    PopupDialogUtil.Type.DOC,
                    "Accept Policies",
                    "Review our Privacy Policy and Terms & Conditions. Check the box below to continue.",
                    "Accept",
                    v -> binding.agreementChkBox.setChecked(true)
            );
            return false;
        }

        return true;
    }

    // ------------------------------------------------------------------
    // API CALL
    // ------------------------------------------------------------------

    private void sendOtp() {
        showLoading(true);

        String countryCode = binding.ccp.getSelectedCountryCodeWithPlus(); // +91
        String phoneNumber = binding.etPhoneNumber.getText().toString().trim(); // 9500951894

        LoginRequest request = new LoginRequest(
                phoneNumber,
                countryCode
        );

        ApiCaller.call(authApi.login(request), new ApiCaller.ApiCallback<>() {
            @Override
            public void onSuccess(ApiResponse<AuthResponse> response) {
                showLoading(false);
                navigateToOtp(phoneNumber, countryCode);
            }

            @Override
            public void onError(ApiResponse<AuthResponse> error) {
                showLoading(false);
                switch (error.getAppCode()) {
                    case AUTH_PHONE_INVALID:
                        showError("Invalid Number", "Phone number must be at least 10 digits.");
                        break;

                    case AUTH_OTP_DAILY_LIMIT_REACHED:
                        showError("Limit Reached", error.getMessage());
                        break;

                    case AUTH_OTP_WAIT_FOR_COOLDOWN:
                        showError("Wait for cooldown", error.getMessage());
                        break;
                }
            }
        });
    }

    // ------------------------------------------------------------------
    // NAVIGATION
    // ------------------------------------------------------------------

    private void navigateToOtp(String phone, String countryCode) {
        Intent intent = new Intent(this, OTPActivity.class);
        intent.putExtra("PHONE_NUMBER", phone);
        intent.putExtra("COUNTRY_CODE", countryCode);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    // ------------------------------------------------------------------
    // UI HELPERS
    // ------------------------------------------------------------------

    private void showLoading(boolean loading) {
        binding.MButton.setEnabled(!loading);
        binding.MButton.setText(loading ? "Please wait..." : "Submit");
    }

    private void showError(String title, String message) {
        PopupDialogUtil.show(this, PopupDialogUtil.Type.ERROR, title, message, "Dismiss", null);
    }
}
