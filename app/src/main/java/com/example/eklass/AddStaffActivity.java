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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class AddStaffActivity extends BaseActivity
        implements AdapterView.OnItemSelectedListener
{

    EditText etStaffName, etStaffPassword, etStaffMobile, etDesignation;
    public String[] designationIds;
    Spinner spinner_designation;
    ArrayAdapter arrayAdapter_designation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstaff);

        etStaffName = findViewById(R.id.etStaffName_activity_staff);
        etStaffMobile = findViewById(R.id.etStaffMobileNo_activity_staff);
        etStaffPassword= findViewById(R.id.etStaffPassword_activity_staff);
        etDesignation = findViewById(R.id.etDesignation_activity_addstaff);

        spinner_designation = findViewById(R.id.spinner_designation_activity_addstaff);
        spinner_designation.setOnItemSelectedListener(this);

        // add designations to spinner
        loadData2();


    }

    public void addStaff()
    {
        User usr = SharedPrefManager.getInstance(this).getUser();

        final String companyId = usr.getCompanyId();
        final String  staffName = etStaffName.getText().toString();
        final String  staffMobileNo = etStaffMobile.getText().toString();
        final String staffPassword = etStaffPassword.getText().toString();
        final String staffDesignation = etDesignation.getText().toString();

        if(TextUtils.isEmpty(staffName))
        {
            etStaffName.setError("Please Enter Staff Name");
            etStaffName.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(staffMobileNo))
        {
            etStaffMobile.setError("Please Enter Staff Mobile No.");
            etStaffMobile.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(staffPassword))
        {
            etStaffPassword.setError("Please Create a Staff Password");
            etStaffPassword.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(staffDesignation))
        {
            spinner_designation.requestFocus();
            return;
        }


        // if everything is fine

        class AddStaff extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();

                params.put("pCompanyId", companyId);
                params.put("pMobileNo", staffMobileNo);
                params.put("pPassword", staffPassword);
                params.put("pStaffName", staffName);
                params.put("pDesignationId", staffDesignation);

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

                    // if no error in .response
                    if(!isError)
                    {
                        // send SMS to Staff maobile with CompanyID and password to login
                        Toast.makeText(getApplicationContext()
                                , "Staff Added Successfully", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                         Toast.makeText(getApplicationContext()
                                , "Some Error Occurred", Toast.LENGTH_SHORT).show();
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

        if(parent.getId()==R.id.spinner_designation_activity_addstaff) {
            selected_value = designationIds[position];
            etDesignation.setText(selected_value);
        }

        Toast.makeText(parent.getContext()
                ,  item + " Value: " + selected_value, Toast.LENGTH_LONG).show();
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

                    List<String> designationList;
                    designationList = new ArrayList<>();

                    List<String> designationIDsList;
                    designationIDsList = new ArrayList<>();

                    for(int i=0; i< array.length(); i++)
                    {
                        JSONObject o = array.getJSONObject(i);

                        designationIDsList.add(o.getString("DId"));
                            designationList.add(o.getString("DName"));

                    }

                    Util util = new Util();
                    String[] designations = util.ConvertListToStringArray(designationList);
                    designationIds = util.ConvertListToStringArray(designationIDsList);
                    arrayAdapter_designation = new ArrayAdapter(getApplicationContext()
                            , R.layout.support_simple_spinner_dropdown_item, designations);

                    spinner_designation.setAdapter(arrayAdapter_designation);


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
        String theURL = URLs.GET_DESIGNATION_URL +"?" + paramsStr;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, theURL
                , responseListener, errorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



}
