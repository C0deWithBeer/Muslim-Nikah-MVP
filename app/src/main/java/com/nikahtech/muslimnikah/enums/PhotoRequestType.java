package com.nikahtech.muslimnikah.enums;

public enum PhotoRequestType {

    SENT(1),
    RECEIVED(2),
    ACCESSED(3);

    private final int type;

    PhotoRequestType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static PhotoRequestType fromType(int type) {
        for (PhotoRequestType t : values()) {
            if (t.type == type) return t;
        }
        throw new IllegalArgumentException("Invalid ConnectionRequestType: " + type);
    }
}
