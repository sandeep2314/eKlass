package com.example.eklass;

public class StaffLocation
{
    private int SLId;
    private int LocationId;
    private String LocationName;
    private int WorkerId;
    private String WorkerName;
    private String WorkerDesignation;
    private String WorkerImage;
    private String ManagerDesignation;
    private int ManagerId;
    private String ManagerName;


    private int CompanyId;

    public StaffLocation(int SLId
            , int locationId, String locationName
            , int workerId, String workerName, String workerDesignation
            , String workerImage
            , int managerId, String managerName, String managerDesignation
            )
    {
        this.SLId = SLId;
        LocationId = locationId;
        LocationName = locationName;
        WorkerId = workerId;
        WorkerName = workerName;
        WorkerDesignation = workerDesignation;
        WorkerImage = workerImage;
        ManagerId = managerId;
        ManagerName = managerName;
        ManagerDesignation = managerDesignation;

    }


    public String getWorkerImage() {
        return WorkerImage;
    }

    public String getWorkerDesignation() {
        return WorkerDesignation;
    }

    public String getManagerDesignation() {
        return ManagerDesignation;
    }

    public int getSLId() {
        return SLId;
    }

    public String getLocationName() {
        return LocationName;
    }

    public String getWorkerName() {
        return WorkerName;
    }

    public String getManagerName() {
        return ManagerName;
    }

    public int getLocationId() {
        return LocationId;
    }

    public int getWorkerId() {
        return WorkerId;
    }

    public int getManagerId() {
        return ManagerId;
    }

    public int getCompanyId() {
        return CompanyId;
    }
}
