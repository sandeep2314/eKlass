package com.example.eklass;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public static int themeNo = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        if(themeNo == 1)
            setTheme(R.style.Theme_AppCompat);
        else
            setTheme(R.style.Theme_AppCompat_Light);

        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("SuperVisor");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        User usr = SharedPrefManager.getInstance(this).getUser();

        Log.w("Sandeep Menu", "usr.getStaffType() 333 " + usr.getStaffType());





        if(usr.getStaffType().equals("1"))
        {
           //MenuItem addStaff =   findViewById(R.id.menuAddStaff);
           MenuItem addStaff =   menu.findItem(R.id.menuAddStaff);
           MenuItem showStaff  = menu.findItem(R.id.menuShowStaff);
           MenuItem addLocations = menu.findItem(R.id.menuAddLocations);
           MenuItem showLocations =   menu.findItem(R.id.menuShowLocations);

           if(addStaff != null) {
               addStaff.setVisible(false);
               showStaff.setVisible(false);
               addLocations.setVisible(false);
               showLocations.setVisible(false);

               Log.w("Sandeep Menu", "usr.getStaffType() 444 " + usr.getStaffType());
           }
            Log.w("Sandeep Menu", "usr.getStaffType() 555 " + usr.getStaffType());
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menuChangeThemeLight:
                if (themeNo == 1)
                    themeNo = 2;
                else
                    themeNo = 1;

                recreate();
                return true;

            case R.id.menuAddStaff:
                finish();
                startActivity( new Intent(this, AddStaffActivity.class));
                return true;
            case R.id.menuShowStaff:
                finish();
                startActivity( new Intent(this, ManagerDashboardActivity.class));
                return true;
            case  R.id.menuAddLocations:
                finish();
                startActivity( new Intent(this, LocationActivity.class));
            case R.id.menuShowLocations:
                Toast.makeText(this, "Show Locations Clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuLogOut:
                Toast.makeText(this, "Log out Clicked", Toast.LENGTH_SHORT).show();
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                return true;

            default: return super.onOptionsItemSelected(item);
        }

    }

}
