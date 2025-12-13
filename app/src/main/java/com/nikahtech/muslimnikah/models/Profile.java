package com.nikahtech.muslimnikah.models;

public class Profile {
    private String profilePic;
    private String name;

    public Profile() {
    }

    public Profile(String profilePic, String name) {
        this.profilePic = profilePic;
        this.name = name;
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
}
