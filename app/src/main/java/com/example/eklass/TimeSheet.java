package com.example.eklass;

public class TimeSheet {

    public String StaffId;
    public String StaffName;
    public String Designation;
    public String Hierarchy;

    public DEY DAY1;
    public DEY DAY26;

    public TimeSheet(String staffId, String staffName, String designation, String hierarchy, DEY DAY1, DEY DAY26) {
        StaffId = staffId;
        StaffName = staffName;
        Designation = designation;
        Hierarchy = hierarchy;
        this.DAY1 = DAY1;
        this.DAY26 = DAY26;
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

    public String getHierarchy() {
        return Hierarchy;
    }

    public DEY getDAY1() {
        return DAY1;
    }

    public DEY getDAY26() {
        return DAY26;
    }
}
