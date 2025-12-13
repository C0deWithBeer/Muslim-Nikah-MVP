package com.nikahtech.muslimnikah.models;

import java.time.Instant;

public class Notification {

    private String title;
    private String desc;
    private int type;
    private Instant time;

    public Notification(String title, String desc, int type, Instant time) {
        this.title = title;
        this.desc = desc;
        this.type = type;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }
}
