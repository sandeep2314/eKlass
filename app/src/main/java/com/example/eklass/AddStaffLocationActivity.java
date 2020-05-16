package com.example.eklass;

import android.app.ProgressDialog;
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

public class AddStaffLocationActivity extends BaseActivity
        implements AdapterView.OnItemSelectedListener
{
    Spinner spinner_location, spinner_worker, spinner_manager;
    ArrayAdapter arrayAdapter_location, arrayAdapter_manager, arrayAdapter_worker;
    public String[] locationids;
    public String[] managerids;
    public String[] workerids;
    EditText etLocationId, etManagerId, etWorkerId;
    Boolean isUpdate = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstafflocation);

        etLocationId = findViewById(R.id.etLocationId_activity_addStaffLocation);
        etManagerId = findViewById(R.id.etManagerId_activity_addStaffLocation);
        etWorkerId = findViewById(R.id.etWorkerId_activity_addStaffLocation);

        spinner_location = findViewById(R.id.spinner_manager_activity_addStaffLocation);
        spinner_manager = findViewById(R.id.spinner_manager_activity_addStaffLocation);
        spinner_worker = findViewById(R.id.spinner_worker_activity_addStaffLocation);

        // applying onItemSelectedListner to it

        spinner_location.setOnItemSelectedListener(this);
        spinner_manager.setOnItemSelectedListener(this);
        spinner_worker.setOnItemSelectedListener(this);

        loadData2();

    }



    public void addStaffLocation()
    {
        User usr = SharedPrefManager.getInstance(this).getUser();

        final  String companyId = usr.getCompanyId();
        final String locationId = etLocationId.getText().toString();
        final String  managerId= etManagerId.getText().toString();
        final String workerId = etWorkerId.getText().toString();

        // if everything is fine

        class AddStaffLocation extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {


                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();

                params.put("pLocationId", locationId);
                params.put("pManagerId", managerId);
                params.put("pGuardId", workerId);
                params.put("pCompanyId", companyId);

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
                                , "Staff Location added Successfully", Toast.LENGTH_LONG).show();

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

        AddStaffLocation as = new AddStaffLocation();
        as.execute();
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

                    List<String> locationList;
                    locationList = new ArrayList<>();
                    List<String> locationIDsList;
                    locationIDsList = new ArrayList<>();

                    List<String> managerList;
                    managerList = new ArrayList<>();
                    List<String> managerIDsList;
                    managerIDsList = new ArrayList<>();

                    for(int i=0; i< array.length(); i++)
                    {
                        JSONObject o = array.getJSONObject(i);

                            locationIDsList.add(o.getString("locationId"));
                            locationList.add(o.getString("locationName"));

                            managerIDsList.add(o.getString("staffID"));
                            managerList.add(o.getString("StaffName"));

                    }

                    Util util = new Util();

                    String[] locations = util.ConvertListToStringArray(locationList);
                    String[] managers = util.ConvertListToStringArray(managerList);

                    locationids = util.ConvertListToStringArray(locationIDsList);
                    managerids = util.ConvertListToStringArray(managerIDsList);

                    arrayAdapter_manager = new ArrayAdapter(getApplicationContext()
                            , R.layout.support_simple_spinner_dropdown_item, locations);

                    arrayAdapter_manager = new ArrayAdapter(getApplicationContext()
                            , R.layout.support_simple_spinner_dropdown_item, managers);

                    spinner_location.setAdapter(arrayAdapter_location);
                    spinner_manager.setAdapter(arrayAdapter_manager);
                    spinner_worker.setAdapter(arrayAdapter_manager);

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
