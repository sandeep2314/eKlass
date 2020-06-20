package com.example.eklass;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class DutyActivity extends BaseActivity
{

/*
    // Showing the current location in Google Map
    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    // Zoom in the Google Map
    googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
*/




    public RecyclerView recyclerView;
    public List<Duty> dutyList;
    public DutyAdapter dutyAdapter;
    public int guardID;
    public String workerName;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duty);

        //RecyclerView recyclerView;
        recyclerView =  findViewById(R.id.rvManagerWorker);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        guardID = getIntent().getIntExtra("WorkerID", -1);
        workerName = getIntent().getStringExtra("WorkerName");

        TextView pageHeading  = findViewById(R.id.tvHeader_activity_managers_workers);
        CircleImageView imageViewProfileHeading, imageViewLogoHeading;
        imageViewLogoHeading=findViewById(R.id.imageLogo_activity_managers_worker);
        imageViewProfileHeading=findViewById(R.id.imageProfile_activity_managers_worker);

        String pageName = "Duty of " + workerName + "("+guardID+")";

        util.SetHeadings(this, pageHeading
                , pageName
                , imageViewLogoHeading
                , imageViewProfileHeading
                , BaseActivity.themeNo);



        dutyList = new ArrayList<>();

        loadData2();
    }

    private void loadData2()
    {
        User usr = SharedPrefManager.getInstance(this).getUser();
        final  String CompanyId = usr.getCompanyId();

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

                        duty_fromDB =   new Duty(o.getString("ScanID")
                                , o.getString("CreatedAt")
                                , o.getString("QRID")
                                , o.getString("LocationName")
                                , o.getString("GuardID")
                                , o.getString("GuardName")
                                , o.getString("Latitude")
                                , o.getString("Longitude")
                                , o.getInt("IsScan")
                                , o.getString("CompanyID")
                        );
                        dutyList.add(duty_fromDB);
                        Log.w("sandeep777", "GuardName " + o.getString("GuardName"));

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

        params.put("pStaffId", String.valueOf(guardID));
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
