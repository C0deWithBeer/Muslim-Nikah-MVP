package com.nikahtech.muslimnikah.models.dashboard_sections;

import com.nikahtech.muslimnikah.constants.DashboardSectionType;
import com.nikahtech.muslimnikah.interfaces.DashboardSection;

public class DailyMatches implements DashboardSection {

    private String userId;
    private String profileId;

    public DailyMatches(String userId, String profileId) {
        this.userId = userId;
        this.profileId = profileId;
    }

    @Override
    public int getType() {
        return DashboardSectionType.DAILY_MATCHES;
    }
}
