package com.example.eklass;

public class User
{

    public String UserMobileNo;
    public String UserPassword;
    public String DeviceId;

    public User(String userMobileNo, String userPassword) {
        UserMobileNo = userMobileNo;
        UserPassword = userPassword;
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
