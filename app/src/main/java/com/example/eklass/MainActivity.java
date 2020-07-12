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
     // LoginActivity

    EditText et_MobileNo, et_Password;
    //RadioGroup radioGroup_Staff;
    String deviceToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_MobileNo = (EditText) findViewById(R.id.etMobileNo_activity_main);
        et_Password = (EditText) findViewById(R.id.etPassword_activity_main);

        // if the user is logged in it will go to mychildren activity
        User usr = SharedPrefManager.getInstance(this).getUser();

        if(SharedPrefManager.getInstance(this).isLoggedIn())
        {
            finish();
            //startActivity( new Intent(this, ShowStaffLocationActivity.class));
            startActivity(new Intent(getApplicationContext()
                    , ShowStaffLocationActivity.class));
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
                    int themeNo_fromDB = 2;
                    int postType_fromDB = 2;

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
                        themeNo_fromDB = o.getInt("ThemeNo");
                        postType_fromDB = o.getInt("PostType");
                        companyId_fromDB =  o.getString("CompanyId");
                        companyName_fromDB =  o.getString("CompanyName");

                    }
                    Log.w("sandeep", " 111 " + postType_fromDB);

                User user = new User(
                            staffMobileNo_fromDB
                            , staffType_fromDB
                            , staffId_fromDB
                            , staffName_fromDB
                            , designationId_fromDB
                            , designationName_fromDB
                            , companyId_fromDB
                            , companyName_fromDB
                            , themeNo_fromDB
                            , postType_fromDB
                            );

                    // if no error in response
                   if(!isError)
                    {
                        Toast.makeText(getApplicationContext()
                                , "Login Successful..", Toast.LENGTH_SHORT).show();

                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        finish();
                        startActivity(new Intent(getApplicationContext()
                                , ShowStaffLocationActivity.class));
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
