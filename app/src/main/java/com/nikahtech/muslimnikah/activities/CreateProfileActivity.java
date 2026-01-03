package com.nikahtech.muslimnikah.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.nikahtech.muslimnikah.Backend.Api.ProfileApi;
import com.nikahtech.muslimnikah.Backend.ApiCaller;
import com.nikahtech.muslimnikah.Backend.ApiResponse;
import com.nikahtech.muslimnikah.Backend.RetrofitClient;
import com.nikahtech.muslimnikah.Backend.dto.eto.ProfileDto;
import com.nikahtech.muslimnikah.Backend.enums.PhotoPrivacy;
import com.nikahtech.muslimnikah.R;
import com.nikahtech.muslimnikah.databinding.ActivityCreateProfileBinding;
import com.nikahtech.muslimnikah.fragments.create_profile_fragments.BasicFragment;
import com.nikahtech.muslimnikah.fragments.create_profile_fragments.EduWorkFragment;
import com.nikahtech.muslimnikah.fragments.create_profile_fragments.LocationFragment;
import com.nikahtech.muslimnikah.fragments.create_profile_fragments.PhotoFragment;
import com.nikahtech.muslimnikah.fragments.create_profile_fragments.ReligiousFragment;
import com.nikahtech.muslimnikah.interfaces.StepNavigator;
import com.nikahtech.muslimnikah.keystores.LocalDBManager;
import com.nikahtech.muslimnikah.models.Profile;
import com.nikahtech.muslimnikah.utils.JSONUtil;
import com.nikahtech.muslimnikah.utils.PopupDialogUtil;
import com.nikahtech.muslimnikah.utils.SysUtil;
import com.nikahtech.muslimnikah.utils.ToastUtil;

import java.util.Arrays;
import java.util.List;

public final class CreateProfileActivity
        extends AppCompatActivity
        implements StepNavigator {

    private static final String KEY_STEP = "key_step";

    private ActivityCreateProfileBinding binding;

    private final ProfileDto profile = new ProfileDto();
    private String userId;
    private int currentStep = 0;

    private ProfileApi profileApi;
    private List<Fragment> steps;

    // --------------------------------------------------
    // Lifecycle
    // --------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SysUtil.changeStatusBarColor(this, R.color.black);

        profileApi = RetrofitClient.createServiceWithAuth(ProfileApi.class, this);

        if (!readIntent()) {
            finish();
            return;
        }

        initSteps();
        setupStepView();

        if (savedInstanceState != null) {
            currentStep = savedInstanceState.getInt(KEY_STEP, 0);
            restoreFragments();
        } else {
            preloadFragments();
        }

        showStep(currentStep, false);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_STEP, currentStep);
    }

    // --------------------------------------------------
    // Init
    // --------------------------------------------------

    private boolean readIntent() {
        Intent i = getIntent();
        if (i == null) return false;

        long uid = i.getLongExtra("userId", -1);
        if (uid <= 0) return false;

        userId = String.valueOf(uid);

        String phone = i.getStringExtra("phoneNumber");
        if (phone != null) {
            profile.setPhoneNumber( phone);
        }
        return true;
    }

    private void initSteps() {
        steps = Arrays.asList(
                new PhotoFragment(),
                new BasicFragment(),
                new EduWorkFragment(),
                new LocationFragment(),
                new ReligiousFragment()
        );
    }

    private void setupStepView() {
        binding.stepView.setSteps(
                Arrays.asList("Photo", "Basic", "Edu & Work", "Location", "Religious")
        );
    }

    // --------------------------------------------------
    // Fragment Handling
    // --------------------------------------------------

    private void preloadFragments() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < steps.size(); i++) {
            ft.add(R.id.fragmentContainer, steps.get(i), "step_" + i);
            ft.hide(steps.get(i));
        }
        ft.commitNow();
    }

    private void restoreFragments() {
        for (int i = 0; i < steps.size(); i++) {
            Fragment f = getSupportFragmentManager().findFragmentByTag("step_" + i);
            if (f != null) steps.set(i, f);
        }
    }

    private void showStep(int step, boolean animate) {
        if (step < 0 || step >= steps.size()) return;

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (animate) {
            ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        for (int i = 0; i < steps.size(); i++) {
            if (i == step) ft.show(steps.get(i));
            else ft.hide(steps.get(i));
        }

        ft.commit();
        currentStep = step;
        binding.stepView.go(step, true);
    }

    // --------------------------------------------------
    // StepNavigator
    // --------------------------------------------------

    @Override
    public void nextStep() {
        if (currentStep < steps.size() - 1) {
            showStep(currentStep + 1, true);
        } else {
            submitProfile();
        }
    }

    @Override
    public void previousStep() {
        if (currentStep > 0) {
            showStep(currentStep - 1, true);
        }
    }

    // --------------------------------------------------
    // Submit
    // --------------------------------------------------

    private void submitProfile() {

        profile.setProfileBoost(0);
        profile.setProfilePhotoPrivacy(PhotoPrivacy.PUBLIC);
        profile.setGalleryPhotosPrivacy(PhotoPrivacy.PUBLIC);

        ApiCaller.call(
                profileApi.saveProfile(Long.parseLong(userId), profile),
                new ApiCaller.ApiCallback<>() {

                    @Override
                    public void onSuccess(ApiResponse<ProfileDto> res) {
                        if (res.getData() == null) return;

                        Log.d("TAG", "Respons DTO:: "+res.getData().toString());

                        LocalDBManager.getStore(CreateProfileActivity.this)
                                .insertOrUpdate("profile", JSONUtil.toJson(res.getData()));

                        LocalDBManager.getStore(CreateProfileActivity.this)
                                .insertOrUpdate("isLoggedIn", "true");

                        ToastUtil.show(
                                CreateProfileActivity.this,
                                "Profile Created Successfully",
                                ToastUtil.AppToastType.SUCCESS
                        );

                        startActivity(new Intent(CreateProfileActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(ApiResponse<ProfileDto> err) {
                        PopupDialogUtil.show(
                                CreateProfileActivity.this,
                                PopupDialogUtil.Type.ERROR,
                                "Profile Creation Failed",
                                "Please try again.",
                                "OK",
                                null
                        );
                    }
                }
        );
    }

    // --------------------------------------------------
    // Access
    // --------------------------------------------------

    public ProfileDto getProfile() {
        return profile;
    }

    public String getUserId() {
        return userId;
    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        if (currentStep > 0) previousStep();
        else super.onBackPressed();
    }
}


