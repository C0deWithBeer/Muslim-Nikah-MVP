package com.nikahtech.muslimnikah.activities;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nikahtech.muslimnikah.R;
import com.nikahtech.muslimnikah.databinding.ActivitySplashBinding;
import com.nikahtech.muslimnikah.keystores.LocalDBManager;
import com.nikahtech.muslimnikah.utils.SysUtil;
import com.nikahtech.muslimnikah.utils.ToastUtil;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private Class<?> nextScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        SysUtil.changeStatusBarColor(this, R.color.colorPrimary);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        decideNextScreen();
        animateLogo();
    }

    private void decideNextScreen() {
        String isLoggedIn = LocalDBManager.getStore(this).get("isLoggedIn");

        if ("true".equalsIgnoreCase(isLoggedIn)) {
            nextScreen = MainActivity.class;
        } else {
            nextScreen = LoginActivity.class;
        }
    }

    private void animateLogo() {
        binding.logo.setAlpha(0f);
        binding.logo.setScaleX(0.8f);
        binding.logo.setScaleY(0.8f);

        binding.logo.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(1800)
                .setInterpolator(new OvershootInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        binding.logo.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        navigateNext();
                    }

                    @Override public void onAnimationCancel(@NonNull Animator animator) {}
                    @Override public void onAnimationRepeat(@NonNull Animator animator) {}
                })
                .start();
    }

    private void navigateNext() {
        ToastUtil.show(this, "Hello there", ToastUtil.AppToastType.SUCCESS);
        startActivity(new Intent(this, nextScreen));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}