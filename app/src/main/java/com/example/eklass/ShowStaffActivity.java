package com.example.eklass;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.widget.ImageViewCompat;
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
      public CircleImageView imageViewProfileHeading;
              //, imageViewLogoHeading;
      public CircleImageView imageViewLogoHeading;




      public ShowStaffAdapter managerDashboardAdapter;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_managers_worker);

            companyID_chosen = getIntent().getStringExtra("companyID_fromShowCompany_activity");
            companyName_chosen = getIntent().getStringExtra("companyName_fromShowCompany_activity");




            Log.w("sandeep", "companyName_chosen 111 " );
            if(companyID_chosen != null && companyID_chosen.length() > 0) {

                Log.w("sandeep", "companyName_chosen 222 " +companyName_chosen);
                User usr = SharedPrefManager.getInstance(getApplicationContext()).getUser();
                usr.setCompanyId(companyID_chosen);
                usr.setCompanyName(companyName_chosen);
                SharedPrefManager.getInstance(getApplicationContext()).userLogin(usr);
            }
            TextView pageHeading  = findViewById(R.id.tvHeader_activity_managers_workers);

            imageViewLogoHeading=findViewById(R.id.imageLogo_activity_managers_worker);
            imageViewProfileHeading=findViewById(R.id.imageProfile_activity_managers_worker);

            util.SetHeadings(this, pageHeading
                    , "My Staff"
                    , imageViewLogoHeading
                    , imageViewProfileHeading
                    , BaseActivity.themeNo);

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
                                    , o.getString("DName")
                                    , o.getString("MobileNo")
                                    , o.getString("CompanyId")
                                    , o.getString("CompanyName")
                                    , imageURL
                            );

                        Log.w("sandeep555" , o.getString("WorkerName").toString());
                        staffList.add(staff_fromDB);
                        }
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
            params.put("pIsLogo", "0");

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



