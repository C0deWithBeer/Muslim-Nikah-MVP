package com.nikahtech.muslimnikah.adapters.recyclerview_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikahtech.muslimnikah.databinding.ItemChatActiveBinding;
import com.nikahtech.muslimnikah.databinding.ItemHorizontalProfileBinding;
import com.nikahtech.muslimnikah.models.Profile;
import com.nikahtech.muslimnikah.viewholders.chats.ActiveUsersVH;
import com.nikahtech.muslimnikah.viewholders.dashboard_section_viewholders.profiles.HorizontalProfilesVH;

import java.util.List;

public class ActiveUsersAdapter extends RecyclerView.Adapter<ActiveUsersVH> {

    Context context;
    List<Profile> profiles;

    public ActiveUsersAdapter(Context context, List<Profile> profiles) {
        this.context = context;
        this.profiles = profiles;
    }

    @NonNull
    @Override
    public ActiveUsersVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemChatActiveBinding binding =
                ItemChatActiveBinding.inflate(inflater, parent, false);
        return new ActiveUsersVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveUsersVH holder, int position) {
        holder.bind(profiles.get(position));
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }
}
