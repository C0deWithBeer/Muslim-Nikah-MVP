package com.nikahtech.muslimnikah.fragments.create_profile_fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikahtech.muslimnikah.databinding.FragmentFamilyBinding;

public class FamilyFragment extends BaseStepFragment {

    private FragmentFamilyBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             android.os.Bundle savedInstanceState) {
        binding = FragmentFamilyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
