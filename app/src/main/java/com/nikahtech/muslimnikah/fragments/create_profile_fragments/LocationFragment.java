package com.nikahtech.muslimnikah.fragments.create_profile_fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.nikahtech.muslimnikah.Backend.dto.eto.ProfileDto;
import com.nikahtech.muslimnikah.databinding.FragmentLocationBinding;
import com.nikahtech.muslimnikah.models.Profile;
import com.nikahtech.muslimnikah.utils.ProfileOptionsManager;
import com.nikahtech.muslimnikah.utils.SingleOptionPicker;

import java.util.List;

public final class LocationFragment extends BaseStepFragment {

    private FragmentLocationBinding binding;
    private Context context;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentLocationBinding.inflate(inflater, container, false);
        context = requireContext();

        // --------------------------------------------------
        // Picker-only fields
        // --------------------------------------------------
        attachPicker(
                binding.countryEdt,
                ProfileOptionsManager.countries(),
                "Country"
        );

        attachPicker(
                binding.motherTongueEdt,
                ProfileOptionsManager.languages(),
                "Mother Tongue"
        );

        // --------------------------------------------------
        // Navigation
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

        if (isEmpty(binding.cityEdt)) {
            binding.cityInputLay.setError("Required");
            return false;
        }

        if (isEmpty(binding.stateEdt)) {
            binding.stateInputLay.setError("Required");
            return false;
        }

        if (isEmpty(binding.countryEdt)) {
            binding.countryInputLay.setError("Required");
            return false;
        }

        if (isEmpty(binding.motherTongueEdt)) {
            binding.motherTongueInputLay.setError("Required");
            return false;
        }

        return true;
    }

    private void clearErrors() {
        binding.cityInputLay.setError(null);
        binding.stateInputLay.setError(null);
        binding.countryInputLay.setError(null);
        binding.motherTongueInputLay.setError(null);
    }

    private boolean isEmpty(TextInputEditText edt) {
        return edt.getText() == null || edt.getText().toString().trim().isEmpty();
    }

    // --------------------------------------------------
    // Save to Profile
    // --------------------------------------------------
    private void persist() {
        ProfileDto profile = profile();

        profile.setCity(value(binding.cityEdt));
        profile.setState(value(binding.stateEdt));
        profile.setCountry(value(binding.countryEdt));
        profile.setMotherTongue(value(binding.motherTongueEdt));
    }

    private String value(TextInputEditText edt) {
        return edt.getText().toString().trim();
    }

    // --------------------------------------------------
    // Picker helper (non-editable fields)
    // --------------------------------------------------
    private void attachPicker(
            TextInputEditText edt,
            List<String> options,
            String title
    ) {
        edt.setShowSoftInputOnFocus(false); // disable keyboard

        View.OnClickListener openPicker = v ->
                new SingleOptionPicker(
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
