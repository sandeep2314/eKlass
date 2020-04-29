package com.example.eklass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    EditText et_MobileNo, et_Password, et_CompanyID;
    RadioGroup radioGroup_Staff;
    String deviceToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        et_MobileNo = (EditText) findViewById(R.id.etMobileNo_activity_main);
        et_Password = (EditText) findViewById(R.id.etPassword_activity_main);
        et_CompanyID = findViewById(R.id.etCompanyId_activity_main);
        radioGroup_Staff = findViewById(R.id.radioGroup_staff_activity_main);

        //deviceId = this.getde

        // if the user is logged in it will go to mychildren activity
        if(SharedPrefManager.getInstance(this).isLoggedIn())
        {
            finish();
            //if(staffType_fromDB.equalsIgnoreCase("admin"))

            User usr = SharedPrefManager.getInstance(this).getUser();

            Log.w("UserStaff ", "usr.getCompanyName 555 " + usr.getStaffType());

            if(usr.getStaffType().equals("admin"))
            {

                startActivity( new Intent(this, DashboardActivity.class));
            }
            else
            {
                 startActivity( new Intent(this, ScanActivity.class));
            }

            return;
        }


        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaffLogin();
            }
        });


       findViewById(R.id.txtRegister).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
               startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
           }
       });




    }

    private void userLogin()
    {
        final String userMobileNo = et_MobileNo.getText().toString();
        final  String userPassword = et_Password.getText().toString();
        final String userComapanyId = et_CompanyID.getText().toString();

        if(TextUtils.isEmpty(userMobileNo))
        {
            et_MobileNo.setError("Please Enter Your Mobile Number");
            et_MobileNo.requestFocus();

            return;
        }

        if(TextUtils.isEmpty(userPassword))
        {
            et_Password.setError("Please Enter Your Password");
            et_Password.requestFocus();
        }

        if(TextUtils.isEmpty(userComapanyId))
        {
            et_CompanyID.setError("Please Enter Your Company ID Given By Your Admin");
            et_CompanyID.requestFocus();
        }

        // if everything is fine

        class UserLogin extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();

                params.put("mobileNo", userMobileNo);
                params.put("password", userPassword);
                params.put("companyId", userComapanyId);

                String response = null;
                try
                {
                    response = requestHandler.sendPostRequest(URLs.LOGIN_URL ,params);
                } catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }

                return response;
            }

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);

                Log.w("sandeep", "response 222 " + s.toString());

                // converting response to JSON object
                try
                {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray array = jsonObject.getJSONArray("a");

                    boolean isError = true;

                    String userMobileFromDB  = "";
                    String StudentId = "";
                    String StudentName= "";
                    String StudentClass =  "";
                    //
                    String StaffType = "";

                    for(int i=0; i< array.length(); i++)
                    {
                        JSONObject o = array.getJSONObject(i);

                        // if there is any record then login is succesfull
                        isError = false;
                        userMobileFromDB = o.getString("MobileNo");

                       StudentId =  o.getString("StudentMasterID");
                       StudentName =  o.getString("StudentName");
                       StudentClass = o.getString("StudentClass");
                    }

                    Student myChild = new Student(StudentId, StudentName, StudentClass,userMobileNo );

                    Log.w("isError ", " 444 " + userMobileFromDB);

                    // if no error in response

                    if(!isError)
                    {
                        User user = new User(userMobileFromDB, myChild);
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        // starting the MyChildren activity
                        finish();
                        //startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        startActivity(new Intent(getApplicationContext(), ScanActivity.class));

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext()
                                , "Invalid Mobile or Password", Toast.LENGTH_SHORT).show();

                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }


            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }



    private void StaffLogin()
    {
        final String  userMobileNo = et_MobileNo.getText().toString();
        final  String userPassword = et_Password.getText().toString();
        final String userCompanyId = et_CompanyID.getText().toString();
        final String userIsAdmin; // = ((RadioButton) findViewById(radioGroup_Staff.getCheckedRadioButtonId())).getText().toString();

        /*RadioButton rdManager = findViewById(R.id.radioBtnManager_activity_staff);

        final  String IsStaff;
        // 1 is worker, 2 is Manager
        IsStaff = rdManager.isChecked()?"2":"1";
*/
        RadioButton rdAdmin = findViewById(R.id.radioAdmin_activity_main);
        userIsAdmin = rdAdmin.isChecked()?"1":"2";

        Log.w("sandeep", "444 userIsAdmin" + userIsAdmin);

        if(TextUtils.isEmpty(userMobileNo))
        {
            et_MobileNo.setError("Please Enter Your Mobile Number");
            et_MobileNo.requestFocus();

            return;
        }


        // if everything is fine

        class StaffLogin extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();

                params.put("rMobileNo", userMobileNo);
                params.put("rPassword", userPassword);
                params.put("rCompanyId", userCompanyId);
                params.put("rIsAdmin", userIsAdmin);
                //params.put("rIsAdmin", "1");

                String response = null;
                try
                {
                    response = requestHandler.sendPostRequest(URLs.GUARD_LOGIN_URL ,params);
                } catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }

                return response;
            }

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);

                Log.w("sandeep", "response 222 " + s.toString());

                // converting response to JSON object
                try
                {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray array = jsonObject.getJSONArray("a");

                    boolean isError = true;
                    String staffMobileNo_fromDB="-1" ;
                    String staffId_fromDB = "";
                    String staffName_fromDB = "";
                    String companyName_fromDB = "";
                    String companyId_fromDB = "";
                    String staffType_fromDB = "admin";


                    for(int i=0; i< array.length(); i++)
                    {
                        JSONObject o = array.getJSONObject(i);

                        // if there is any record then login is succesfull
                        isError = false;

                        staffId_fromDB =  o.getString("StaffId");
                        staffName_fromDB =o.getString("StaffName");
                        staffMobileNo_fromDB = o.getString("MobileNo");
                        staffType_fromDB = o.getString("StaffType");
                        companyId_fromDB =  o.getString("CompanyId");
                        companyName_fromDB =  o.getString("CompanyName");

                    }

                    User user = new User(
                            userMobileNo
                            , staffType_fromDB
                            , staffId_fromDB
                            , staffName_fromDB
                            , companyId_fromDB
                            , companyName_fromDB

                            );


                    Log.w("staffType_fromDB ", " 444 " + staffType_fromDB);

                    // if no error in response

                    if(!isError)
                    {

                        Toast.makeText(getApplicationContext()
                                , "Login Successful..", Toast.LENGTH_SHORT).show();

                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        // starting the MyChildren activity
                        finish();
                        // admin log in
                        if(staffType_fromDB.equalsIgnoreCase("-1"))
                        {
                            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        }
                        else if(staffType_fromDB.equalsIgnoreCase("1"))
                        {
                            startActivity(new Intent(getApplicationContext(), ScanActivity.class));
                        }
                        else if(staffType_fromDB.equalsIgnoreCase("2"))
                        {
                            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        }

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext()
                                , "Invalid Mobile or Password", Toast.LENGTH_SHORT).show();

                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }


            }
        }

        StaffLogin ul = new StaffLogin();
        ul.execute();
    }


    private void CustomerLogin()
    {
        final String userMobileNo = et_MobileNo.getText().toString();
        final  String userPassword = et_Password.getText().toString();

        if(TextUtils.isEmpty(userMobileNo))
        {
            et_MobileNo.setError("Please Enter Your Mobile Number");
            et_MobileNo.requestFocus();

            return;
        }

        if(TextUtils.isEmpty(userPassword))
        {
            et_Password.setError("Please Enter Your Password");
            et_Password.requestFocus();
        }


        // if everything is fine

        class CustomerLogin extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();

                params.put("mobileNo", userMobileNo);
                params.put("password", userPassword);

                String response = null;
                try
                {
                    response = requestHandler.sendPostRequest(URLs.GUARD_LOGIN_URL,params);
                } catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }

                return response;
            }

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);

                Log.w("CustomerLogin", "response 666 " + s.toString());

                // converting response to JSON object
                try
                {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray array = jsonObject.getJSONArray("a");

                    boolean isError = true;
                    String CustomerMobile_FromDB="-1" ;
                    String CustomerId_FromDB = "";
                    String CustomerName_FromDB = "";


                    for(int i=0; i< array.length(); i++)
                    {
                        JSONObject o = array.getJSONObject(i);

                        // if there is any record then login is succesfull
                        isError = false;
                        CustomerMobile_FromDB = o.getString("MobileNo");

                        CustomerId_FromDB =  o.getString("RID");
                        CustomerName_FromDB =  o.getString("UserName");

                    }

                    //Student myChild = new Student(StudentId, StudentName, StudentClass,userMobileNo );
                    Customer customer = new Customer(CustomerId_FromDB, CustomerName_FromDB
                            , CustomerMobile_FromDB   );


                    // if no error in response

                    if(!isError)
                    {
                        User user = new User(CustomerMobile_FromDB);
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);


                        // starting the MyChildren activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext()
                                , "Invalid Mobile or Password", Toast.LENGTH_SHORT).show();

                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }


            }
        }

        CustomerLogin cl = new CustomerLogin();
        cl.execute();
    }



}
