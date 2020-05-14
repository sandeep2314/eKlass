package com.example.eklass;

public class Designation
{
    public String DesignationId;
    public  String DName;
    public Integer HNo;
    public String dept;

    public Designation(String designationId, String DName, Integer HNo, String dept) {
        DesignationId = designationId;
        this.DName = DName;
        this.HNo = HNo;
        this.dept = dept;
    }

    public String getDesignationId() {
        return DesignationId;
    }

    public String getDName() {
        return DName;
    }

    public Integer getHNo() {
        return HNo;
    }

    public String getDept() {
        return dept;
    }
}
