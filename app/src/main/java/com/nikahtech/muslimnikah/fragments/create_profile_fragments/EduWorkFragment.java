package com.nikahtech.muslimnikah.fragments.create_profile_fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.nikahtech.muslimnikah.Backend.dto.eto.ProfileDto;
import com.nikahtech.muslimnikah.databinding.FragmentEduWorkBinding;
import com.nikahtech.muslimnikah.models.Profile;
import com.nikahtech.muslimnikah.utils.ProfileOptionsManager;
import com.nikahtech.muslimnikah.utils.SingleOptionPicker;

import java.util.List;

public final class EduWorkFragment extends BaseStepFragment {

    private FragmentEduWorkBinding binding;
    private Context context;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentEduWorkBinding.inflate(inflater, container, false);
        context = requireContext();

        // --------------------------------------------------
        // Initialize all pickers
        // --------------------------------------------------
        attachPicker(binding.highestEducationEdt, ProfileOptionsManager.highestEducation(), "Highest Education");
        attachPicker(binding.fieldOfStudyEdt, ProfileOptionsManager.fieldOfStudy(), "Field of Study"); // selectable only
        attachPicker(binding.employedInEdt, ProfileOptionsManager.employmentType(), "Employed In");
        attachPicker(binding.occupationEdt, ProfileOptionsManager.occupation(), "Occupation");
        attachPicker(binding.annualIncomeEdt, ProfileOptionsManager.annualIncome(), "Annual Income");

        // --------------------------------------------------
        // Navigation buttons
        // --------------------------------------------------
        binding.prevBtn.setOnClickListener(v -> stepNavigator.previousStep());

        binding.nextBtn.setOnClickListener(v -> {
            if (validate()) {
                persist();
                stepNavigator.nextStep();
            }
        });

        return binding.getRoot();
    }

    // --------------------------------------------------
    // Validation
    // --------------------------------------------------
    private boolean validate() {
        clearErrors();

        if (isEmpty(binding.highestEducationEdt)) {
            binding.highestEducationInputLay.setError("Required");
            return false;
        }

        if (isEmpty(binding.fieldOfStudyEdt)) {
            binding.fieldOfStudyInputLay.setError("Required");
            return false;
        }

        if (isEmpty(binding.employedInEdt)) {
            binding.employedInInputLay.setError("Required");
            return false;
        }

        if (isEmpty(binding.occupationEdt)) {
            binding.occupationInputLay.setError("Required");
            return false;
        }

        if (isEmpty(binding.annualIncomeEdt)) {
            binding.annualIncomeInputLay.setError("Required");
            return false;
        }

        return true;
    }

    private void clearErrors() {
        binding.highestEducationInputLay.setError(null);
        binding.fieldOfStudyInputLay.setError(null);
        binding.employedInInputLay.setError(null);
        binding.occupationInputLay.setError(null);
        binding.annualIncomeInputLay.setError(null);
    }

    private boolean isEmpty(TextInputEditText edt) {
        return edt.getText() == null || edt.getText().toString().trim().isEmpty();
    }

    // --------------------------------------------------
    // Save data to profile
    // --------------------------------------------------
    private void persist() {
        ProfileDto profile = profile();

        profile.setHighestEducation(value(binding.highestEducationEdt));
        profile.setFieldOfStudy(value(binding.fieldOfStudyEdt)); // mandatory and selectable
        profile.setEmploymentType(value(binding.employedInEdt));
        profile.setOccupation(value(binding.occupationEdt));
        profile.setIncomeRange(value(binding.annualIncomeEdt));
    }

    private String value(TextInputEditText edt) {
        return edt.getText().toString().trim();
    }

    // --------------------------------------------------
    // Picker helper (all pickers selectable, not editable)
    // --------------------------------------------------
    private void attachPicker(TextInputEditText edt, List<String> options, String title) {
        edt.setShowSoftInputOnFocus(false); // prevent keyboard

        View.OnClickListener openPicker = v -> new SingleOptionPicker(
                context,
                options,
                edt,
                title,
                value(edt),
                selectedItem -> {
                    edt.setText(selectedItem.toString());
                    edt.clearFocus();
                }
        ).open();

        edt.setOnClickListener(openPicker);
        edt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) openPicker.onClick(v);
        });
    }
}
