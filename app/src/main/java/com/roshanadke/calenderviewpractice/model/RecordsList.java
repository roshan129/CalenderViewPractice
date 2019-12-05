package com.roshanadke.calenderviewpractice.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecordsList {


    @SerializedName("event")
    private List<Event1> event = null;
    @SerializedName("summary")
    private List<Summary> summary = null;
    @SerializedName("num_summary")
    private Integer numSummary;

    public List<Event1> getEvent() {
        return event;
    }

    public void setEvent(List<Event1> event) {
        this.event = event;
    }

    public List<Summary> getSummary() {
        return summary;
    }

    public void setSummary(List<Summary> summary) {
        this.summary = summary;
    }

    public Integer getNumSummary() {
        return numSummary;
    }

    public void setNumSummary(Integer numSummary) {
        this.numSummary = numSummary;
    }

}
