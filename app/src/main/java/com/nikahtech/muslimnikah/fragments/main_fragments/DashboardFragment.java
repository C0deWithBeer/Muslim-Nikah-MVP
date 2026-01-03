package com.nikahtech.muslimnikah.fragments.main_fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nikahtech.muslimnikah.Backend.dto.eto.ProfileDto;
import com.nikahtech.muslimnikah.activities.ConnectionActivity;
import com.nikahtech.muslimnikah.activities.NotificationActivity;
import com.nikahtech.muslimnikah.activities.PhotoRequestActivity;
import com.nikahtech.muslimnikah.adapters.recyclerview_adapters.DashboardAdapter;
import com.nikahtech.muslimnikah.databinding.FragmentDashboardBinding;
import com.nikahtech.muslimnikah.interfaces.DashboardSection;
import com.nikahtech.muslimnikah.keystores.LocalDBManager;
import com.nikahtech.muslimnikah.models.dashboard_sections.DailyMatches;
import com.nikahtech.muslimnikah.models.dashboard_sections.Header;
import com.nikahtech.muslimnikah.models.dashboard_sections.Nearby;
import com.nikahtech.muslimnikah.models.dashboard_sections.Premium;
import com.nikahtech.muslimnikah.models.dashboard_sections.Recent;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    FragmentDashboardBinding binding;
    Context context;

    private String name;
    private String profileImageUrl;
    private Long userId;
    private Long profileId;


    public DashboardFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        context = requireContext();

        if (LocalDBManager.getProfile(requireContext()) != null) {
            profileImageUrl = LocalDBManager.getProfile(context).getProfilePic();
            name = LocalDBManager.getProfile(context).getName();
        }

        setupDashboard();
        binding.connectionsOpt.setOnClickListener(view -> openConnections());
        binding.notificationOpt.setOnClickListener(view -> openNotifications());
        binding.photoReqOpt.setOnClickListener(view -> openPhotoRequests());

        return binding.getRoot();
    }

    private void openPhotoRequests() {
        Intent intent = new Intent(requireContext(), PhotoRequestActivity.class);
        startActivity(intent);
    }

    private void openNotifications() {
        Intent intent = new Intent(requireContext(), NotificationActivity.class);
        startActivity(intent);
    }

    private void openConnections() {
        Intent intent = new Intent(requireContext(), ConnectionActivity.class);
        startActivity(intent);
    }

    private void setupDashboard() {
        // Create list
        List<DashboardSection> items = new ArrayList<>();

        items.add(new Header(
                name,
                profileImageUrl
        ));

        items.add(new DailyMatches(
                "1",
                "1"
        ));

        items.add(new Recent(
                "1",
                "1"
        ));

        items.add(new Premium(
                "1",
                "1"
        ));

        items.add(new Nearby(
                "1",
                "1"
        ));

        // Attach adapter
        DashboardAdapter adapter = new DashboardAdapter(requireContext(), items);
        binding.mainRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.mainRc.setAdapter(adapter);
    }
}