package com.example.eklass;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LocationActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    EditText etLocationName, etManagerId, etGuardId;
    //public List<String> staffList;
    public String[] managerids;
    public String[] workerids;
    Spinner spinner_manager;
    ArrayAdapter arrayAdapter_manager;
    Spinner spinner_worker;
    ArrayAdapter arrayAdapter_worker;
    Boolean isUpdate = false;

    Button btnSaveLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlocation);

        etLocationName = findViewById(R.id.etLocationName_activity_location);
        etManagerId = findViewById(R.id.etManagerID_activity_location);
        etGuardId = findViewById(R.id.etGuardID_activity_location);
        btnSaveLocation = findViewById(R.id.btnSaveLocation_activity_location);

        // Getting the instance of Spinner and
        spinner_manager = findViewById(R.id.spinner_manager_activity_addLocation);
        spinner_worker = findViewById(R.id.spinner_worker_activity_location);
        // applying onItemSelectedListner to it
        spinner_manager.setOnItemSelectedListener(this);
        spinner_worker.setOnItemSelectedListener(this);

        loadData2();

        String update  = getIntent().getStringExtra("isUpdate");
        if(update!= null)
            isUpdate = update.equals("yes");

        if(isUpdate)
        {
            etLocationName.setText(getIntent().getStringExtra("locationName"));
            btnSaveLocation.setText("Update Location");


        }

     findViewById(R.id.btnSaveLocation_activity_location).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {


                addLocation();

         }
     });

    }



    public void addLocation()
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

                String urlLocation = URLs.ADD_LOCATION_URL;

                if(isUpdate)
                    urlLocation = URLs.UPDATE_LOCATION_URL;

                try
                {
                    response = requestHandler.sendPostRequest(urlLocation ,params);
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

            selected_value = managerids[position];
            etManagerId.setText(selected_value);
        }
        else if(parent.getId()==R.id.spinner_worker_activity_location) {
            item2 = "Worker";
            selected_value = workerids[position].toString();
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


    private void loadData2()
    {
        final  String CompanyId = SharedPrefManager.getInstance(this).getUser().getCompanyId();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading data...");
        progressDialog.show();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //{"a": [{"StudentName": "Mahi", "MobileF": "8923579979"}, {"StudentName": "ANURAG VERMA", "MobileF": "9837402809"}
                // {'a':[{'StudentMasterID':'50215','StudentName':'ARJUN','dey':'7','mnth':'3'}]}
                Log.w("Sandeep444",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("a");

                    List<String> managerList;
                    managerList = new ArrayList<>();
                    List<String> workerList;
                    workerList = new ArrayList<>();

                    List<String> managerIDsList;
                    List<String> workerIDsList;
                    managerIDsList = new ArrayList<>();
                    workerIDsList = new ArrayList<>();

                    for(int i=0; i< array.length(); i++)
                    {
                        JSONObject o = array.getJSONObject(i);

                        if(o.getString("StaffType").equals("2"))
                        {
                            managerIDsList.add(o.getString("staffID"));
                            managerList.add(o.getString("StaffName"));
                        }
                        if(o.getString("StaffType").equals("1"))
                        {
                            workerIDsList.add(o.getString("staffID"));
                            workerList.add(o.getString("StaffName"));
                        }
                    }

                    Util util = new Util();

                    String[] managers = util.ConvertListToStringArray(managerList);
                    String[] workers = util.ConvertListToStringArray(workerList);
                     managerids = util.ConvertListToStringArray(managerIDsList);
                     workerids = util.ConvertListToStringArray(workerIDsList);

                    arrayAdapter_manager = new ArrayAdapter(getApplicationContext()
                            , R.layout.support_simple_spinner_dropdown_item, managers);

                    arrayAdapter_worker = new ArrayAdapter(getApplicationContext(),
                               R.layout.support_simple_spinner_dropdown_item, workers );

                    spinner_manager.setAdapter(arrayAdapter_manager);
                    spinner_worker.setAdapter(arrayAdapter_worker);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getApplicationContext(),  error.getMessage()
                        , Toast.LENGTH_LONG ).show();
            }
        };

        HashMap<String, String> params = new HashMap<>();

        params.put("pCompanyId", CompanyId);

        RequestHandler rh = new RequestHandler();
        String paramsStr = rh.getPostDataString(params);
        String theURL = URLs.GET_STAFF_URL +"?" + paramsStr;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, theURL
                , responseListener, errorListener);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



}
