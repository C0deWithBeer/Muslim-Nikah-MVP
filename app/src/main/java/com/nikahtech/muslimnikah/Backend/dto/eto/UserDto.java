package com.nikahtech.muslimnikah.Backend.dto.eto;

import java.util.List;

public class UserDto {
    private Long id;
    private String phoneNumber;
    private String countryCode;
    private List<Long> profileIds;
    private Long selectedProfileId;
    private SubscriptionDto subscription;

    public Long getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public List<Long> getProfileIds() {
        return profileIds;
    }

    public Long getSelectedProfileId() {
        return selectedProfileId;
    }

    public SubscriptionDto getSubscription() {
        return subscription;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", profileIds=" + profileIds +
                ", selectedProfileId=" + selectedProfileId +
                ", subscription=" + subscription +
                '}';
    }
}
