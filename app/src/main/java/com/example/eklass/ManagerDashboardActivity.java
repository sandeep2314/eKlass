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

/*
            staffList = new ArrayList<>();
            staffList.add(new Staff("2", "W1"
                    , "Worker", "1", "Mahi Retails"));

            staffList.add(new Staff("3", "W2"
                    , "Worker", "1", "Mahi Retails"));
*/


            /*public Staff(String staffId, String staffName
                    , String staffType, String companyId, String companyName) {
*/




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

   private void getWorkers_volley()  {
        final String staff_mobileNo = SharedPrefManager.getInstance(getApplicationContext()).getUser().UserMobileNo;
        final String staffId = SharedPrefManager.getInstance(getApplicationContext()).getUser().getStaffId();
        final  String CompanyId = SharedPrefManager.getInstance(this).getUser().getCompanyId();
        //final ProgressDialog progressDialog = new ProgressDialog(this);

        // progressDialog.setMessage("Getting Staff...");
        //progressDialog.show();
       Response.Listener<String> responseListener = new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {


               //progressDialog.dismiss();
                //{"a": [{"StudentName": "Mahi", "MobileF": "8923579979"}, {"StudentName": "ANURAG VERMA", "MobileF": "9837402809"}
                // {'a':[{'StudentMasterID':'50215','StudentName':'ARJUN','dey':'7','mnth':'3'}]}
                Log.w("Sandeep","Worker444  " + response);

                String staffName_fromDB;
                Staff staff_fromDB;

                JSONObject jsonObject = null;
                try
                {
                    jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("a");

                    boolean isError = true;

                    for(int i=0; i< array.length(); i++)
                    {
                        JSONObject o = array.getJSONObject(i);

                        // if there is any record then login is succesfull
                        isError = false;
                        staffName_fromDB = o.getString("StaffName");

                        staff_fromDB =   new Staff(o.getString("WorkerID")
                                , o.getString("WorkerName")
                                , o.getString("StaffType")
                                , o.getString("companyId")
                                , o.getString("CompanyName")
                        );

                        staffList.add(staff_fromDB);
                    }

                    managerDashboardAdapter =
                            new ManagerDashboardAdapter(getApplicationContext(),staffList);

                    recyclerView.setAdapter(managerDashboardAdapter);

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext()
                        , "Got Workers Successfully", Toast.LENGTH_LONG).show();



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


        RequestHandler rh = new RequestHandler();

        HashMap<String, String> params = new HashMap<>();

        Log.w("sandeep", "worker 222  " + staffId);

        params.put("pStaffId", staffId);
        params.put("pCompanyId", CompanyId);


        String paramsStr = rh.getPostDataString(params);
        String theURL = URLs.GET_WORKER_URL  +"?" + paramsStr;


        StringRequest stringRequest = new StringRequest(Request.Method.POST
                , theURL , responseListener, errorListener);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }

  /*  private void getWorkers()
    {
        final String staff_mobileNo = SharedPrefManager.getInstance(getApplicationContext()).getUser().UserMobileNo;
        final String staffId = SharedPrefManager.getInstance(getApplicationContext()).getUser().getStaffId();
        final  String CompanyId = SharedPrefManager.getInstance(this).getUser().getCompanyId();

        // if everything is fine

        class GetWorker extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();

                params.put("pStaffId", staffId);
                params.put("pCompanyId", CompanyId);


                String response = null;
                try
                {
                    response = requestHandler.sendPostRequest(URLs.GET_WORKER_URL ,params);
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



                Log.w("sandeep", "response 222 " + s.toString());

                // converting response to JSON object
                try
                {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray array = jsonObject.getJSONArray("a");

                    boolean isError = true;
                    Staff staff_fromDB;

                    //staffList_getWorker = new ArrayList<>();


                    for(int i=0; i< array.length(); i++)
                    {
                        JSONObject o = array.getJSONObject(i);

                        // if there is any record then login is succesfull
                        isError = false;

                        staff_fromDB =   new Staff(o.getString("WorkerID")
                                , o.getString("WorkerName")
                                , o.getString("StaffType")
                                , o.getString("companyId")
                                , o.getString("CompanyName"));

                        staffList.add(staff_fromDB);

                    }

                    if(!isError)
                    {


                        Toast.makeText(getApplicationContext()
                                , "Got Workers Successfully..", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext()
                                , "Some Error Occurred ", Toast.LENGTH_SHORT).show();

                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }


            }
        }

        GetWorker getWorker = new GetWorker();
        getWorker.execute();
    }

*/
}



