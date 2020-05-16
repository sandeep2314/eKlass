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
        //setTheme(R.style.Theme_AppCompat);
        setContentView(R.layout.activity_main);

        et_MobileNo = (EditText) findViewById(R.id.etMobileNo_activity_main);
        et_Password = (EditText) findViewById(R.id.etPassword_activity_main);
        et_CompanyID = findViewById(R.id.etCompanyId_activity_main);
        radioGroup_Staff = findViewById(R.id.radioGroup_staff_activity_main);

        // if the user is logged in it will go to mychildren activity
        if(SharedPrefManager.getInstance(this).isLoggedIn())
        {
            User usr = SharedPrefManager.getInstance(this).getUser();
            finish();
            // mamager
            startActivity( new Intent(this, ShowStaffActivity.class));
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
        userIsAdmin = rdAdmin.isChecked()?Util.USER_TYPE_ADMIN:Util.USER_TYPE_MANAGER;

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
                    String designationId_fromDB = "1";
                    String designationName_fromDB = "";
                    String companyName_fromDB = "";
                    String companyId_fromDB = "";
                    String staffType_fromDB = "222";

                    Log.w("sandeep", " 999 " + isError);

                    for(int i=0; i< array.length(); i++)
                    {
                        JSONObject o = array.getJSONObject(i);

                        Log.w("sandeep", " 000 " + isError);
                        // if there is any record then login is succesfull
                        isError = false;

                        staffId_fromDB =  o.getString("StaffId");
                        staffName_fromDB =o.getString("StaffName");
                        staffMobileNo_fromDB = o.getString("MobileNo");
                        designationId_fromDB = o.getString("DesignationID");

                        companyId_fromDB =  o.getString("CompanyId");
                        companyName_fromDB =  o.getString("CompanyName");

                    }
                    Log.w("sandeep", " 111 " + isError);


/*
                    public User(String userMobileNo, String staffType, String staffId
                        , String staffName
                        , String designationId
                        , String designationName
                        , String companyId
                        , String companyName
                        , int userTheme) {
*/


                    User user = new User(
                            staffMobileNo_fromDB
                            , staffType_fromDB
                            , staffId_fromDB
                            , staffName_fromDB
                            , designationId_fromDB
                            , designationName_fromDB
                            , companyId_fromDB
                            , companyName_fromDB
                            , Util.WHITE_THEME
                            );

                    // if no error in response

                    Log.w("sandeep", " 333 " + isError);
                    if(!isError)
                    {

                        Log.w("sandeep", " 444 " + isError);

                        Toast.makeText(getApplicationContext()
                                , "Login Successful..", Toast.LENGTH_SHORT).show();

                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                        Log.w("sandeep", " 555 " + isError);

                        finish();
                        startActivity(new Intent(getApplicationContext()
                                , ShowStaffActivity.class));


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



}
