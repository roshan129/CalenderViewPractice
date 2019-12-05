package com.roshanadke.calenderviewpractice.model;

import com.google.gson.annotations.SerializedName;

public class Event1 {

    @SerializedName("holiday_name")
    private String holidayName;
    @SerializedName("holiday_date")
    private String holidayDate;
    @SerializedName("holiday_to_date")
    private String holidayToDate;
    @SerializedName("holiday_type")
    private String holidayType;
    @SerializedName("department")
    private String department;

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public String getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(String holidayDate) {
        this.holidayDate = holidayDate;
    }

    public String getHolidayToDate() {
        return holidayToDate;
    }

    public void setHolidayToDate(String holidayToDate) {
        this.holidayToDate = holidayToDate;
    }

    public String getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(String holidayType) {
        this.holidayType = holidayType;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
