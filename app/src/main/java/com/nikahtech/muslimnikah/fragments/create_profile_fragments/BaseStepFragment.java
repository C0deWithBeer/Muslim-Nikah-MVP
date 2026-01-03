package com.nikahtech.muslimnikah.fragments.create_profile_fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.nikahtech.muslimnikah.Backend.dto.eto.ProfileDto;
import com.nikahtech.muslimnikah.activities.CreateProfileActivity;
import com.nikahtech.muslimnikah.interfaces.StepNavigator;
import com.nikahtech.muslimnikah.models.Profile;
import com.nikahtech.muslimnikah.viewmodels.CreateProfileViewModel;

public abstract class BaseStepFragment extends Fragment {

    protected StepNavigator stepNavigator;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof StepNavigator) {
            stepNavigator = (StepNavigator) context;
        } else {
            throw new IllegalStateException(
                    "Parent activity must implement StepNavigator"
            );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        stepNavigator = null;
    }

    protected CreateProfileActivity host() {
        return (CreateProfileActivity) requireActivity();
    }

    protected ProfileDto profile() {
        return host().getProfile();
    }
}


