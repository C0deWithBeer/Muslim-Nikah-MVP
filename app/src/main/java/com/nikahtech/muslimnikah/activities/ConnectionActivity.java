package com.nikahtech.muslimnikah.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.nikahtech.muslimnikah.R;
import com.nikahtech.muslimnikah.databinding.ActivityConnectionBinding;
import com.nikahtech.muslimnikah.fragments.connection_fragments.ConnectedFragment;
import com.nikahtech.muslimnikah.fragments.connection_fragments.ReceivedFragment;
import com.nikahtech.muslimnikah.fragments.connection_fragments.SentFragment;
import com.nikahtech.muslimnikah.fragments.subscription_fragments.ActiveFragment;
import com.nikahtech.muslimnikah.fragments.subscription_fragments.PlansFragment;
import com.nikahtech.muslimnikah.widgets.MButton;

import java.util.Arrays;
import java.util.List;

public class ConnectionActivity extends AppCompatActivity {

    ActivityConnectionBinding binding;

    Fragment sentFragment;
    Fragment receivedFragment;
    Fragment connectedFragment;

    List<MButton> optionButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConnectionBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        handleSystemBars();
        initFragments();
        setupOptionButtons();
        showInitialFragment();

    }

    private void handleSystemBars() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initFragments() {
        sentFragment = new SentFragment();
        receivedFragment = new ReceivedFragment();
        connectedFragment = new ConnectedFragment();
    }

    private void showInitialFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, sentFragment, "SENT")
                .commit();
    }

    private void setupOptionButtons() {

        binding.backBtn.setOnClickListener(view -> finish());

        optionButtons = Arrays.asList(
                binding.sentOpt,
                binding.receivedOpt,
                binding.connectedOpt
        );

        for (MButton button : optionButtons) {
            button.setOnClickListener(v -> handleOptionClick((MButton) v));
        }
    }

    private void handleOptionClick(MButton clickedButton) {
        resetAllOptions();
        applySelectedStyle(clickedButton);
        applyMinimalFade(clickedButton);

        if (clickedButton == binding.sentOpt) {
            showFragment(sentFragment);
        } else if (clickedButton == binding.receivedOpt) {
            showFragment(receivedFragment);
        } else if (clickedButton == binding.connectedOpt) {
            showFragment(connectedFragment);
        }
    }

    private void resetAllOptions() {
        Typeface regular = ResourcesCompat.getFont(this, R.font.regular);

        for (MButton button : optionButtons) {
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            button.setTypeface(regular);
        }
    }

    private void applySelectedStyle(MButton btn) {
        Typeface semiBold = ResourcesCompat.getFont(this, R.font.semibold);

        btn.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        btn.setTextColor(ContextCompat.getColor(this, R.color.white));
        btn.setTypeface(semiBold);
    }

    private void applyMinimalFade(View v) {
        AlphaAnimation fade = new AlphaAnimation(0.6f, 1f);
        fade.setDuration(120);
        fade.setFillAfter(true);
        v.startAnimation(fade);
    }

    /* ---------------------------------------------
                    FRAGMENT HANDLER
       --------------------------------------------- */
    private void showFragment(Fragment targetFragment) {
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

        if (current == targetFragment) return; // No unnecessary reload

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.fade_in_fast,
                        R.anim.fade_out_fast
                )
                .replace(R.id.fragmentContainer, targetFragment)
                .commit();
    }



}