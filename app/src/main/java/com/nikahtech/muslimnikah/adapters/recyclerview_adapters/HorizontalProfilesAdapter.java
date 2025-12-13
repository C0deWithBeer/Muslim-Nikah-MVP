package com.nikahtech.muslimnikah.adapters.recyclerview_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikahtech.muslimnikah.databinding.ItemHorizontalProfileBinding;
import com.nikahtech.muslimnikah.models.Profile;
import com.nikahtech.muslimnikah.viewholders.dashboard_section_viewholders.profiles.HorizontalProfilesVH;

import java.util.List;

public class HorizontalProfilesAdapter extends RecyclerView.Adapter<HorizontalProfilesVH> {

    private final Context context;
    private final List<Profile> profiles;

    public HorizontalProfilesAdapter(Context context, List<Profile> profiles) {
        this.context = context;
        this.profiles = profiles;
    }

    @NonNull
    @Override
    public HorizontalProfilesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemHorizontalProfileBinding binding =
                ItemHorizontalProfileBinding.inflate(inflater, parent, false);
        return new HorizontalProfilesVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProfilesVH holder, int position) {
        holder.bind(profiles.get(position).getProfilePic(), profiles.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }
}

