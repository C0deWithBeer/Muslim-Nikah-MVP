package com.nikahtech.muslimnikah.Backend.dto.eto;

public class SubscriptionDto {
    private Long id;
    private String plan;
    private String badge;
    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }


    @Override
    public String toString() {
        return "SubscriptionDto{" +
                "id=" + id +
                ", plan='" + plan + '\'' +
                ", badge='" + badge + '\'' +
                ", active=" + active +
                '}';
    }
}
