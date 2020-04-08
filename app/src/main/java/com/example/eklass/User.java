package com.example.eklass;

public class User
{

    public String UserMobileNo;
    public String UserPassword;
    public String DeviceId;
    public Student UserChild;


    public User(String userMobileNo, Student student) {
        UserMobileNo = userMobileNo;
        UserChild = student;

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


}
