package com.example.eklass;

public class DEY {

    public String INTIME;
    public String OUTTIME;
    public String hrs;
    public String INTIME_lat;
    public String OUTTIME_lat;
    public String INTIME_long;
    public String OUTTIME_long;
    public String INTIME_location;
    public String OUTTIME_location;

    public DEY(String INTIME, String OUTTIME, String hrs, String INTIME_lat, String OUTTIME_lat, String INTIME_long, String OUTTIME_long, String INTIME_location, String OUTTIME_location) {
        this.INTIME = INTIME;
        this.OUTTIME = OUTTIME;
        this.hrs = hrs;
        this.INTIME_lat = INTIME_lat;
        this.OUTTIME_lat = OUTTIME_lat;
        this.INTIME_long = INTIME_long;
        this.OUTTIME_long = OUTTIME_long;
        this.INTIME_location = INTIME_location;
        this.OUTTIME_location = OUTTIME_location;
    }

    public String getINTIME() {
        return INTIME;
    }

    public String getOUTTIME() {
        return OUTTIME;
    }

    public String getHrs() {
        return hrs;
    }

    public String getINTIME_lat() {
        return INTIME_lat;
    }

    public String getOUTTIME_lat() {
        return OUTTIME_lat;
    }

    public String getINTIME_long() {
        return INTIME_long;
    }

    public String getOUTTIME_long() {
        return OUTTIME_long;
    }

    public String getINTIME_location() {
        return INTIME_location;
    }

    public String getOUTTIME_location() {
        return OUTTIME_location;
    }
}
