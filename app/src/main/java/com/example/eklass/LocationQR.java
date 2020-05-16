package com.example.eklass;

public class LocationQR
{
    public String locationId;
    public String locationName;
    public String latitude;
    public String longitude;

    public LocationQR(String locationId, String locationName) {
        this.locationId = locationId;
        this.locationName = locationName;
    }

    public LocationQR(String locationId, String locationName, String latitude, String longitude) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getLocationName() {

        return locationName;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
