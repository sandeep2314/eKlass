package com.example.eklass;

import java.util.Date;

public class Duty
{
    public String DutyId;
    public String DutyDateTime;
    public String QRId;
    public String LocationName;
    public String GuardId;
    public String GuardName;
    public String Latitude;
    public String Longitude;
    private int IsScan;
    private int PostType;
    private String InTime;
    private String OutTime;
    private int WorkingHrs;

    public String CompanyId;

    public Duty(String dutyId, String dutyDateTime, String QRId
            , String locationName, String guardId, String guardName
            , String latitude, String longitude
            , int isScan
            , int postType
            , String companyId
            , String inTime
            , String outTime
            , int workingHrs

    ) {
        DutyId = dutyId;
        DutyDateTime = dutyDateTime;
        this.QRId = QRId;
        LocationName = locationName;
        GuardId = guardId;
        GuardName = guardName;
        Latitude = latitude;
        Longitude = longitude;
        IsScan = isScan;
        PostType = postType;
        CompanyId = companyId;
        InTime = inTime;
        OutTime = outTime;
        WorkingHrs = workingHrs;
    }

    public int getIsScan() {
        return IsScan;
    }

    public int getPostType() {
        return PostType;
    }

    public String getDutyId() {
        return DutyId;
    }

    public String getDutyDateTime() {
        return DutyDateTime;
    }

    public String getQRId() {
        return QRId;
    }

    public String getLocationName() {
        return LocationName;
    }

    public String getGuardId() {
        return GuardId;
    }

    public String getGuardName() {
        return GuardName;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getInTime() {
        return InTime;
    }

    public String getOutTime() {
        return OutTime;
    }

    public int getWorkingHrs() {
        return WorkingHrs;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getCompanyId() {
        return CompanyId;
    }



}

