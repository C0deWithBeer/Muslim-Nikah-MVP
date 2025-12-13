package com.nikahtech.muslimnikah.viewholders.notification_viewholder;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nikahtech.muslimnikah.databinding.ItemNotificationBinding;
import com.nikahtech.muslimnikah.models.Notification;

public class NotificationVH extends RecyclerView.ViewHolder {

    ItemNotificationBinding binding;

    public NotificationVH(ItemNotificationBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Notification notification, Context context) {
        binding.titleTxt.setText(notification.getTitle());
        binding.descTxt.setText(notification.getDesc());
    }
}
