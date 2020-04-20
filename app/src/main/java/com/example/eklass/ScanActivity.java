package com.example.eklass;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;


public class ScanActivity extends AppCompatActivity {
    // view objects

    private Button btnScan;
    private TextView txtName, txtAddress, txtLatitude, txtLongitude;

    //qr code scanner object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        // view objects

        btnScan = findViewById(R.id.btnScan_activity_scan);
        txtName = findViewById(R.id.txtName_activity_scan);
        txtAddress = findViewById(R.id.txtAdress_activity_scan);
        txtLatitude = findViewById(R.id.txtLatitude_activity_scan);
        txtLongitude = findViewById(R.id.txtLongitude_activity_scan);


        //intializing scan object
        qrScan = new IntentIntegrator(this);


        // attaching onClick listener

        btnScan.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                qrScan.setBeepEnabled(true);

                qrScan.setOrientationLocked(true);
                qrScan.setCaptureActivity(CaptureActivityPortrait.class);
                qrScan.initiateScan();
                                //GetLocation();
            }
        });

    }

    @Override
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

                    txtName.setText(obj.getString("name"));
                    txtAddress.setText(obj.getString("address"));

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


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    txtLatitude.setText((int) 66);
                    txtLongitude.setText((int) 77);

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public boolean checkLocationPermission()
    {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);

        return (res == PackageManager.PERMISSION_GRANTED);
    }

}
