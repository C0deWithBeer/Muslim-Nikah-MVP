package com.nikahtech.muslimnikah.adapters.recyclerview_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikahtech.muslimnikah.databinding.ItemDashHeaderBinding;
import com.nikahtech.muslimnikah.databinding.ItemNotificationBinding;
import com.nikahtech.muslimnikah.models.Notification;
import com.nikahtech.muslimnikah.models.dashboard_sections.Header;
import com.nikahtech.muslimnikah.viewholders.dashboard_section_viewholders.HeaderVH;
import com.nikahtech.muslimnikah.viewholders.notification_viewholder.NotificationVH;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Notification> notifications;

    public NotificationAdapter(Context context, List<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @Override
    public int getItemViewType(int position) {
        return notifications.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemNotificationBinding notificationBinding =
                ItemNotificationBinding.inflate(inflater, parent, false);
        return new NotificationVH(notificationBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NotificationVH notificationVH = (NotificationVH) holder;
        notificationVH.bind(notifications.get(position), context);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}
