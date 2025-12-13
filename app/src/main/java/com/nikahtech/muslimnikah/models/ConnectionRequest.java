package com.nikahtech.muslimnikah.models;

import com.nikahtech.muslimnikah.enums.ConnectionRequestType;

import java.time.Instant;

public class ConnectionRequest {

    private String profileId;
    private String profilePic;
    private String name;
    private Instant time;
    private ConnectionRequestType connectionRequestType;

    public ConnectionRequest(String profileId, String profilePic, String name,
                             Instant time, ConnectionRequestType type) {
        this.profileId = profileId;
        this.profilePic = profilePic;
        this.name = name;
        this.time = time;
        this.connectionRequestType = type;
    }

    public String getProfileId() { return profileId; }
    public String getProfilePic() { return profilePic; }
    public String getName() { return name; }
    public Instant getTime() { return time; }
    public ConnectionRequestType getConnectionRequestType() { return connectionRequestType; }
}
