package com.example.eklass;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.HashMap;


public class ScanActivity extends BaseActivity {
    // view objects

    private Button btnScan;
    private TextView txtQRName, txtQRAddress, txtLatitude, txtLongitude;

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

                try
                {
                    SaveScan();
                }
                catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }
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

            } else // if qr contains data
            {
                try {   // converting data to JSON
                    // {"name":"Sandeep", "address":"Shop 14"}

                    JSONObject obj = new JSONObject(result.getContents());

                    txtQRName.setText(obj.getString("name"));
                    txtQRAddress.setText(obj.getString("address"));

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
            // here to request the missing permissions, and then overriding
          /*  public void onRequestPermissionsResult(int requestCode, String[] permissions,
            {

            }*/

            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.




            return;
        }
       /* Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();*/

       // txtLatitude.setText((int) latitude);
        //txtLongitude.setText((int) longitude);

        txtLatitude.setText((int) 40);
        txtLongitude.setText((int) 59);
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


    private void SaveScan() throws MalformedURLException {
        final String staff_mobileNo = SharedPrefManager.getInstance(getApplicationContext()).getUser().UserMobileNo;
        final String txtQR_Name = txtQRName.getText().toString();
        final String txtQR_Address = txtQRAddress.getText().toString();
        final String txt_latitude = txtLatitude.getText().toString();
        final String txt_longitude = txtLongitude.getText().toString();


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

        params.put("sMobileNo", staff_mobileNo);
        params.put("sQRName", txtQR_Name);
        params.put("sQRAddress", txtQR_Address);
        params.put("sLatitude", txt_latitude);
        params.put("sLongitude", txt_longitude);


        String paramsStr = rh.getPostDataString(params);
        String theURL = URLs.SAVESCAN_URL +"?" + paramsStr;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, theURL
                , responseListener, errorListener);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }





}
