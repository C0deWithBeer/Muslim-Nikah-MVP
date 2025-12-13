package com.nikahtech.muslimnikah.viewholders.chats;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nikahtech.muslimnikah.databinding.ItemChatActiveBinding;
import com.nikahtech.muslimnikah.models.Chat;
import com.nikahtech.muslimnikah.models.Profile;

public class ActiveUsersVH extends RecyclerView.ViewHolder {
    ItemChatActiveBinding binding;
    public ActiveUsersVH(ItemChatActiveBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Profile profile) {

        Glide.with(binding.getRoot().getContext())
                .load(profile.getProfilePic())
                .into(binding.profilePicImg);

    }

}
