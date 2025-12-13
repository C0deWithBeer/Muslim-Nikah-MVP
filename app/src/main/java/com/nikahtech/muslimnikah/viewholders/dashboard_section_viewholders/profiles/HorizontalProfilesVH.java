package com.nikahtech.muslimnikah.viewholders.dashboard_section_viewholders.profiles;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nikahtech.muslimnikah.activities.ViewProfileActivity;
import com.nikahtech.muslimnikah.databinding.ItemHorizontalProfileBinding;

public class HorizontalProfilesVH extends RecyclerView.ViewHolder {

    private final ItemHorizontalProfileBinding binding;

    public HorizontalProfilesVH(ItemHorizontalProfileBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(String profilePic, String name) {

        Glide.with(binding.getRoot().getContext())
                .load(profilePic)
                .into(binding.profilePicImg);

        binding.nameTxt.setText(name);

        binding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ViewProfileActivity.class);
            v.getContext().startActivity(intent);
        });

    }
}

