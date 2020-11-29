package com.example.eklass;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

public class BaseActivity extends AppCompatActivity {

    Util util = new Util();
    static  int themeNo;

    MenuItem btnSave, btnDelete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        User usr = SharedPrefManager.getInstance(this).getUser();
        int themeNo = usr.getUserTheme();

        if (themeNo == Util.BLACK_THEME)
        {
            setTheme(R.style.Theme_AppCompat);
        }
        else
        {
            //setTheme(R.style.Theme_AppCompat_Light);
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setLogo();
        actionBar.setTitle("SuperVisor");

        if(this.getClass().getSimpleName().equals("ShowStaffActivity"))
            actionBar.setDisplayHomeAsUpEnabled(false);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        User usr = SharedPrefManager.getInstance(this).getUser();

         btnSave = menu.findItem(R.id.menuSave);
         btnDelete = menu.findItem(R.id.menuDelete);

        if(themeNo == Util.BLACK_THEME) {
            btnSave.setIcon(R.drawable.ic_save_white_24dp);
            btnDelete.setIcon(R.drawable.ic_delete_white_24dp);
        }
        else {
            btnSave.setIcon(R.drawable.ic_save_green_24dp);
            btnDelete.setIcon(R.drawable.ic_delete_black_24dp);
        }

        btnSave.setVisible((this.getClass().getSimpleName().equals("AddStaffActivity")
                || this.getClass().getSimpleName().equals("AddDesignationActivity")
                || this.getClass().getSimpleName().equals("AddStaffLocationActivity")
                || this.getClass().getSimpleName().equals("ProfileActivity")
                || this.getClass().getSimpleName().equals("CompanyProfileActivity")
                || this.getClass().getSimpleName().equals("AddLocationActivity")));

        // delete icon
        btnDelete.setVisible(
                      this.getClass().getSimpleName().equals("ShowStaffActivity")
                    || this.getClass().getSimpleName().equals("ShowLocationsActivity")
                    || this.getClass().getSimpleName().equals("ShowDesignationActivity")
                    || this.getClass().getSimpleName().equals("ShowStaffLocationActivity")
                    );

        //hide menus when worker
        if(usr.getStaffType().equals(Util.USER_TYPE_WORKER))
        {
           //MenuItem addStaff =   findViewById(R.id.menuAddStaff);
           MenuItem addStaff =   menu.findItem(R.id.menuAddStaff);
           MenuItem showStaff  = menu.findItem(R.id.menuShowAssignedStaff);
           MenuItem addLocations = menu.findItem(R.id.menuAddLocations);
           MenuItem showLocations =   menu.findItem(R.id.menuShowLocations);
           MenuItem  generateQRCode = menu.findItem(R.id.menuGenerateQRCode);

           if(addStaff != null) {
               addStaff.setVisible(false);
               showStaff.setVisible(false);
               addLocations.setVisible(false);
               showLocations.setVisible(false);
           }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        User usr = SharedPrefManager.getInstance(this).getUser();

        Method method = null;
        String methodName="";

        switch (item.getItemId())
        {
            case  R.id.menuSave:
                    // call addStaff


                    try {
                        if(this.getClass().getSimpleName().equals("AddStaffActivity")) {
                            //method = this.getClass().getMethod("addStaff");
                            methodName = "addStaff";
                        }
                        else if(this.getClass().getSimpleName().equals("AddLocationActivity"))
                        {
                            methodName = "addLocation";
                        }
                        else if(this.getClass().getSimpleName().equals("AddDesignationActivity"))
                        {
                            methodName = "addDesignation";
                        }
                        else if(this.getClass().getSimpleName().equals("AddStaffLocationActivity"))
                        {
                            methodName = "addStaffLocation";
                        }
                        else if(this.getClass().getSimpleName().equals("ProfileActivity"))
                        {
                            methodName = "saveProfile";
                        }
                        else if(this.getClass().getSimpleName().equals("CompanyProfileActivity"))
                        {
                            methodName = "saveProfile";
                        }
                        method = this.getClass().getMethod(methodName);

                        if(method != null)
                            method.invoke(this);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    return true;

            case R.id.menuDelete:
                try {
                    method = this.getClass().getMethod("Delete");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                if(method != null) {
                    try {
                        method.invoke(this);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                return true;


            case R.id.menuChangeThemeLight:
                if (themeNo == Util.BLACK_THEME) {
                    themeNo = Util.WHITE_THEME;
                    usr.setUserTheme(Util.WHITE_THEME);

                }
                else {
                    themeNo = Util.BLACK_THEME;
                    usr.setUserTheme(Util.BLACK_THEME);
                }
                SharedPrefManager.getInstance(getApplicationContext()).userLogin(usr);
                recreate();
                return true;


            case android.R.id.home:
               // Toast.makeText(getApplicationContext(), "Home Clicked", Toast.LENGTH_LONG).show();
                finish();

                Intent i = new Intent(getApplicationContext(), ShowStaffLocationActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("isAllStaffLocation", 0 );
                getApplicationContext().startActivity(i);

                return true;

            case R.id.menuGenerateQRCode:

                String generatedPathName = util.GenerateLocationQRCode(getApplicationContext());

                Toast.makeText(getApplicationContext()
                        , "QRCodes Generated at.."+generatedPathName
                        , Toast.LENGTH_LONG).show();
                return true;


            case R.id.menuDuty:
                finish();
                startActivity( new Intent(this, ScanActivity.class));
                return true;


            case R.id.menuAddStaff:
                finish();
                startActivity( new Intent(this, AddStaffActivity.class));
                return true;

            case R.id.menuShowAllStaff:

                finish();

                //startActivity( new Intent(this, ShowStaffActivity.class));
                i = new Intent(getApplicationContext(), ShowStaffActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("isAllStaff", 1 );
                getApplicationContext().startActivity(i);

                return true;

            case  R.id.menuAddLocations:
                finish();
                startActivity( new Intent(this, AddLocationActivity.class));
                return true;
            case R.id.menuShowLocations:
                finish();
                startActivity( new Intent(this, ShowLocationsActivity.class));
                return true;

            case R.id.menuAddStaffLocation:
                finish();
                startActivity( new Intent(this, AddStaffLocationActivity.class));
                return true;
            case R.id.menuShowStaffLocation:
                finish();
                i = new Intent(getApplicationContext(), ShowStaffLocationActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("isAllStaffLocation", 1 );
                getApplicationContext().startActivity(i);
                return true;
            case R.id.menuAddDesignations:
                finish();
                startActivity( new Intent(this, AddDesignationActivity.class));
                return true;

            case R.id.menuShowDesignations:
                finish();
                startActivity( new Intent(this, ShowDesignationActivity.class));
                return true;

            case R.id.menuShowMonths:
                finish();
                startActivity( new Intent(this, ShowMonthActivity.class));
                return true;
            case R.id.menuGenerateMonthlyReport:

                String theExcelFile
                        = null;

                try {
                    util.GetStaffTimeSheet(getApplicationContext(), 10, 20);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext()
                        , "TimeSheet Generated"
                        , Toast.LENGTH_LONG).show();
                return true;


            case R.id.menuProfile:
                finish();
                startActivity( new Intent(this, ProfileActivity.class));
                return true;

            case R.id.menuLogo:
                finish();
                startActivity( new Intent(this, CompanyProfileActivity.class));
                return true;

            case R.id.menuChangeCompany:
                finish();
                startActivity( new Intent(this, ShowMyCompaniesActivity.class));
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
