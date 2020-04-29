package com.example.eklass;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.HashMap;

public class AddStaffActivity extends BaseActivity
{

    EditText etStaffName, etStaffPassword, etStaffMobile;
    RadioGroup rdGroupStaffType;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstaff);

        etStaffName = findViewById(R.id.etStaffName_activity_staff);
        etStaffMobile = findViewById(R.id.etStaffMobileNo_activity_staff);
        etStaffPassword= findViewById(R.id.etStaffPassword_activity_staff);
        rdGroupStaffType = findViewById(R.id.radioGroup_staff_activity_main);

        findViewById(R.id.btnAddStaff_activity_staff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add new staff to database
                addStaff();
            }
        });

    }

    private void addStaff()
    {
        final  String userCompantId = SharedPrefManager.getInstance(this).getUser().getCompanyId();
        final String  userName = etStaffName.getText().toString();
        final String  userMobileNo = etStaffMobile.getText().toString();
        final  String userPassword = etStaffPassword.getText().toString();
        RadioButton rdManager = findViewById(R.id.radioBtnManager_activity_staff);

        final  String IsStaff;
        // 1 is worker, 2 is Manager
        IsStaff = rdManager.isChecked()?"2":"1";

        Log.w("sandeep", "444 IsStaff = " + IsStaff);

        if(TextUtils.isEmpty(userMobileNo))
        {
            etStaffMobile.setError("Please Enter Your Mobile Number");
            etStaffMobile.requestFocus();

            return;
        }


        // if everything is fine

        class AddStaff extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();

                params.put("pCompanyId", userCompantId);
                params.put("pMobileNo", userMobileNo);
                params.put("pPassword", userPassword);
                params.put("pStaffName", userName);
                params.put("pIsStaff", IsStaff);

                String response = null;
                try
                {
                    response = requestHandler.sendPostRequest(URLs.ADDSTAFF_URL ,params);
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

                Log.w("sandeep", "response 222 " + s);

                // converting response to JSON object
                try
                {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray array = jsonObject.getJSONArray("a");

                    boolean isError = true;

                    for(int i=0; i< array.length(); i++)
                    {
                        JSONObject o = array.getJSONObject(i);

                        // if there is any record then login is succesfull
                        isError = false;
                        //isError = o.getString("IsSaved").equals("yes");


                    }



                    Log.w("staffType_fromDB ", " 444 " + s);

                    // if no error in .response

                    if(!isError)
                    {

                        // send SMS to Staff maobile with CompanyID and password to login
                        Toast.makeText(getApplicationContext()
                                , "Staff Added Sucessfully", Toast.LENGTH_LONG).show();

        /*                etStaffName.setText("");
                        etStaffMobile.setText("");
                        etStaffPassword.setText("");

                        etStaffName.requestFocus();
*/

                    }
                    else
                    {
                         Toast.makeText(getApplicationContext()
                                , "Invalid Staf Details", Toast.LENGTH_SHORT).show();

                         etStaffMobile.setText(userMobileNo);


                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }


            }
        }

        AddStaff as = new AddStaff();
        as.execute();
    }



}
