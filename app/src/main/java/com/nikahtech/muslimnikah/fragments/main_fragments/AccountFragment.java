package com.nikahtech.muslimnikah.fragments.main_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikahtech.muslimnikah.R;
import com.nikahtech.muslimnikah.activities.EditProfileActivity;
import com.nikahtech.muslimnikah.activities.PartnerPreferenceActivity;
import com.nikahtech.muslimnikah.activities.SubscriptionActivity;
import com.nikahtech.muslimnikah.activities.ViewProfileActivity;
import com.nikahtech.muslimnikah.databinding.FragmentAccountBinding;

public class AccountFragment extends Fragment {

    FragmentAccountBinding binding;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);

        binding.viewProfileOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), ViewProfileActivity.class));
            }
        });

        binding.editProfileOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), EditProfileActivity.class));
            }
        });

        binding.subscriptionOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), SubscriptionActivity.class));
            }
        });

        binding.partnerPreferenceOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), PartnerPreferenceActivity.class));
            }
        });

        return binding.getRoot();
    }
}