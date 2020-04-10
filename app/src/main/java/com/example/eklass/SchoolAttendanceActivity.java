package com.example.eklass;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SchoolAttendanceActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolattendance);

        List<Attendance> attendancesList;
        RecyclerView recyclerView;




        recyclerView =  findViewById(R.id.rvSchoolAttendance);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        attendancesList = new ArrayList<>();

        attendancesList.add(new Attendance("Your Child has arrived in school"));
        attendancesList.add(new Attendance("Your child has left the school"));

        //DashboardAdapter dashboardAdapter = new DashboardAdapter(this, featureList);

        SchoolAttendanceAdapter schoolAttendanceAdapter = new SchoolAttendanceAdapter(this,attendancesList);

        recyclerView.setAdapter(schoolAttendanceAdapter);


    }
}
