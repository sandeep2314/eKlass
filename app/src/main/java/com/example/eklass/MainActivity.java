package com.example.eklass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    EditText et_MobileNo, et_Password;
    String deviceToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        et_MobileNo = (EditText) findViewById(R.id.etMobileNo);
        et_Password = (EditText) findViewById(R.id.etPassword);

        //deviceId = this.getde

        // if the user is logged in it will go to mychildren activity
        if(SharedPrefManager.getInstance(this).isLoggedIn())
        {
            finish();
            //startActivity( new Intent(this, DashboardActivity.class));
            startActivity( new Intent(this, ScanActivity.class));

            return;
        }




        findViewById(R.id.btnLogin).setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {

                        // login user

                        userLogin();

                        //CustomerLogin();

                    }
                }

        );

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

        if(TextUtils.isEmpty(userMobileNo))
        {
            et_MobileNo.setError("Please Enter Your Mobile Number");
            et_MobileNo.requestFocus();

            return;
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
                    String userMobileFromDB="-1" ;
                    String StudentId = "";
                    String StudentName = "";
                    String StudentClass = "";

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
                    response = requestHandler.sendPostRequest(URLs.CUSTOMERLOGIN_URL ,params);
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
                        User user = new User(CustomerMobile_FromDB, customer);
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
