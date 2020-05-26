package com.example.eklass;

public class Staff
{
    public String StaffId;
    public String StaffName;
    // admin, Guard, manager

    public String Designation;
    public String CompanyId;
    private String CompanyName;
    private String MobileNo;
    private String StaffImage;

    public Staff(String staffId, String staffName
            , String designation
            , String mobileNo
            , String companyId, String companyName) {
        StaffId = staffId;
        StaffName = staffName;
        Designation = designation;
        CompanyId = companyId;
        CompanyName = companyName;
        MobileNo = mobileNo;

    }

    public Staff(String staffId, String staffName
            , String designation
            , String mobileNo
            , String companyId, String companyName, String staffImage) {
        StaffId = staffId;
        StaffName = staffName;
        Designation = designation;
        CompanyId = companyId;
        CompanyName = companyName;
        MobileNo = mobileNo;
        StaffImage = staffImage;

    }



    public String getStaffId() {
        return StaffId;
    }

    public String getStaffName() {
        return StaffName;
    }

    public String getDesignation() {
        return Designation;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public String getStaffImage() {
        return StaffImage;
    }
}
