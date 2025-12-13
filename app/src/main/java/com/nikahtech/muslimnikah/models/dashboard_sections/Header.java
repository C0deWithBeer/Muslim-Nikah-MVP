package com.nikahtech.muslimnikah.models.dashboard_sections;

import com.nikahtech.muslimnikah.constants.DashboardSectionType;
import com.nikahtech.muslimnikah.interfaces.DashboardSection;

public class Header implements DashboardSection {

    public final String profilePic;
    public final String name;

    public Header(String name, String profilePic) {
        this.name = name;
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    @Override
    public int getType() {
        return DashboardSectionType.HEADER;
    }
}
