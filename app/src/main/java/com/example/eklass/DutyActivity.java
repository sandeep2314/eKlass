package com.example.eklass;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DutyActivity extends BaseActivity
{

    public RecyclerView recyclerView;
    public List<Duty> dutyList;
    public DutyAdapter dutyAdapter;
    public String guardID;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duty);

        //RecyclerView recyclerView;

        recyclerView =  findViewById(R.id.rvDuty);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        guardID = getIntent().getStringExtra("guardID");

        dutyList = new ArrayList<>();

        loadData2();
    }

    private void loadData2()
    {
        final String staff_mobileNo = SharedPrefManager.getInstance(getApplicationContext()).getUser().UserMobileNo;
        final String staffId = SharedPrefManager.getInstance(getApplicationContext()).getUser().getStaffId();
        final String guardId = "5";
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
                Log.w("Sandeep777",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("a");

                    Duty duty_fromDB ;
                    for(int i=0; i< array.length(); i++)
                    {
                        JSONObject o = array.getJSONObject(i);


/*
public Duty(Integer dutyId, String dutyDateTime, Integer QRId
                            , String locationName, Integer guardId, String guardName
                            , Double latitude, Double longitude, Integer companyId)
*/

                        duty_fromDB =   new Duty(o.getString("ScanID")
                                , o.getString("CreatedAt")
                                , o.getString("QRID")
                                , o.getString("LocationName")
                                , o.getString("GuardID")
                                , o.getString("GuardName")
                                , o.getString("Latitude")
                                , o.getString("Longitude")
                                , o.getString("CompanyID")
                        );
                        dutyList.add(duty_fromDB);

                    }

                    dutyAdapter =
                            new DutyAdapter (getApplicationContext(), dutyList);
                    recyclerView.setAdapter(dutyAdapter);

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

        params.put("pStaffId", guardId);
        params.put("pCompanyId", CompanyId);


        RequestHandler rh = new RequestHandler();
        String paramsStr = rh.getPostDataString(params);

        String theURL = URLs.GET_DUTY_URL +"?" + paramsStr;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, theURL
                , responseListener, errorListener);



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



}
