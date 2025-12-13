package com.nikahtech.muslimnikah.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.nikahtech.muslimnikah.R;
import com.nikahtech.muslimnikah.adapters.viewpager_adapters.OnBoardAdapter;
import com.nikahtech.muslimnikah.databinding.ActivityOnBoardBinding;
import com.nikahtech.muslimnikah.models.OnBoardItem;

import java.util.Arrays;
import java.util.List;

public class OnBoardActivity extends AppCompatActivity {

    ActivityOnBoardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityOnBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<OnBoardItem> list = Arrays.asList(
                new OnBoardItem(R.drawable.onboard_1, "Find Your Perfect Match", "Verified Muslim profiles only"),
                new OnBoardItem(R.drawable.onboard_2, "Safe & Secure", "Your privacy is our top priority"),
                new OnBoardItem(R.drawable.onboard_3, "Start Your Journey", "Marriage is a sunnah — begin today")
        );

        OnBoardAdapter adapter = new OnBoardAdapter(list);
        binding.viewPager.setAdapter(adapter);
        binding.indicator.setCount(list.size());

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.indicator.setActive(position);
            }
        });

        binding.getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLogin();
            }
        });

    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}