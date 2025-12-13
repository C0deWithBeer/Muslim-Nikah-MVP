package com.nikahtech.muslimnikah.viewholders.dashboard_section_viewholders;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.RecyclerView;

import com.nikahtech.muslimnikah.activities.ListProfilesActivity;
import com.nikahtech.muslimnikah.adapters.recyclerview_adapters.HorizontalProfilesAdapter;
import com.nikahtech.muslimnikah.adapters.recyclerview_adapters.skeleton.SkeletonHorizontalAdapter;
import com.nikahtech.muslimnikah.databinding.ItemDashNearbyBinding;
import com.nikahtech.muslimnikah.databinding.ItemDashRecentBinding;
import com.nikahtech.muslimnikah.models.Profile;
import com.nikahtech.muslimnikah.models.dashboard_sections.Nearby;
import com.nikahtech.muslimnikah.models.dashboard_sections.Recent;

import java.util.ArrayList;
import java.util.List;

public class NearbyVH extends RecyclerView.ViewHolder{

    ItemDashNearbyBinding binding;

    public NearbyVH(ItemDashNearbyBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Nearby model, Context context) {

        showSkeleton();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            List<Profile> profiles = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                profiles.add(new Profile("https://picsum.photos/200?1", "Aisha, 22"));
            }

            HorizontalProfilesAdapter realAdapter =
                    new HorizontalProfilesAdapter(context, profiles);

            binding.horizontalProfilesRv.setAdapter(realAdapter);

        }, 10000); // 10 seconds

        binding.viewAllTxt.setOnClickListener(view -> openListAllProfiles(context, "Nearby Profiles"));


    }

    private void openListAllProfiles(Context context, String heading) {
        Intent intent = new Intent(context, ListProfilesActivity.class);
        intent.putExtra("heading", heading);
        context.startActivity(intent);
    }


    private void showSkeleton() {
        SkeletonHorizontalAdapter skeletonAdapter = new SkeletonHorizontalAdapter(10);
        binding.horizontalProfilesRv.setAdapter(skeletonAdapter);
    }

}
