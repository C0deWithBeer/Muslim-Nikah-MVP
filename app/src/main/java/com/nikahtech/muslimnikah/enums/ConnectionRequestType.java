package com.nikahtech.muslimnikah.enums;

public enum ConnectionRequestType {

    SENT(1),
    RECEIVED(2),
    CONNECTED(3);

    private final int type;

    ConnectionRequestType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static ConnectionRequestType fromType(int type) {
        for (ConnectionRequestType t : values()) {
            if (t.type == type) return t;
        }
        throw new IllegalArgumentException("Invalid ConnectionRequestType: " + type);
    }
}
