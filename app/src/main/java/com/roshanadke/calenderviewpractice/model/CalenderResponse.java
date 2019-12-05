package com.roshanadke.calenderviewpractice.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CalenderResponse {
    @SerializedName("present")
    private List<Present> present = null;
    @SerializedName("apsent")
    private List<Absent> apsent = null;
    @SerializedName("pandding")
    private List<Pandding> pandding = null;
    @SerializedName("not_checkout")
    private List<NotCheckout> notCheckout = null;

    @SerializedName("holiday")
    private List<Holiday> holiday = null;


    @SerializedName("num_present")
    private Integer numPresent;
    @SerializedName("num_apsent")
    private Integer numApsent;
    @SerializedName("num_pandding")
    private Integer numPandding;
    @SerializedName("num_not_checkout")
    private Integer numNotCheckout;
    @SerializedName("num_found")
    private Integer numFound;

    public List<Present> getPresent() {
        return present;
    }

    public void setPresent(List<Present> present) {
        this.present = present;
    }

    public List<Absent> getApsent() {
        return apsent;
    }

    public void setApsent(List<Absent> apsent) {
        this.apsent = apsent;
    }

    public List<Pandding> getPandding() {
        return pandding;
    }

    public void setPandding(List<Pandding> pandding) {
        this.pandding = pandding;
    }

    public List<NotCheckout> getNotCheckout() {
        return notCheckout;
    }

    public void setNotCheckout(List<NotCheckout> notCheckout) {
        this.notCheckout = notCheckout;
    }

    public Integer getNumPresent() {
        return numPresent;
    }

    public void setNumPresent(Integer numPresent) {
        this.numPresent = numPresent;
    }

    public Integer getNumApsent() {
        return numApsent;
    }

    public void setNumApsent(Integer numApsent) {
        this.numApsent = numApsent;
    }

    public Integer getNumPandding() {
        return numPandding;
    }

    public void setNumPandding(Integer numPandding) {
        this.numPandding = numPandding;
    }

    public Integer getNumNotCheckout() {
        return numNotCheckout;
    }

    public void setNumNotCheckout(Integer numNotCheckout) {
        this.numNotCheckout = numNotCheckout;
    }

    public Integer getNumFound() {
        return numFound;
    }

    public void setNumFound(Integer numFound) {
        this.numFound = numFound;
    }

    public List<Holiday> getHoliday() {
        return holiday;
    }

    public void setHoliday(List<Holiday> holiday) {
        this.holiday = holiday;
    }
}
