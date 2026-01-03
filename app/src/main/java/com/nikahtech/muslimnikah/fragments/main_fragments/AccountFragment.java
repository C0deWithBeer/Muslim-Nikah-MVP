package com.nikahtech.muslimnikah.fragments.main_fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.nikahtech.muslimnikah.activities.EditProfileActivity;
import com.nikahtech.muslimnikah.activities.PartnerPreferenceActivity;
import com.nikahtech.muslimnikah.activities.SubscriptionActivity;
import com.nikahtech.muslimnikah.activities.ViewProfileActivity;
import com.nikahtech.muslimnikah.databinding.FragmentAccountBinding;
import com.nikahtech.muslimnikah.keystores.LocalDBManager;

public class AccountFragment extends Fragment {

    FragmentAccountBinding binding;
    Context context;

    private String name;
    private String profileImageUrl;
    private String maritalStatus;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        context = requireContext();

        if (LocalDBManager.getProfile(requireContext()) != null) {
            profileImageUrl = LocalDBManager.getProfile(context).getProfilePic();
            name = LocalDBManager.getProfile(context).getName();
            maritalStatus = LocalDBManager.getProfile(context).getMaritalStatus();

            setupUI();
        }



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

    private void setupUI() {
        Glide.with(context)
                .load(profileImageUrl)
                .into(binding.profileImg);

        binding.nameTxt.setText(name);
        binding.maritalStsTxt.setText(maritalStatus);
    }
}