package com.nikahtech.muslimnikah.adapters.recyclerview_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikahtech.muslimnikah.constants.DashboardSectionType;
import com.nikahtech.muslimnikah.databinding.ItemDashDailymatchesBinding;
import com.nikahtech.muslimnikah.databinding.ItemDashHeaderBinding;
import com.nikahtech.muslimnikah.databinding.ItemDashNearbyBinding;
import com.nikahtech.muslimnikah.databinding.ItemDashPremiumBinding;
import com.nikahtech.muslimnikah.databinding.ItemDashRecentBinding;
import com.nikahtech.muslimnikah.interfaces.DashboardSection;
import com.nikahtech.muslimnikah.models.dashboard_sections.DailyMatches;
import com.nikahtech.muslimnikah.models.dashboard_sections.Header;
import com.nikahtech.muslimnikah.models.dashboard_sections.Nearby;
import com.nikahtech.muslimnikah.models.dashboard_sections.Premium;
import com.nikahtech.muslimnikah.models.dashboard_sections.Recent;
import com.nikahtech.muslimnikah.viewholders.dashboard_section_viewholders.DailyMatchesVH;
import com.nikahtech.muslimnikah.viewholders.dashboard_section_viewholders.HeaderVH;
import com.nikahtech.muslimnikah.viewholders.dashboard_section_viewholders.NearbyVH;
import com.nikahtech.muslimnikah.viewholders.dashboard_section_viewholders.PremiumVH;
import com.nikahtech.muslimnikah.viewholders.dashboard_section_viewholders.RecentVH;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<DashboardSection> items;
    private final Context context;

    public DashboardAdapter(Context context, List<DashboardSection> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {

            case DashboardSectionType.HEADER:
                ItemDashHeaderBinding headerBinding =
                        ItemDashHeaderBinding.inflate(inflater, parent, false);
                return new HeaderVH(headerBinding);

            case DashboardSectionType.DAILY_MATCHES:
                ItemDashDailymatchesBinding dailyMatchesBinding =
                        ItemDashDailymatchesBinding.inflate(inflater, parent, false);
                return new DailyMatchesVH(dailyMatchesBinding);

            case DashboardSectionType.RECENT:
                ItemDashRecentBinding recentBinding =
                        ItemDashRecentBinding.inflate(inflater, parent, false);
                return new RecentVH(recentBinding);

            case DashboardSectionType.PREMIUM:
                ItemDashPremiumBinding premiumBinding =
                        ItemDashPremiumBinding.inflate(inflater, parent, false);
                return new PremiumVH(premiumBinding);

            case DashboardSectionType.NEARBY:
                ItemDashNearbyBinding nearbyBinding =
                        ItemDashNearbyBinding.inflate(inflater, parent, false);
                return new NearbyVH(nearbyBinding);

            default:
                throw new IllegalArgumentException("Unknown view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        DashboardSection item = items.get(position);

        switch (item.getType()) {
            case DashboardSectionType.HEADER:
                HeaderVH headerVH = (HeaderVH) holder;
                headerVH.bind((Header) item, context);
                break;

            case DashboardSectionType.DAILY_MATCHES:
                DailyMatchesVH dailyMatchesVH = (DailyMatchesVH) holder;
                dailyMatchesVH.bind((DailyMatches) item, context);
                break;

            case DashboardSectionType.RECENT:
                RecentVH recentVH = (RecentVH) holder;
                recentVH.bind((Recent) item, context);
                break;

            case DashboardSectionType.PREMIUM:
                PremiumVH premiumVH = (PremiumVH) holder;
                premiumVH.bind((Premium) item, context);
                break;

            case DashboardSectionType.NEARBY:
                NearbyVH nearbyVH = (NearbyVH) holder;
                nearbyVH.bind((Nearby) item, context);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
