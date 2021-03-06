package com.example.eklass;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ScanActivity extends BaseActivity
        implements AdapterView.OnItemSelectedListener
{
    // view objects

    private Button btnIn, btnOut, btnScan;
    public TextView txtQRName, txtLatitude, txtLongitude;
    public String QRName;
    LocationManager locationManager;
    String latitude,longitude;
    private IntentIntegrator qrScan;
    private static final int REQUEST_LOCATION=1;
    Spinner spinner_location;
    ArrayAdapter arrayAdapter_location;
    public String[] locationids;
    public Integer selectedLocationId = -1;
    public RadioGroup radioGroupIsScan;
    public RadioButton radioButtonIsScanYes;

    public int postType;
    public  User usr;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        usr = SharedPrefManager.getInstance(this).getUser();

        // view objects

        TextView pageHeading  = findViewById(R.id.tvHeader_activity_managers_workers);
        CircleImageView imageViewProfileHeading, imageViewLogoHeading;
        imageViewLogoHeading=findViewById(R.id.imageLogo_activity_managers_worker);
        imageViewProfileHeading=findViewById(R.id.imageProfile_activity_managers_worker);

        String pageName = "Mark Attendance";

        util.SetHeadings(this, pageHeading
                , pageName
                , imageViewLogoHeading
                , imageViewProfileHeading
                , BaseActivity.themeNo);

        btnIn = findViewById(R.id.btnIn_activity_scan);
        btnOut = findViewById(R.id.btnOut_activity_scan);
        btnScan = findViewById(R.id.btnScan_activity_scan);
        txtQRName = findViewById(R.id.txtName_activity_scan);
        txtLatitude = findViewById(R.id.txtLatitude_activity_scan);
        txtLongitude = findViewById(R.id.txtLongitude_activity_scan);
        spinner_location = findViewById(R.id.spinner_location_activity_scan);
        radioGroupIsScan = findViewById(R.id.radioGroup_IsScan_activity_scan);
        radioButtonIsScanYes = findViewById(R.id.radioBtn_IsScanYes_activity_scan);

        spinner_location.setOnItemSelectedListener(this);

        UpdateButtons();
        Log.w("Sandeep888", "getPostType " + usr.getPostType());

        if(radioButtonIsScanYes.isChecked()) {
            spinner_location.setEnabled(false);
            btnIn.setText("DAY-IN By QR Scan");
            btnOut.setText("DAY-OUT By QR Scan");
            btnScan.setText("IN-BETWEEN By QR Scan");

        }

        radioGroupIsScan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioButtonIsScanYes.isChecked())
                {
                    spinner_location.setEnabled(false);
                    btnIn.setText("DAY-IN By QR Scan");
                    btnOut.setText("DAY-OUT QR Scan");
                    btnScan.setText("IN-BETWEEN By QR Scan");
                }
                else
                {
                    spinner_location.setEnabled(true);
                    btnIn.setText("DAY-IN");
                    btnOut.setText("DAY-OUT");
                    btnScan.setText("IN-BETWEEN");
                }
            }
        });



        loadData2();
        locationManager=(LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            Log.w("Scan", "locationManager222" );
            //Write Function To enable gps
            OnGPS();
        }
        else
        {
            Log.w("Scan", "locationManager333" );
            //GPS is already On then
            getLocation();
        }


        //intializing scan object
        qrScan = new IntentIntegrator(this);
        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        // attaching onClick listener
        btnScan.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                postType = Util.ATTENDANCE_BETWEEN;
                usr.setPostType(postType);
                SharedPrefManager.getInstance(getApplicationContext()).userLogin(usr);
                UpdateButtons();
                util.UpdateAttendanceType(getApplicationContext()
                        , Util.ATTENDANCE_BETWEEN, URLs.UPDATE_STAFF_ATTENDANCE_TYPE_URL );


                if(radioButtonIsScanYes.isChecked())
                    {
                        ScanProcess();
                    }
                    else
                    {
                        QRName = selectedLocationId.toString();
                        addScan();
                    }

            }
        });

        btnIn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                postType = Util.ATTENDANCE_DAY_IN;
                usr.setPostType(postType);
                SharedPrefManager.getInstance(getApplicationContext()).userLogin(usr);
                // update attendance status to tblStaff (PostType)
                util.UpdateAttendanceType(getApplicationContext()
                        , Util.ATTENDANCE_DAY_IN, URLs.UPDATE_STAFF_ATTENDANCE_TYPE_URL );

                UpdateButtons();

                Log.w("Sandeep888", "IN getPostType " + usr.getPostType());

                if(radioButtonIsScanYes.isChecked())
                {
                    ScanProcess();
                }
                else
                {
                    QRName = selectedLocationId.toString();
                    addScan();
                }

            }
        });

        btnOut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                postType = Util.ATTENDANCE_DAY_OUT;
                usr.setPostType(postType);
                SharedPrefManager.getInstance(getApplicationContext()).userLogin(usr);
                util.UpdateAttendanceType(getApplicationContext()
                        , Util.ATTENDANCE_DAY_OUT, URLs.UPDATE_STAFF_ATTENDANCE_TYPE_URL );

                UpdateButtons();

                if(radioButtonIsScanYes.isChecked())
                {
                    ScanProcess();
                }
                else
                {
                    QRName = selectedLocationId.toString();
                    addScan();
                }
            }
        });

    }


    protected  void UpdateButtons()
    {

       // when btn out is clicked
       btnIn.setEnabled(usr.getPostType() == Util.ATTENDANCE_DAY_OUT);
       btnOut.setEnabled(usr.getPostType()== Util.ATTENDANCE_DAY_IN
               || usr.getPostType()== Util.ATTENDANCE_BETWEEN);
       btnScan.setEnabled(usr.getPostType()== Util.ATTENDANCE_DAY_IN
               || usr.getPostType()== Util.ATTENDANCE_BETWEEN);

    }



    protected void ScanProcess()
    {
        qrScan.setBeepEnabled(true);
        qrScan.setOrientationLocked(true);
        qrScan.setCaptureActivity(CaptureActivityPortrait.class);
        qrScan.initiateScan();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        //super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null)
            {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();

            }
            else // if qr contains data
            {
                try {   // converting data to JSON
                    // {"name":"Sandeep", "address":"Shop 14"}
                    // result.getContents() = 2

                    //JSONObject obj = new JSONObject(result.getContents());

                    QRName = result.getContents();
                    txtQRName.setText(QRName);

                    if(latitude.length()> 0 && longitude.length()>0)
                        addScan();
                    else
                    {
                        Toast.makeText(getApplicationContext()
                                , "Can not fetch Latitude & Longitude. Please try again"
                                ,Toast.LENGTH_LONG).show();

                        return;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    // if the control comes here
                    // that means the encoded data
                    // dislay whatever data is there

                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }


        } else {

            super.onActivityResult(requestCode, resultCode, data);

        }
    }

    private void OnGPS() {

        Log.w("OnGPS", "OnGPS1111");

        final AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton
                ("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                Log.w("OnGPS", "OnGPS2222");
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }


    private void getLocation() {

        //Check Permissions again

        if (ActivityCompat.checkSelfPermission(
                ScanActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(ScanActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps= locationManager.getLastKnownLocation(
                    LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(
                    LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(
                    LocationManager.PASSIVE_PROVIDER);

            if (LocationGps !=null)
            {
                double lat=LocationGps.getLatitude();
                double longi=LocationGps.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                //txtLatitude.setText("Latitude= "+latitude+"\n"+"Longitude= "+longitude);
                txtLatitude.setText("Latitude= "+latitude);
                txtLongitude.setText("Longitude= "+longitude);
            }
            else if (LocationNetwork !=null)
            {
                double lat=LocationNetwork.getLatitude();
                double longi=LocationNetwork.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                //txtLatitude.setText("Latitude= "+latitude+"\n"+"Longitude= "+longitude);
                txtLatitude.setText("Latitude= "+latitude);
                txtLongitude.setText("Longitude= "+longitude);
            }
            else if (LocationPassive !=null)
            {
                double lat=LocationPassive.getLatitude();
                double longi=LocationPassive.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                //txtLatitude.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
                txtLatitude.setText("Latitude= "+latitude);
                txtLongitude.setText("Longitude= "+longitude);
            }
            else
            {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
                txtLatitude.setText("Can't Get Your Location. Please switch on your mobile GPS");
                txtLongitude.setText("");
            }

            //Thats All Run Your App
        }

    }


    private void addScan()
    {
        User usr = SharedPrefManager.getInstance(this).getUser();

        final  String companyId = usr.getCompanyId();
        final String  WorkerStaffId = usr.getStaffId();
        final  Boolean hasScanned = radioButtonIsScanYes.isChecked();

        // if everything is fine

        class AddScan extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();

                params.put("pQRId", QRName);
                params.put("pGuardId", WorkerStaffId);
                //params.put("pLatitude", latitude);
                //params.put("pLongitude", longitude);
                if(latitude != null)
                    params.put("pLatitude", latitude);
                else
                    params.put("pLatitude", "0");
                if(longitude != null)
                    params.put("pLongitude", longitude);
                else
                    params.put("pLongitude", "0");



                if(hasScanned)
                    params.put("pIsScan", Util.HAS_SCANNED_QR);
                else
                    params.put("pIsScan", Util.HAS_NOT_SCANNED_QR);
                params.put("pCompanyId", companyId);
                params.put("pPostType", String.valueOf(postType));

                String response = null;
                try
                {
                    response = requestHandler.sendPostRequest(URLs.SAVE_SCAN_URL,params);
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

                // converting response to JSON object
                try
                {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray array = jsonObject.getJSONArray("a");

                    boolean isError = true;

                    for(int i=0; i< array.length(); i++)
                    {
                        JSONObject o = array.getJSONObject(i);

                        // if there is any record then login is succesfull
                        isError = false;
                        //isError = o.getString("IsSaved").equals("yes");
                    }

                    Log.w("staffType_fromDB ", " 444 " + s);

                    // if no error in .response

                    if(!isError)
                    {

                        // send SMS to Staff maobile with CompanyID and password to login
                        Toast.makeText(getApplicationContext()
                                , "Scan Added Successfully", Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext()
                                , "Invalid Staf Details", Toast.LENGTH_SHORT).show();

                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }

        AddScan as = new AddScan();
        as.execute();
    }

    private void loadData2()
    {
        User usr  = SharedPrefManager.getInstance(this).getUser();
        final  String CompanyId = usr.getCompanyId();
        final  String staffId = usr.getStaffId();

        /*final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading data...");
        progressDialog.show();*/

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progressDialog.dismiss();
                //{"a": [{"StudentName": "Mahi", "MobileF": "8923579979"}, {"StudentName": "ANURAG VERMA", "MobileF": "9837402809"}
                // {'a':[{'StudentMasterID':'50215','StudentName':'ARJUN','dey':'7','mnth':'3'}]}
                Log.w("Sandeep444",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("a");

                    List<String> locationList;
                    locationList = new ArrayList<>();
                    List<String> locationIDsList;
                    locationIDsList = new ArrayList<>();

                    for(int i=0; i< array.length(); i++)
                    {
                        JSONObject o = array.getJSONObject(i);
                        locationIDsList.add(o.getString("LocationID"));
                        locationList.add(o.getString("LocationName"));
                    }

                    Util util = new Util();

                    String[] locations = util.ConvertListToStringArray(locationList);
                    locationids = util.ConvertListToStringArray(locationIDsList);
                    arrayAdapter_location = new ArrayAdapter(getApplicationContext()
                            , R.layout.support_simple_spinner_dropdown_item, locations);
                    spinner_location.setAdapter(arrayAdapter_location);
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
        params.put("pCompanyId", CompanyId);
        params.put("pStaffId", staffId);

        RequestHandler rh = new RequestHandler();
        String paramsStr = rh.getPostDataString(params);
        String theURL = URLs.GET_LOCATION_BY_STAFF_URL +"?" + paramsStr;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, theURL
                , responseListener, errorListener);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        selectedLocationId = Integer.parseInt(locationids[position]);

   }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
