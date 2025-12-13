package com.nikahtech.muslimnikah.models.dashboard_sections;

import com.nikahtech.muslimnikah.constants.DashboardSectionType;
import com.nikahtech.muslimnikah.interfaces.DashboardSection;

public class Recent implements DashboardSection {

    private String userId;
    private String profileId;

    public Recent(String userId, String profileId) {
        this.userId = userId;
        this.profileId = profileId;
    }

    @Override
    public int getType() {
        return DashboardSectionType.RECENT;
    }
}
