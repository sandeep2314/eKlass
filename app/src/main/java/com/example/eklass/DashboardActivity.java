package com.example.eklass;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity
{

    TextView txt_children;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mychildren);

        List<Feature> featureList;
        RecyclerView recyclerView;


        // if user is not logged in then start the login activity
        if(!SharedPrefManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this, MainActivity.class));

        }

        txt_children = (TextView) findViewById(R.id.txtChildren);

        // getting the current user

        User user = SharedPrefManager.getInstance(this).getUser();

        // setting the values to the textViews

        Log.w("666 ", user.toString() );
         txt_children.setText("Welcome Back: " + user.getUserMobileNo());
         //txt_children.setText("Welcome Back: " + user.getUserChild().StudentName.toString());

         findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
                 SharedPrefManager.getInstance(getApplicationContext()).logout();
             }
         });




        recyclerView =  findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        featureList = new ArrayList<>();

        featureList.add(new Feature("School Attendance"));
        featureList.add(new Feature("Bus Attendance"));

        DashboardAdapter dashboardAdapter = new DashboardAdapter(this, featureList);

        recyclerView.setAdapter(dashboardAdapter);





        /*findViewById(R.id.tvFeatureName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                Toast.makeText(getApplicationContext()
                        , "Card Clicked", Toast.LENGTH_SHORT).show();


            }
        });*/




    }
}
