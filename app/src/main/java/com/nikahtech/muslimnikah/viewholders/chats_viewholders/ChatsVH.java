package com.nikahtech.muslimnikah.viewholders.chats;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nikahtech.muslimnikah.databinding.ItemChatActiveBinding;
import com.nikahtech.muslimnikah.databinding.ItemChatBinding;
import com.nikahtech.muslimnikah.models.Chat;
import com.nikahtech.muslimnikah.models.Profile;

public class ChatsVH extends RecyclerView.ViewHolder {
    ItemChatBinding binding;

    public ChatsVH(ItemChatBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Chat chat) {

        Glide.with(binding.getRoot().getContext())
                .load(chat.getProfilePic())
                .into(binding.profilePicImg);

    }

}
