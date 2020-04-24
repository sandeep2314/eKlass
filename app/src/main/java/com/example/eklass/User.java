package com.example.eklass;

import java.security.PublicKey;

public class User
{

    public String UserMobileNo;
    public String UserPassword;
    public String DeviceId;
    public Student UserChild;
    public Customer UserCutomer;

    public String StaffType;
    public String StaffId;
    public String StaffName;
    public String CompanyId;
    public String CompanyName;


    public Staff UserStaff;


    public Staff getUserStaff() {
        return UserStaff;
    }

    public User(String userMobileNo, Student student) {
        UserMobileNo = userMobileNo;
        UserChild = student;

    }

    public User(String userMobileNo, Staff staff) {
        UserMobileNo = userMobileNo;
        UserStaff = staff;

    }

    public User(String userMobileNo, String staffType) {
        UserMobileNo = userMobileNo;
        StaffType = staffType;

    }



    public User(String userMobileNo, Customer userCutomer) {
        UserMobileNo = userMobileNo;
        UserCutomer = userCutomer;
    }

    public User(String userMobileNo) {
        UserMobileNo = userMobileNo;


    }


    public Student getUserChild()
    {
        return UserChild;
    }

    public String getUserMobileNo() {
        return UserMobileNo;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public User(String userMobileNo, String staffType, String staffId, String staffName, String companyId, String companyName) {
        UserMobileNo = userMobileNo;
        StaffType = staffType;
        StaffId = staffId;
        StaffName = staffName;
        CompanyId = companyId;
        CompanyName = companyName;
    }

    public String getStaffType() {
        return StaffType;
    }

    public String getStaffId() {
        return StaffId;
    }

    public String getStaffName() {
        return StaffName;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public String getCompanyName() {
        return CompanyName;
    }
}
