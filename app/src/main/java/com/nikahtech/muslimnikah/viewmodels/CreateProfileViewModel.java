package com.nikahtech.muslimnikah.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nikahtech.muslimnikah.models.Profile;

public class CreateProfileViewModel extends ViewModel {

    private final MutableLiveData<Integer> currentStep = new MutableLiveData<>(0);
    private final Profile profile = new Profile();
    private String userId;

    public Profile getProfile() {
        return profile;
    }

    public LiveData<Integer> getCurrentStep() {
        return currentStep;
    }

    public void nextStep(int maxSteps) {
        Integer step = currentStep.getValue();
        if (step != null && step < maxSteps - 1) {
            currentStep.setValue(step + 1);
        }
    }

    public void previousStep() {
        Integer step = currentStep.getValue();
        if (step != null && step > 0) {
            currentStep.setValue(step - 1);
        }
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}

