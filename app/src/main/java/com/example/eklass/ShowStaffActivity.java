package com.example.eklass;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
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

public class ShowStaffActivity extends BaseActivity
{
      public RecyclerView recyclerView;
      public List<Staff> staffList;
      Util util = new Util();
      public String companyID_chosen;
      public String companyName_chosen;
      public ShowStaffAdapter showStaffAdapter;
      public  String isAllStaff;
      private List<String> currentSelectedItems1 = new ArrayList<>();
      SparseBooleanArray currentSelectedItems = new SparseBooleanArray();



    @Override
        protected void onCreate(@Nullable Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_managers_worker);

            companyID_chosen = getIntent().getStringExtra("companyID_fromShowCompany_activity");
            companyName_chosen = getIntent().getStringExtra("companyName_fromShowCompany_activity");

            isAllStaff = String.valueOf(getIntent().getIntExtra("isAllStaff", 0));

            if(companyID_chosen != null && companyID_chosen.length() > 0) {

                Log.w("sandeep", "companyName_chosen 222 " +companyName_chosen);
                User usr = SharedPrefManager.getInstance(getApplicationContext()).getUser();
                usr.setCompanyId(companyID_chosen);
                usr.setCompanyName(companyName_chosen);
                SharedPrefManager.getInstance(getApplicationContext()).userLogin(usr);
            }
            TextView pageHeading  = findViewById(R.id.tvHeader_activity_managers_workers);
            CircleImageView imageViewProfileHeading, imageViewLogoHeading;
            imageViewLogoHeading=findViewById(R.id.imageLogo_activity_managers_worker);
            imageViewProfileHeading=findViewById(R.id.imageProfile_activity_managers_worker);

            String pageName = "";
            if(isAllStaff.equals("0"))
                pageName = "My Assigned Staff";
            else
                pageName = "My All Staff";

            util.SetHeadings(this, pageHeading
                    , pageName
                    , imageViewLogoHeading
                    , imageViewProfileHeading
                    , BaseActivity.themeNo);

            findViewById(R.id.imageProfile_activity_managers_worker).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                }
            });


            findViewById(R.id.imageLogo_activity_managers_worker).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), CompanyProfileActivity.class));
                }
            });



            recyclerView =  findViewById(R.id.rvManagerWorker);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            staffList = new ArrayList<>();

            loadData2();
      }


    public void Delete()
    {
        String selectedIds = TextUtils.join(", ", currentSelectedItems1) ;
        util.DeleteRecord(getApplicationContext()
                , selectedIds, URLs.DEL_STAFF_URL);
        Toast.makeText(getApplicationContext()
                , "Records Deleted.." + selectedIds, Toast.LENGTH_LONG).show();
        recreate();

    }


    //////////////////begin loaddata2////////////
        private void loadData2()
        {
            User usr = SharedPrefManager.getInstance(getApplicationContext()).getUser();

            final String staff_mobileNo = usr.getUserMobileNo();
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
                        //http://103.233.24.31:8080/getimage?fileName=bpslogo.jpg
                        //http://103.233.24.31:8080/getimage?fileName=bpslogo.jpg&pIsLogo=1
                        String imageURL = "";
                        for(int i=0; i< array.length(); i++)
                        {
                            JSONObject o = array.getJSONObject(i);
                            imageURL = URLs.GET_IMAGE_URL + o.getString("imageURL");
                            imageURL += "&pIsLogo=0";
                            Log.w("sandeep444" , "imageURL "+imageURL);

                            staff_fromDB =   new Staff(
                                      o.getString("WorkerID")
                                    , o.getString("WorkerName")
                                    , o.getString("StaffPassword")
                                    , o.getInt("Did")
                                    , o.getString("DName")
                                    , o.getString("MobileNo")
                                    , o.getString("CompanyId")
                                    , o.getString("CompanyName")
                                    , o.getInt("IsActive")
                                    , imageURL
                            );

                        Log.w("sandeep555" , o.getString("WorkerName").toString());
                        staffList.add(staff_fromDB);
                        }

                        showStaffAdapter = new ShowStaffAdapter
                                (getApplicationContext(),  staffList
                                        , new ShowStaffAdapter.OnItemCheckListener()
                                {

                                    @Override
                                    public void onItemCheck(int pos, Staff item) {

                                        //currentSelectedItems.add(item);
                                        currentSelectedItems.put(pos, true);
                                        currentSelectedItems1.add(item.getStaffId());

                                        Toast.makeText(getApplicationContext()
                                                , "onItemCheck " + currentSelectedItems1.toString()
                                                , Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onItemUncheck(int pos, Staff item) {
                                        currentSelectedItems.delete(pos);
                                        currentSelectedItems1.remove(item.getStaffId());


                                        Toast.makeText(getApplicationContext()
                                                , "onItemUn-Check "
                                                        + currentSelectedItems1.toString()
                                                , Toast.LENGTH_LONG).show();
                                    }
                                });




                        recyclerView.setAdapter(showStaffAdapter);

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
            params.put("pIsLogo", "0");
            params.put("pIsAllStaff", isAllStaff);


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



