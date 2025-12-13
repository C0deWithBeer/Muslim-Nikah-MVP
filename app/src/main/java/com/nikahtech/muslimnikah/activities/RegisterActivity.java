package com.nikahtech.muslimnikah.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nikahtech.muslimnikah.R;
import com.nikahtech.muslimnikah.databinding.ActivityRegisterBinding;
import com.nikahtech.muslimnikah.utils.SysUtil;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SysUtil.changeStatusBarColor(this, R.color.colorPrimary);

        binding.signInBtn.setOnClickListener(view -> navigateToSignIn());

        binding.MButton.setOnClickListener(view -> startActivity(new Intent(this, OTPActivity.class)));

    }

    private void navigateToSignIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}