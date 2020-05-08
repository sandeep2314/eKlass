package com.example.eklass;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class ShowLocationsActivity extends BaseActivity
{
    public RecyclerView recyclerView;
    public List<LocationQR> locationList;
    public ShowLocationsAdapter showLocationsAdapter;

    public Button btnDelete;

    Util util = new Util();
    private List<LocationQR> currentSelectedItems = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managers_worker);



        TextView pageHeading  = findViewById(R.id.txtSchoolAttendance);
        util.SetHeadings(getApplicationContext(), pageHeading, "My Locations", BaseActivity.themeNo);

        recyclerView =  findViewById(R.id.rvManagerWorker);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        locationList = new ArrayList<>();

        btnDelete = findViewById(R.id.btnDelete_activity_managers_worker);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // Get Checked boxes ids
                String selectedIds = "3,4";

                int pos = recyclerView.getChildAdapterPosition(v);
                Log.w("Sandeep", "recyclerView 222 "+pos);

                /*util.DeleteRecord(getApplicationContext()
                        , selectedIds, URLs.DEL_LOCATION_URL);*/

                loadData2();
            }
        });

        loadData2();
    }

    private void loadData2()
    {

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

                    LocationQR location_fromDB ;
                    for(int i=0; i< array.length(); i++)
                    {
                        JSONObject o = array.getJSONObject(i);
                        location_fromDB =   new LocationQR(o.getString("LocationID")
                                , o.getString("LocationName")

                        );
                        locationList.add(location_fromDB);
                    }

                /*    showLocationsAdapter =
                            new ShowLocationsAdapter(getApplicationContext(), locationList);
                */

                    showLocationsAdapter = new ShowLocationsAdapter
                            (getApplicationContext(),  locationList
                            , new ShowLocationsAdapter.OnItemCheckListener()
                    {

                        @Override
                        public void onItemCheck(LocationQR item) {
                            currentSelectedItems.add(item);
                            Toast.makeText(getApplicationContext()
                                    , "onItemCheck " + currentSelectedItems.size()
                            , Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onItemUncheck(LocationQR item) {
                            currentSelectedItems.remove(item);
                            Toast.makeText(getApplicationContext()
                                    , "onItemUn-Check " + currentSelectedItems.size()
                                    , Toast.LENGTH_LONG).show();
                        }
                    });


                    recyclerView.setAdapter(showLocationsAdapter);

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
        String theURL = URLs.GET_LOCATION_URL+"?" + paramsStr;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, theURL
                , responseListener, errorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



}
