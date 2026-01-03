package com.nikahtech.muslimnikah.fragments.create_profile_fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.nikahtech.muslimnikah.Backend.dto.eto.ProfileDto;
import com.nikahtech.muslimnikah.databinding.FragmentReligiousBinding;
import com.nikahtech.muslimnikah.models.Profile;
import com.nikahtech.muslimnikah.utils.ProfileOptionsManager;
import com.nikahtech.muslimnikah.utils.SingleOptionPicker;

import java.util.List;

public final class ReligiousFragment extends BaseStepFragment {

    private FragmentReligiousBinding binding;
    private Context context;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentReligiousBinding.inflate(inflater, container, false);
        context = requireContext();

        initPickers();
        initNavigation();

        return binding.getRoot();
    }

    // --------------------------------------------------
    // Picker setup (ALL NON-TYPABLE)
    // --------------------------------------------------
    private void initPickers() {

        attachPicker(binding.sectEdt,
                ProfileOptionsManager.sects(), "Sect");

        attachPicker(binding.casteEdt,
                ProfileOptionsManager.castes(), "Caste");

        attachPicker(binding.namazFrequencyEdt,
                ProfileOptionsManager.namazFrequencies(), "Prayer Habit");

        attachPicker(binding.readQuranEdt,
                ProfileOptionsManager.readQuranOptions(), "Quran reading");

        attachPicker(binding.halalDietEdt,
                ProfileOptionsManager.halalDietOptions(), "Halal Diet");

        attachPicker(binding.beardHijabEdt,
                ProfileOptionsManager.beardHijabOptions(), "Beard / Hijab Preferences");

        attachPicker(binding.religiousLevelEdt,
                ProfileOptionsManager.religiousObservationLevels(),
                "Religious Involvement");
    }

    private void attachPicker(
            TextInputEditText edt,
            List<String> options,
            String title
    ) {
        edt.setShowSoftInputOnFocus(false);

        View.OnClickListener openPicker = v ->
                new SingleOptionPicker(
                        context,
                        options,
                        edt,
                        title,
                        value(edt),
                        selected -> {
                            edt.setText(selected.toString());
                            edt.clearFocus();
                        }
                ).open();

        edt.setOnClickListener(openPicker);
        edt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) openPicker.onClick(v);
        });
    }

    // --------------------------------------------------
    // Navigation
    // --------------------------------------------------
    private void initNavigation() {

        binding.prevBtn.setOnClickListener(v ->
                stepNavigator.previousStep());

        binding.nextBtn.setOnClickListener(v -> {
            if (validate()) {
                persist();
                stepNavigator.nextStep();
            }
        });
    }

    // --------------------------------------------------
    // Validation (ONLY mandatory fields)
    // --------------------------------------------------
    private boolean validate() {
        clearErrors();

        if (isEmpty(binding.sectEdt)) {
            binding.sectInputLay.setError("Required");
            return false;
        }

        if (isEmpty(binding.casteEdt)) {
            binding.casteInputLay.setError("Required");
            return false;
        }

        return true;
    }

    private void clearErrors() {
        binding.sectInputLay.setError(null);
        binding.casteInputLay.setError(null);
    }

    private boolean isEmpty(TextInputEditText edt) {
        return edt.getText() == null ||
                edt.getText().toString().trim().isEmpty();
    }

    // --------------------------------------------------
    // Save to Profile
    // --------------------------------------------------
    private void persist() {
        ProfileDto profile = profile();

        profile.setSect(value(binding.sectEdt));
        profile.setCaste(value(binding.casteEdt));
        profile.setPrayerHabit(value(binding.namazFrequencyEdt));
        profile.setQuranReading(value(binding.readQuranEdt));
        profile.setHalalDiet(value(binding.halalDietEdt));
        profile.setBeardHijab(value(binding.beardHijabEdt));
        profile.setInvolvement(value(binding.religiousLevelEdt));
    }

    private String value(TextInputEditText edt) {
        return edt.getText() == null || edt.getText().toString().trim().isEmpty()
                ? null
                : edt.getText().toString().trim();
    }
}
