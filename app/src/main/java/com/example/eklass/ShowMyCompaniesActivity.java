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

public class ShowMyCompaniesActivity extends BaseActivity
{

    public RecyclerView recyclerView;
    public List<Company> companyList;
    Util util = new Util();
    public ShowMyCompaniesAdapter showMyCompaniesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managers_worker);

        TextView pageHeading  = findViewById(R.id.tvHeader_activity_managers_workers);
        util.SetHeadings(getApplicationContext(), pageHeading
                , "My Staff", BaseActivity.themeNo);

        recyclerView =  findViewById(R.id.rvManagerWorker);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        companyList = new ArrayList<>();

        loadData2();

    }

    private void loadData2()
    {
        User usr = SharedPrefManager.getInstance(getApplicationContext()).getUser();

        final String mobileNo = usr.getUserMobileNo();

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

                    Company company_fromDB ;
                    for(int i=0; i< array.length(); i++)
                    {
                        JSONObject o = array.getJSONObject(i);
                        company_fromDB =   new Company(
                                o.getInt("CompanyID")
                                , o.getString("CompanyName")

                        );

                        companyList.add(company_fromDB);
                    }

                    showMyCompaniesAdapter  =
                            new ShowMyCompaniesAdapter(getApplicationContext(), companyList);
                    recyclerView.setAdapter(showMyCompaniesAdapter);

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
        params.put("pMobileNo", mobileNo);
        params.put("pCompanyId", "-1");

        RequestHandler rh = new RequestHandler();
        String paramsStr = rh.getPostDataString(params);
        String theURL = URLs.GET_COMPANIES_URL +"?" + paramsStr;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, theURL
                , responseListener, errorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



}
