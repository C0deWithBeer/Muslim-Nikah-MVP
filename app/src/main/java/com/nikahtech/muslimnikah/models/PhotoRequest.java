package com.nikahtech.muslimnikah.models;

import com.nikahtech.muslimnikah.enums.PhotoRequestType;

import java.time.Instant;

public class PhotoRequest {

    String profileId;
    String profilePic;
    String name;
    String mid;
    Instant time;
    PhotoRequestType type;

    public PhotoRequest(String profileId, String profilePic, String name, String mid, Instant time, PhotoRequestType type) {
        this.profileId = profileId;
        this.profilePic = profilePic;
        this.name = name;
        this.mid = mid;
        this.time = time;
        this.type = type;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public PhotoRequestType getType() {
        return type;
    }

    public void setType(PhotoRequestType type) {
        this.type = type;
    }
}
