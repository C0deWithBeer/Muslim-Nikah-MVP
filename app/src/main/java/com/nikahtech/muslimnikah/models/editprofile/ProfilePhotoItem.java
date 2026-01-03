package com.nikahtech.muslimnikah.models.editprofile;

public class ProfilePhotoItem {

    public static final int TYPE_PHOTO = 1;
    public static final int TYPE_ADD = 2;

    private final String imageUrl;
    private final int type;

    public ProfilePhotoItem(String imageUrl, int type) {
        this.imageUrl = imageUrl;
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getType() {
        return type;
    }
}

