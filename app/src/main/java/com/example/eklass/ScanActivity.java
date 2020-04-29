package com.example.eklass;

import android.Manifest;
import android.app.ProgressDialog;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.HashMap;


public class ScanActivity extends BaseActivity {
    // view objects

    private Button btnScan;
    public TextView txtQRName, txtQRAddress, txtLatitude, txtLongitude;

    public String QRName, QRAddress;

    LocationManager locationManager;
    String latitude,longitude;

    //qr code scanner object
    private IntentIntegrator qrScan;

    private static  final int REQUEST_LOCATION=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        // view objects

        btnScan = findViewById(R.id.btnScan_activity_scan);
        txtQRName = findViewById(R.id.txtName_activity_scan);
        txtQRAddress = findViewById(R.id.txtAdress_activity_scan);
        txtLatitude = findViewById(R.id.txtLatitude_activity_scan);
        txtLongitude = findViewById(R.id.txtLongitude_activity_scan);


        //intializing scan object
        qrScan = new IntentIntegrator(this);

        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


        // attaching onClick listener

        btnScan.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

                //Check gps is enable or not

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    //Write Function To enable gps

                    OnGPS();
                }
                else
                {
                    //GPS is already On then

                    getLocation();
                }

                qrScan.setBeepEnabled(true);

                qrScan.setOrientationLocked(true);
                qrScan.setCaptureActivity(CaptureActivityPortrait.class);
                qrScan.initiateScan();

                //addScan();

                //IntentIntegrator.parseActivityResult()

                /*try
                {
                    SaveScan();
                } catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }*/
                //GetLocation();
            }
        });



        findViewById(R.id.btnScan_activity_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });


    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();

            }
            else // if qr contains data
            {
                try {   // converting data to JSON
                    // {"name":"Sandeep", "address":"Shop 14"}

                    Log.w("sandeep", " result.getContents()  " + result.getContents().toString());
                    JSONObject obj = new JSONObject(result.getContents());

                    Log.w("sandeep", " 5555  " + obj.getString("name"));
                    txtQRName.setText(obj.getString("name"));
                    QRName = obj.getString("name");
                    txtQRAddress.setText(obj.getString("address"));
                    QRAddress = obj.getString("address");


                    addScan();


                } catch (JSONException e) {
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void GetLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                !=  PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this
                    ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                    , 1);

            if(checkLocationPermission())
            {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
            }

            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            txtLatitude.setText((int) latitude);
            txtLongitude.setText((int) longitude);

            // TODO: Consider calling
            //    Activity#requestPermissions
            return;
        }

    }




    public boolean checkLocationPermission()
    {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);

        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private void OnGPS() {

        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
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

        if (ActivityCompat.checkSelfPermission(ScanActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ScanActivity.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps !=null)
            {
                double lat=LocationGps.getLatitude();
                double longi=LocationGps.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                txtLatitude.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
            }
            else if (LocationNetwork !=null)
            {
                double lat=LocationNetwork.getLatitude();
                double longi=LocationNetwork.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                txtLatitude.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
            }
            else if (LocationPassive !=null)
            {
                double lat=LocationPassive.getLatitude();
                double longi=LocationPassive.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                txtLatitude.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
            }
            else
            {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }

            //Thats All Run Your App
        }

    }


    private void addScan()
    {
        final  String companyId = SharedPrefManager.getInstance(this).getUser().getCompanyId();
        final String  qrName =  txtQRName.getText().toString();
        final String  WorkerStaffId = SharedPrefManager.getInstance(this).getUser().getStaffId();
        //final  String userPassword = etStaffPassword.getText().toString();


        Log.w("sandeep", "444 IsStaff = " + qrName);

        // if everything is fine

        class AddScan extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();

                params.put("pQRName", QRName);
                params.put("pGuardId", WorkerStaffId);
                params.put("pLatitude", latitude);
                params.put("pLongitude", longitude);
                params.put("pCompanyId", companyId);

                Log.w("sandeep", "pQRName 999 " + qrName);




                String response = null;
                try
                {
                    response = requestHandler.sendPostRequest(URLs.SAVESCAN_URL ,params);
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

                Log.w("sandeep", "response 222 " + s);

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


    private void SaveScanOld() throws MalformedURLException {
        final String staff_mobileNo = SharedPrefManager.getInstance(getApplicationContext()).getUser().UserMobileNo;

        final String txtQR_Name = txtQRName.getText().toString();
        final String txtQR_Address = txtQRAddress.getText().toString();
        final String txt_latitude =  latitude; //txtLatitude.getText().toString();
        final String txt_longitude = longitude; //txtLongitude.getText().toString();

        final  String CompanyId = SharedPrefManager.getInstance(this).getUser().getCompanyId();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving Scan...");
        progressDialog.show();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //{"a": [{"StudentName": "Mahi", "MobileF": "8923579979"}, {"StudentName": "ANURAG VERMA", "MobileF": "9837402809"}
                // {'a':[{'StudentMasterID':'50215','StudentName':'ARJUN','dey':'7','mnth':'3'}]}
                Log.w("Scan444",response);

                Toast.makeText(getApplicationContext()
                        , "Scan Saved Successfully", Toast.LENGTH_LONG).show();



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

        Log.w("sandeep", "txtQR_Name  " + QRName);

        params.put("pMobileNo", staff_mobileNo);
        params.put("pQRName", txtQR_Name);
        params.put("pQRAddress", txtQR_Address);
        params.put("pLatitude", txt_latitude);
        params.put("pLongitude", txt_longitude);
        params.put("pCompanyId", CompanyId);


        String paramsStr = rh.getPostDataString(params);
        String theURL = URLs.SAVESCAN_URL +"?" + paramsStr;

        /*StringRequest stringRequest = new StringRequest(Request.Method.POST, theURL
                , responseListener, errorListener);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/

    }





}
