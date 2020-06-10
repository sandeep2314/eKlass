package com.example.eklass;

public class StaffLocation
{
    private int SLId;
    private int LocationId;
    private String LocationName;
    private int WorkerId;
    private String WorkerName;
    private int ManagerId;
    private String ManagerName;
    private int CompanyId;

    public StaffLocation(int SLId
            , int locationId, String locationName
            , int workerId, String workerName
            , int managerId, String managerName
            )
    {
        this.SLId = SLId;
        LocationId = locationId;
        LocationName = locationName;
        WorkerId = workerId;
        WorkerName = workerName;
        ManagerId = managerId;
        ManagerName = managerName;

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
