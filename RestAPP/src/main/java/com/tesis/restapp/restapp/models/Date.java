package com.tesis.restapp.restapp.models;

/**
 * Created by feresr on 5/26/14.
 */
public class Date {

    private String date;
    private int timezone_type;
    private String UTC;

    public String getUTC() {
        return UTC;
    }

    public void setUTC(String UTC) {
        this.UTC = UTC;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTimezone_type() {
        return timezone_type;
    }

    public void setTimezone_type(int timezone_type) {
        this.timezone_type = timezone_type;
    }



}
