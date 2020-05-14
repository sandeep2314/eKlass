package com.example.eklass;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.HashMap;

public class AddDesignationActivity extends BaseActivity
{
    EditText etDesignation, etHierarchy, etDept;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddesignation);

        etDesignation = findViewById(R.id.etDName_activity_addDesignation);
        etHierarchy = findViewById(R.id.etHno_activity_addDesignation);
        etDept = findViewById(R.id.etDept_activity_addDesignation);

    }

    public void addDesignation()
     {
        User usr =  SharedPrefManager.getInstance(this).getUser();

        final String companyId = usr.getCompanyId();
        final String  designation = etDesignation.getText().toString();
        final String  hierarchyNo = etHierarchy.getText().toString();
        final String dept = etDept.getText().toString();


        if(TextUtils.isEmpty(designation))
        {
            etDesignation.setError("Please Enter Your Mobile Number");
            etDesignation.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(hierarchyNo))
         {
             etHierarchy.setError("Please Enter Designation Hierarchy No.");
             etHierarchy.requestFocus();
             return;
         }

         // if everything is fine

        class AddDesignation extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();

                params.put("pCompanyId", companyId);
                params.put("pDName", designation);
                params.put("pHNo", hierarchyNo);
                params.put("pDept", dept);

                String response = null;
                try
                {
                    response = requestHandler.sendPostRequest(URLs.ADD_DESIGNATION_URL ,params);
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

                    Log.w("_fromDB ", " 444 " + s);

                    // if no error in .response

                    if(!isError)
                    {

                        // send SMS to Staff maobile with CompanyID and password to login
                        Toast.makeText(getApplicationContext()
                                , "Designation Added Successfully", Toast.LENGTH_LONG).show();


                    }
                    else
                    {
                        Toast.makeText(getApplicationContext()
                                , "Invalid Details", Toast.LENGTH_SHORT).show();

                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }


            }
        }

        AddDesignation ad = new AddDesignation();
        ad.execute();
    }


}
