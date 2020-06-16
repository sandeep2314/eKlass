package com.example.eklass;

public class Staff
{
    public String StaffId;
    public String StaffName;
    public String StaffPassword;
    public int DesignationId;
    public String Designation;
    public String CompanyId;
    private String CompanyName;
    private String MobileNo;
    private int IsActive;
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

    public Staff(String staffId
            , String staffName
            , String staffPassword
            , int designationId
            , String designation
            , String mobileNo
            , String companyId
            , String companyName
            , int isActive
            , String staffImage)
    {
        StaffId = staffId;
        StaffName = staffName;
        StaffPassword = staffPassword;
        DesignationId = designationId;
        Designation = designation;
        CompanyId = companyId;
        CompanyName = companyName;
        MobileNo = mobileNo;
        IsActive = isActive;
        StaffImage = staffImage;

    }

    public int getIsActive() {
        return IsActive;
    }

    public String getStaffPassword() {
        return StaffPassword;
    }

    public int getDesignationId() {
        return DesignationId;
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
