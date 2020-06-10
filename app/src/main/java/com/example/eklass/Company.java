package com.example.eklass;

public class Company
{
    public Integer CompanyID;
    public String CompanyName;
    private String CompanyLogo;

    public Company(Integer companyID, String companyName) {
        CompanyID = companyID;
        CompanyName = companyName;
    }

    public Company(Integer companyID, String companyName, String companyLogo) {
        CompanyID = companyID;
        CompanyName = companyName;
        CompanyLogo = companyLogo;
    }

    public Integer getCompanyID() {
        return CompanyID;
    }

    public String getCompanyLogo() {
        return CompanyLogo;
    }

    public String getCompanyName() {
        return CompanyName;
    }
}
