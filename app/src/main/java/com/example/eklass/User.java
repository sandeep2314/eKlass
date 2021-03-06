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

    public String DesignationId;
    public String DesignationName;
    public String CompanyId;
    public String CompanyName;
    public int UserTheme;
    public int PostType;


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

    public User(String userMobileNo, String staffType, String staffId
            , String staffName
            , String designationId
            , String designationName
            , String companyId
            , String companyName
            , int userTheme, int postType ) {
        UserMobileNo = userMobileNo;
        StaffType = staffType;
        StaffId = staffId;
        StaffName = staffName;
        DesignationId = designationId;
        DesignationName = designationName;
        CompanyId = companyId;
        CompanyName = companyName;
        UserTheme = userTheme;
        PostType = postType;
    }




    public String getDesignationId() {
        return DesignationId;
    }

    public String getDesignationName() {
        return DesignationName;
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

    public int getUserTheme() {
        return UserTheme;
    }

    public void setPostType(int postType) {
        PostType = postType;
    }

    public int getPostType() {
        return PostType;
    }

    public void setUserTheme(int userTheme) {
        UserTheme = userTheme;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }
}
