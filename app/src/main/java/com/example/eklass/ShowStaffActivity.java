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

public class ShowStaffActivity extends BaseActivity
{
      public RecyclerView recyclerView;
      public List<Staff> staffList;

      Util util = new Util();

       public ShowStaffAdapter managerDashboardAdapter;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_managers_worker);

            TextView pageHeading  = findViewById(R.id.txtSchoolAttendance);
            util.SetHeadings(getApplicationContext(), pageHeading
                    , "My Staff", BaseActivity.themeNo);

            recyclerView =  findViewById(R.id.rvManagerWorker);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            staffList = new ArrayList<>();

            loadData2();
      }

        //////////////////begin loaddata2////////////
        private void loadData2()
        {
            User usr = SharedPrefManager.getInstance(getApplicationContext()).getUser();

            final String staff_mobileNo = usr.UserMobileNo;
            final String staffId = usr.getStaffId();
            final  String CompanyId = usr.getCompanyId();
            final String DesignationId = usr.getDesignationId();

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
                            Log.w("sandeep444" , o.getString("WorkerName").toString());

                            staff_fromDB =   new Staff(
                                      o.getString("WorkerID")
                                    , o.getString("WorkerName")
                                    , o.getString("DName")
                                    , o.getString("MobileNo")
                                    , o.getString("CompanyId")
                                    , o.getString("CompanyName")
                            );

                        Log.w("sandeep555" , o.getString("WorkerName").toString());
                        staffList.add(staff_fromDB);
                        }

                        Log.w("Sandeep", " stafflist222 "+ staffList.size());

                        managerDashboardAdapter  =
                                new ShowStaffAdapter(getApplicationContext(), staffList);
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


