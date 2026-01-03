package com.nikahtech.muslimnikah.viewholders.dashboard_section_viewholders;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.RecyclerView;

import com.nikahtech.muslimnikah.activities.ListProfilesActivity;
import com.nikahtech.muslimnikah.adapters.recyclerview_adapters.VerticalProfilesAdapter;
import com.nikahtech.muslimnikah.adapters.recyclerview_adapters.skeleton.SkeletonVerticalAdapter;
import com.nikahtech.muslimnikah.databinding.ItemDashPremiumBinding;
import com.nikahtech.muslimnikah.models.Profile;
import com.nikahtech.muslimnikah.models.dashboard_sections.Premium;

import java.util.ArrayList;
import java.util.List;

public class PremiumVH extends RecyclerView.ViewHolder{

    ItemDashPremiumBinding binding;

    public PremiumVH(ItemDashPremiumBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Premium model, Context context) {

        showSkeleton();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            List<Profile> profiles = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                profiles.add(new Profile("https://picsum.photos/200?1", "Aisha, 22"));
            }

            VerticalProfilesAdapter realAdapter =
                    new VerticalProfilesAdapter(context, profiles);

            binding.verticalProfilesRv.setAdapter(realAdapter);

        }, 10000); // 10 seconds

        binding.viewAllTxt.setOnClickListener(view -> openListAllProfiles(context, "Premium Profiles"));


    }

    private void openListAllProfiles(Context context, String heading) {
        // navigate to it
        Intent intent = new Intent(context, ListProfilesActivity.class);
        intent.putExtra("heading", heading);
        context.startActivity(intent);
    }

    private void showSkeleton() {
        SkeletonVerticalAdapter skeletonAdapter = new SkeletonVerticalAdapter(10);
        binding.verticalProfilesRv.setAdapter(skeletonAdapter);
    }

}
