package com.example.eklass;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class ManagerDashboardActivity extends BaseActivity
{
      public RecyclerView recyclerView;
      public List<Staff> staffList;
      public ManagerDashboardAdapter managerDashboardAdapter;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_managers_worker);


            //RecyclerView recyclerView;


            recyclerView =  findViewById(R.id.rvManagerWorker);
            recyclerView.setHasFixedSize(true);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            staffList = new ArrayList<>();

            loadData2();
      }


        //////////////////begin loaddata2////////////
        private void loadData2()
        {
            final String staff_mobileNo = SharedPrefManager.getInstance(getApplicationContext()).getUser().UserMobileNo;
            final String staffId = SharedPrefManager.getInstance(getApplicationContext()).getUser().getStaffId();
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

                        Staff staff_fromDB ;
                        for(int i=0; i< array.length(); i++)
                        {
                            JSONObject o = array.getJSONObject(i);
                            staff_fromDB =   new Staff(o.getString("WorkerID")
                                    , o.getString("WorkerName")
                                    , o.getString("StaffType")
                                    , o.getString("companyId")
                                    , o.getString("CompanyName")
                            );
                        staffList.add(staff_fromDB);

                        }

                        //studentAdapter = new StudentAdapter(students, getApplicationContext());

                        managerDashboardAdapter  =
                                new ManagerDashboardAdapter (getApplicationContext(), staffList);
                        recyclerView.setAdapter(managerDashboardAdapter);



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

            params.put("pStaffId", staffId);
            params.put("pCompanyId", CompanyId);


            RequestHandler rh = new RequestHandler();
            String paramsStr = rh.getPostDataString(params);

            String theURL = URLs.GET_WORKER_URL +"?" + paramsStr;


            StringRequest stringRequest = new StringRequest(Request.Method.POST, theURL
                    , responseListener, errorListener);



            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }


    //////////////////////end loadData2//////////////


}



