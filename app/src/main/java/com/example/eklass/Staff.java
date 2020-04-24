package com.example.eklass;

public class Staff
{
    public String StaffId;
    public String StaffName;
    // admin, Guard, manager
    public String StaffType;
    public String CompanyId;
    public String CompanyName;
    public String MobileNo;

    public Staff(String staffId, String staffName
            , String staffType, String companyId, String companyName) {
        StaffId = staffId;
        StaffName = staffName;
        StaffType = staffType;
        CompanyId = companyId;
        CompanyName = companyName;

    }

    public String getStaffId() {
        return StaffId;
    }

    public String getStaffName() {
        return StaffName;
    }

    public String getStaffType() {
        return StaffType;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public String getMobileNo() {
        return MobileNo;
    }
}
