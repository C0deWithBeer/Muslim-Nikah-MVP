package com.nikahtech.muslimnikah.fragments.create_profile_fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.nikahtech.muslimnikah.Backend.dto.eto.ProfileDto;
import com.nikahtech.muslimnikah.Backend.enums.Gender;
import com.nikahtech.muslimnikah.R;
import com.nikahtech.muslimnikah.databinding.FragmentBasicBinding;
import com.nikahtech.muslimnikah.models.Profile;
import com.nikahtech.muslimnikah.utils.ProfileOptionsManager;
import com.nikahtech.muslimnikah.utils.SingleOptionPicker;
import com.nikahtech.muslimnikah.utils.SysUtil;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

public final class BasicFragment extends BaseStepFragment {

    private FragmentBasicBinding binding;
    private Context context;

    // --------------------------------------------------
    // Lifecycle
    // --------------------------------------------------

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentBasicBinding.inflate(inflater, container, false);
        context = requireContext();

        initPickers();
        initDatePicker();
        initNavigation();

        return binding.getRoot();
    }

    // --------------------------------------------------
    // Init
    // --------------------------------------------------

    private void initPickers() {
        attachPicker(binding.profileCreatedByEdt,
                ProfileOptionsManager.profileCreatedBy(), "Profile Created By");

        attachPicker(binding.genderEdt,
                ProfileOptionsManager.gender(), "Gender");

        attachPicker(binding.heightEdt,
                ProfileOptionsManager.heightInFt(), "Height");

        attachPicker(binding.maritalStatusEdt,
                ProfileOptionsManager.maritalStatus(), "Marital Status");

        attachPicker(binding.disabilityEdt,
                ProfileOptionsManager.disabilityStatus(), "Disability");
    }

    private void initNavigation() {
        binding.prevBtn.setOnClickListener(v -> stepNavigator.previousStep());

        binding.nextBtn.setOnClickListener(v -> {
            if (validate()) {
                persist();
                stepNavigator.nextStep();
            }
        });
    }

    // --------------------------------------------------
    // Validation
    // --------------------------------------------------

    private boolean validate() {
        clearErrors();

        if (isEmpty(binding.profileCreatedByEdt)) {
            binding.profileCreatedByInputLay.setError("Required");
            return false;
        }
        if (isEmpty(binding.genderEdt)) {
            binding.genderInputLay.setError("Required");
            return false;
        }
        if (isEmpty(binding.dobEdt)) {
            binding.dobInputLay.setError("Required");
            return false;
        }
        if (isEmpty(binding.heightEdt)) {
            binding.heightInputLay.setError("Required");
            return false;
        }
        if (isEmpty(binding.maritalStatusEdt)) {
            binding.maritalStatusInputLay.setError("Required");
            return false;
        }
        if (isEmpty(binding.disabilityEdt)) {
            binding.disabilityInputLay.setError("Required");
            return false;
        }

        return true;
    }

    private void clearErrors() {
        binding.profileCreatedByInputLay.setError(null);
        binding.genderInputLay.setError(null);
        binding.dobInputLay.setError(null);
        binding.heightInputLay.setError(null);
        binding.maritalStatusInputLay.setError(null);
        binding.disabilityInputLay.setError(null);
    }

    private boolean isEmpty(TextInputEditText edt) {
        return edt.getText() == null || edt.getText().toString().trim().isEmpty();
    }

    // --------------------------------------------------
    // Save
    // --------------------------------------------------

    private void persist() {
        ProfileDto profile = profile();

        profile.setProfileCreatedBy(value(binding.profileCreatedByEdt));
        profile.setName(value(binding.fullNameEdt));
        profile.setGender(parseGender(value(binding.genderEdt)));
        profile.setDateOfBirth(LocalDate.parse(
                value(binding.dobEdt),
                DateTimeFormatter.ISO_LOCAL_DATE
        ));
        profile.setHeight(parseHeightCm(value(binding.heightEdt)));
        profile.setMaritalStatus(value(binding.maritalStatusEdt));
        profile.setDisabilityStatus(value(binding.disabilityEdt));
    }

    private String value(TextInputEditText edt) {
        return edt.getText().toString().trim();
    }

    // --------------------------------------------------
    // Date Picker
    // --------------------------------------------------

    private void initDatePicker() {
        binding.dobEdt.setOnClickListener(this::openDatePicker);
        binding.dobEdt.setOnFocusChangeListener((v, f) -> {
            if (f) openDatePicker(v);
        });
    }

    private void openDatePicker(View v) {
        SysUtil.hideKeyboard(v);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -18);

        MaterialDatePicker<Long> picker =
                MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select Date of Birth")
                        .setTheme(R.style.ThemeOverlay_App_DatePicker)
                        .setSelection(cal.getTimeInMillis())
                        .setCalendarConstraints(
                                new CalendarConstraints.Builder()
                                        .setEnd(cal.getTimeInMillis())
                                        .build()
                        )
                        .build();

        picker.addOnPositiveButtonClickListener(ms -> {
            LocalDate date = Instant.ofEpochMilli(ms)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            binding.dobEdt.setText(date.toString());
            binding.dobEdt.clearFocus();
        });

        picker.show(getParentFragmentManager(), "DOB_PICKER");
    }

    // --------------------------------------------------
    // Picker Helper
    // --------------------------------------------------

    private void attachPicker(
            TextInputEditText edt,
            List<String> options,
            String title
    ) {
        View.OnClickListener open = v -> {
            new SingleOptionPicker(
                    context,
                    options,
                    edt,
                    title,
                    edt.getText().toString(),
                    selected -> {
                        edt.setText(selected.toString());
                        edt.clearFocus();
                    }
            ).open();
        };

        edt.setOnClickListener(open);
        edt.setOnFocusChangeListener((v, f) -> {
            if (f) open.onClick(v);
        });
    }

    // --------------------------------------------------
    // Utils
    // --------------------------------------------------

    private static int parseHeightCm(String value) {
        try {
            String s = value.toLowerCase().replace(" ", "");
            int ft = Integer.parseInt(s.split("ft")[0]);
            int in = s.contains("in")
                    ? Integer.parseInt(s.split("ft")[1].replace("in", ""))
                    : 0;
            return (int) (ft * 30.48 + in * 2.54);
        } catch (Exception e) {
            return 0;
        }
    }

    private static Gender parseGender(String v) {
        if (v == null) return null;
        return v.equalsIgnoreCase("male")
                ? Gender.MALE
                : Gender.FEMALE;
    }
}
