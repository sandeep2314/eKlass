package com.example.eklass;

public class LocationQR
{
    public String locationId;
    public String locationName;

    public LocationQR(String locationId, String locationName) {
        this.locationId = locationId;
        this.locationName = locationName;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getLocationName() {
        return locationName;
    }
}
