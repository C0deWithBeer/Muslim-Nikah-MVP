package com.nikahtech.muslimnikah.adapters.recyclerview_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikahtech.muslimnikah.databinding.ItemHorizontalProfileBinding;
import com.nikahtech.muslimnikah.databinding.ItemVerticalProfileBinding;
import com.nikahtech.muslimnikah.models.Profile;
import com.nikahtech.muslimnikah.viewholders.dashboard_section_viewholders.profiles.HorizontalProfilesVH;
import com.nikahtech.muslimnikah.viewholders.dashboard_section_viewholders.profiles.VerticalProfilesVH;

import java.util.List;

public class VerticalProfilesAdapter extends RecyclerView.Adapter<VerticalProfilesVH> {

    private final Context context;
    private final List<Profile> profiles;

    public VerticalProfilesAdapter(Context context, List<Profile> profiles) {
        this.context = context;
        this.profiles = profiles;
    }

    @NonNull
    @Override
    public VerticalProfilesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemVerticalProfileBinding binding =
                ItemVerticalProfileBinding.inflate(inflater, parent, false);
        return new VerticalProfilesVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalProfilesVH holder, int position) {
        holder.bind(profiles.get(position).getProfilePic(), profiles.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }
}

