package com.example.eklass;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.HashMap;

public class LocationActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    EditText etLocationName, etManagerId, etGuardId;

    public String[] manager_ids;// = { "1", "2", "3", "4", "5"};
    public String[] worker_ids;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlocation);

      etLocationName = findViewById(R.id.etLocationName_activity_location);
      etManagerId = findViewById(R.id.etManagerID_activity_location);
      etGuardId = findViewById(R.id.etGuardID_activity_location);

        // Getting the instance of Spinner and
        // applying onItemSelectedListner to it
        Spinner spinner_manager = findViewById(R.id.spinner_manager_activity_addLocation);
        Spinner spinner_worker = findViewById(R.id.spinner_worker_activity_location);

        spinner_manager.setOnItemSelectedListener(this);
        spinner_worker.setOnItemSelectedListener(this);

        //String[] manager = { "India", "USA", "China", "Japan", "Other"};
        String[] manager = fillStaff("manager");

        String[] workers = fillStaff("worker");

        ArrayAdapter arrayAdapter_manager = new ArrayAdapter(this
                , R.layout.support_simple_spinner_dropdown_item, manager);

        ArrayAdapter arrayAdapter_worker = new ArrayAdapter(this,
                               R.layout.support_simple_spinner_dropdown_item, workers );


        spinner_manager.setAdapter(arrayAdapter_manager);
        spinner_worker.setAdapter(arrayAdapter_worker);

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
        //final String  managerId= etManagerId.getText().toString();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();
        String selected_value = "0";

        String item2;

        if(parent.getId()==R.id.spinner_manager_activity_addLocation) {
            item2 = "Manager";
            selected_value=manager_ids[position];
            etManagerId.setText(selected_value);
        }
        else if(parent.getId()==R.id.spinner_worker_activity_location) {
            item2 = "Worker";
            selected_value=worker_ids[position];
            etGuardId.setText(selected_value);
        }
        else
            item2="Other";




        Toast.makeText(parent.getContext()
                , item2 + "  " + item + " Value: " + selected_value, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public String[] fillStaff(String manager_worker)
    {

        final String [] staffs = { "M1", "M2", "M3", "M4", "Other"};
        final String [] staffs_ids = { "M1", "M2", "M3", "M4", "Other"};

        final String staffType ;

        if(manager_worker.equals("manager"))
            staffType = "2";
        else
            staffType = "1";

        final  String userCompantId = SharedPrefManager.getInstance(this).getUser().getCompanyId();

        Log.w("sandeep", "444 IsStaff = " + userCompantId);

        class FillStaff extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();

                params.put("pCompanyId", userCompantId);
                params.put("pStaffType", staffType);

                String response = null;
                try
                {
                    response = requestHandler.sendPostRequest(URLs.GET_STAFF_URL ,params);
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

                        staffs[i] = o.getString("StaffName");
                        staffs_ids[i] = o.getString("staffID");

                    }

                    if(!isError)
                    {

                        // send SMS to Staff maobile with CompanyID and password to login
                        Toast.makeText(getApplicationContext()
                                , "Loaded Staff Successfully", Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext()
                                , "No Staff Present", Toast.LENGTH_SHORT).show();

                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }

        FillStaff fillStaff = new FillStaff();
        fillStaff.execute();

        if(manager_worker.equals("manager"))
            manager_ids = staffs_ids;
        else
            worker_ids = staffs_ids;

        return staffs;

    }

}
