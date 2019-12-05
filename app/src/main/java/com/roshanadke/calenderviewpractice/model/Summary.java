package com.roshanadke.calenderviewpractice.model;

import com.google.gson.annotations.SerializedName;

public class Summary {

    @SerializedName("area_name")
    private String areaName;
    @SerializedName("checkin_distance")
    private String checkinDistance;
    @SerializedName("checkout_distance")
    private Object checkoutDistance;
    @SerializedName("date")
    private String date;
    @SerializedName("checkin_time")
    private String checkinTime;
    @SerializedName("checkout_time")
    private String checkoutTime;
    @SerializedName("diff")
    private String diff;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCheckinDistance() {
        return checkinDistance;
    }

    public void setCheckinDistance(String checkinDistance) {
        this.checkinDistance = checkinDistance;
    }

    public Object getCheckoutDistance() {
        return checkoutDistance;
    }

    public void setCheckoutDistance(Object checkoutDistance) {
        this.checkoutDistance = checkoutDistance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(String checkinTime) {
        this.checkinTime = checkinTime;
    }

    public String getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(String checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }
}
