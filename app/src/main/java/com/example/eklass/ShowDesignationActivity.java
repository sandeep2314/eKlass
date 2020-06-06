package com.example.eklass;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.widget.ImageView;
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

public class ShowDesignationActivity extends BaseActivity
{

    public RecyclerView recyclerView;
    public List<Designation> designationList;
    public ShowDesignationAdapter showDesignationAdapter;

    Util util = new Util();
    private List<String> currentSelectedItems1 = new ArrayList<>();
    SparseBooleanArray currentSelectedItems = new SparseBooleanArray();
    public ImageView imageViewProfileHeading, imageViewLogoHeading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managers_worker);

        TextView pageHeading  = findViewById(R.id.tvHeader_activity_managers_workers);

        imageViewLogoHeading=findViewById(R.id.imageLogo_activity_managers_worker);
        imageViewProfileHeading=findViewById(R.id.imageProfile_activity_managers_worker);

        util.SetHeadings(getApplicationContext(), pageHeading
                , "My Locations"
                , imageViewLogoHeading
                , imageViewProfileHeading
                , BaseActivity.themeNo);

        recyclerView =  findViewById(R.id.rvManagerWorker);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        designationList = new ArrayList<>();

        loadData2();
    }


    private void loadData2()
    {

        User usr = SharedPrefManager.getInstance(getApplicationContext()).getUser();

        final String staffId = usr.getStaffId();
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
                Log.w("Sandeep444",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("a");

                    Designation designation_fromDB;
                    for(int i=0; i< array.length(); i++)
                    {
                        JSONObject o = array.getJSONObject(i);

                        designation_fromDB =   new Designation(
                                o.getString("DId")
                                , o.getString("DName")
                                , o.getInt("HNo")
                                , o.getString("Dept")

                        );
                        designationList.add(designation_fromDB);
                    }


                    showDesignationAdapter = new ShowDesignationAdapter
                            (getApplicationContext(),  designationList
                                    , new ShowDesignationAdapter.OnItemCheckListener()
                            {

                                @Override
                                public void onItemCheck(int pos, Designation item) {

                                    //currentSelectedItems.add(item);
                                    currentSelectedItems.put(pos, true);
                                    currentSelectedItems1.add(item.getDesignationId());

                                    Toast.makeText(getApplicationContext()
                                            , "onItemCheck " + currentSelectedItems1.toString()
                                            , Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onItemUncheck(int pos, Designation item) {
                                    currentSelectedItems.delete(pos);
                                    currentSelectedItems1.remove(item.getDesignationId());


                                    Toast.makeText(getApplicationContext()
                                            , "onItemUn-Check "
                                                    + currentSelectedItems1.toString()
                                            , Toast.LENGTH_LONG).show();
                                }
                            });


                    recyclerView.setAdapter(showDesignationAdapter);

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
        //params.put("pStaffId", staffId);
        params.put("pCompanyId", CompanyId);
        RequestHandler rh = new RequestHandler();
        String paramsStr = rh.getPostDataString(params);
        String theURL = URLs.GET_DESIGNATION_URL+"?" + paramsStr;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, theURL
                , responseListener, errorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}
