package com.example.eklass;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddStaffActivity extends BaseActivity
        implements AdapterView.OnItemSelectedListener
{

    EditText etStaffName, etStaffPassword, etStaffMobile;
    public String[] designationIds;
    Spinner spinner_designation;
    int designationId;
    ArrayAdapter arrayAdapter_designation;
    Boolean isUpdate = false;
    String staffIdUpdate;
    int  designationIdUpdate, IsActiveUpdate;
    CheckBox ckbIsActive;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstaff);

        TextView pageHeading  = findViewById(R.id.tvHeader_activity_managers_workers);
        CircleImageView imageViewProfileHeading, imageViewLogoHeading;
        imageViewLogoHeading=findViewById(R.id.imageLogo_activity_managers_worker);
        imageViewProfileHeading=findViewById(R.id.imageProfile_activity_managers_worker);

        String update  = getIntent().getStringExtra("isUpdate");
        if(update!= null)
            isUpdate = update.equals("yes");

        String pageName = "";
        if(isUpdate)
            pageName = "Update Staff";
        else
            pageName = "Add Staff";

        util.SetHeadings(this, pageHeading
                , pageName
                , imageViewLogoHeading
                , imageViewProfileHeading
                , BaseActivity.themeNo);

        etStaffName = findViewById(R.id.etStaffName_activity_staff);
        etStaffMobile = findViewById(R.id.etStaffMobileNo_activity_staff);
        etStaffPassword= findViewById(R.id.etStaffPassword_activity_staff);

        spinner_designation = findViewById(R.id.spinner_designation_activity_addstaff);
        spinner_designation.setOnItemSelectedListener(this);

        ckbIsActive = findViewById(R.id.ckbActive_activity_isstaff);

        if(isUpdate)
        {
            staffIdUpdate = getIntent().getStringExtra("StaffID");
            etStaffName.setText(getIntent().getStringExtra("StaffName"));
            etStaffPassword.setText(getIntent().getStringExtra("StaffPassword"));
            etStaffMobile.setText(getIntent().getStringExtra("StaffMobile"));
            designationIdUpdate = getIntent().getIntExtra("DesignationId",0);
            IsActiveUpdate = getIntent().getIntExtra("IsActive",1);
            ckbIsActive.setChecked(IsActiveUpdate==1);

        }

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
        final Boolean IsCkbChecked = ckbIsActive.isChecked();


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

        // if everything is fine

        class AddStaff extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();

                params.put("pStaffId", String.valueOf(staffIdUpdate));
                params.put("pCompanyId", companyId);
                params.put("pMobileNo", staffMobileNo);
                params.put("pPassword", staffPassword);
                params.put("pStaffName", staffName);
                params.put("pDesignationId", String.valueOf(designationId));

                if(IsCkbChecked)
                    params.put("pIsActive", "1");
                else
                    params.put("pIsActive", "0");

                String saveStaffURL="";
                if(isUpdate)
                    saveStaffURL=URLs.UPDATE_STAFF_URL;
                else
                    saveStaffURL=URLs.ADDSTAFF_URL;

                String response = null;
                try
                {
                    response = requestHandler.sendPostRequest(saveStaffURL ,params);
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
            designationId = Integer.parseInt(selected_value);
        }

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

                    if(isUpdate) {
                        Log.w("sandeep", "designationIdUpdate "+designationIdUpdate);
                        spinner_designation.setSelection(
                                util.GetSpinnerPosition(designationIds
                                                        , designationIdUpdate));
                    }


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
