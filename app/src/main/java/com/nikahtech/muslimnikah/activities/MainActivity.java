package com.nikahtech.muslimnikah.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.nikahtech.muslimnikah.Backend.dto.eto.ProfileDto;
import com.nikahtech.muslimnikah.R;
import com.nikahtech.muslimnikah.databinding.ActivityMainBinding;
import com.nikahtech.muslimnikah.fragments.main_fragments.AccountFragment;
import com.nikahtech.muslimnikah.fragments.main_fragments.ChatFragment;
import com.nikahtech.muslimnikah.fragments.main_fragments.DashboardFragment;
import com.nikahtech.muslimnikah.fragments.main_fragments.SearchFragment;
import com.nikahtech.muslimnikah.keystores.LocalDBManager;
import com.nikahtech.muslimnikah.utils.JSONUtil;
import com.nikahtech.muslimnikah.utils.SysUtil;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    Fragment dashboardFragment = new DashboardFragment();
    Fragment searchFragment = new SearchFragment();
    Fragment chatFragment = new ChatFragment();
    Fragment accountFragment = new AccountFragment();

    Fragment active = dashboardFragment;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        SysUtil.setStatusBar(this, ContextCompat.getColor(this, R.color.colorPrimary), false);

        String profileJson = LocalDBManager.getStore(this).get("profile");

        if (profileJson != null && !profileJson.isEmpty()) {
            ProfileDto savedOne = JSONUtil.fromJson(profileJson, ProfileDto.class);

            if (savedOne != null) {
                Log.d("Saved Profile", savedOne.toString());
            } else {
                Log.w("Saved Profile", "Failed to parse profile JSON");
            }
        } else {
            Log.w("Saved Profile", "No profile found in local storage");
        }




        // SETUP: add all fragments once
        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_container, accountFragment, "4")
                .hide(accountFragment)
                .commit();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_container, chatFragment, "3")
                .hide(chatFragment)
                .commit();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_container, searchFragment, "2")
                .hide(searchFragment)
                .commit();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_container, dashboardFragment, "1")
                .commit();

        setupNavigation();

    }

    private void setupNavigation() {

        binding.menu.setOnItemSelectedListener(item -> {

            if (item == R.id.nav_home) {
                showFragment(dashboardFragment);

            } else if (item == R.id.nav_search) {
                showFragment(searchFragment);

            } else if (item == R.id.nav_chat) {
                showFragment(chatFragment);

            } else if (item == R.id.nav_profile) {
                showFragment(accountFragment);
            }

        });

        binding.menu.setItemSelected(R.id.nav_home, true);
    }

    private void showFragment(Fragment fragment) {
        if (fragment == active) return;

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.fade_in_fast,
                        R.anim.fade_out_fast
                )
                .hide(active)
                .show(fragment)
                .commit();

        active = fragment;
    }
}
