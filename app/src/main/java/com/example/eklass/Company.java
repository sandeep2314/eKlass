package com.example.eklass;

public class Company
{
    public Integer CompanyID;
    public String CompanyName;

    public Company(Integer companyID, String companyName) {
        CompanyID = companyID;
        CompanyName = companyName;
    }

    public Integer getCompanyID() {
        return CompanyID;
    }

    public String getCompanyName() {
        return CompanyName;
    }
}
