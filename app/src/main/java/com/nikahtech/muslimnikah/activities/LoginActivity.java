package com.nikahtech.muslimnikah.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nikahtech.muslimnikah.R;
import com.nikahtech.muslimnikah.databinding.ActivityLoginBinding;
import com.nikahtech.muslimnikah.utils.SysUtil;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SysUtil.changeStatusBarColor(this, R.color.colorPrimary);

        binding.signUpBtn.setOnClickListener(view -> navigateToSignUp());

        binding.MButton.setOnClickListener(view -> startActivity(new Intent(this, OTPActivity.class)));

    }

    private void navigateToSignUp() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}