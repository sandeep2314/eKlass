package com.example.eklass;

public class Student
{
    public String StudentId;
    public String StudentName;
    public String StudentClass;
    public String MobileNo;

    public Student(String studentID, String studentName, String studentClass, String mobileNo) {
        StudentId = studentID;
        StudentName = studentName;
        StudentClass = studentClass;
        MobileNo = mobileNo;
    }

    public String getStudentId() {
        return StudentId;
    }

    public String getStudentName() {
        return StudentName;
    }

    public String getStudentClass() {
        return StudentClass;
    }

    public String getMobileNo() {
        return MobileNo;
    }
}
