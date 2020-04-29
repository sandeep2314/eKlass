package com.example.eklass;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.HashMap;

public class LocationActivity extends BaseActivity
{

    EditText etLocationName, etManagerId, etGuardId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlocation);



      etLocationName = findViewById(R.id.etLocationName_activity_location);
      etManagerId = findViewById(R.id.etManagerID_activity_location);
      etGuardId = findViewById(R.id.etGuardID_activity_location);

     findViewById(R.id.btnSaveLocation_activity_location).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             addLocation();

         }
     });

    }

    private void addLocation()
    {
        final  String userCompantId = SharedPrefManager.getInstance(this).getUser().getCompanyId();
        final String  locationName = etLocationName.getText().toString();
        final String  managerId= etManagerId.getText().toString();
        final String guardId = etGuardId.getText().toString();



        Log.w("sandeep", "444 IsStaff = " + userCompantId);

        if(TextUtils.isEmpty(locationName))
        {
            etLocationName.setError("Please Enter Your Mobile Number");
            etLocationName.requestFocus();

            return;
        }


        // if everything is fine

        class AddStaff extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();


                params.put("pLocationName", locationName);
                params.put("pManagerId", managerId);
                params.put("pGuardId", guardId);
                params.put("pCompanyId", userCompantId);


                String response = null;
                try
                {
                    response = requestHandler.sendPostRequest(URLs.ADDLOCATION_URL ,params);
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

                Log.w("sandeep", "response 222 " + userCompantId);

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

                    }



                    if(!isError)
                    {

                        // send SMS to Staff maobile with CompanyID and password to login
                        Toast.makeText(getApplicationContext()
                                , "Location Added Successfully", Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext()
                                , "Invalid Staff Details", Toast.LENGTH_SHORT).show();

                        etLocationName.setText(locationName);
                        etManagerId.setText(managerId);
                        etGuardId.setText(guardId);

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
