package com.example.eklass;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

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

public class AddLocationActivity extends BaseActivity
{

    EditText etLocationName, etLatitude, etLongitude;
    Boolean isUpdate = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlocation);

        etLocationName = findViewById(R.id.etLocationName_activity_location);
        etLatitude = findViewById(R.id.etLatitude_activity_location);
        etLongitude = findViewById(R.id.etLongitude_activity_location);

        TextView pageHeading  = findViewById(R.id.tvHeader_activity_managers_workers);
        ImageView imageViewProfileHeading, imageViewLogoHeading;
        imageViewLogoHeading=findViewById(R.id.imageLogo_activity_managers_worker);
        imageViewProfileHeading=findViewById(R.id.imageProfile_activity_managers_worker);

        util.SetHeadings(getApplicationContext(), pageHeading
                , "Add Locations"
                , imageViewLogoHeading
                , imageViewProfileHeading
                , BaseActivity.themeNo);


        String update  = getIntent().getStringExtra("isUpdate");
        if(update!= null)
            isUpdate = update.equals("yes");

        if(isUpdate)
        {
            etLocationName.setText(getIntent().getStringExtra("locationName"));

        }

     /*findViewById(R.id.btnSaveLocation_activity_location).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

                addLocation();

         }
     });
*/
    }

   public void addLocation()
    {
        User usr = SharedPrefManager.getInstance(this).getUser();

        final String companyId = usr.getCompanyId();
        final String  locationName = etLocationName.getText().toString();
        final String  latitude = etLatitude.getText().toString();
        final String longitude = etLongitude.getText().toString();

        if(TextUtils.isEmpty(locationName))
        {
            etLocationName.setError("Please Enter Your Mobile Number");
            etLocationName.requestFocus();

            return;
        }


        // if everything is fine

        class AddLocation extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();

                params.put("pLocationName", locationName);
                params.put("pLatitude", latitude);
                params.put("pLongitude", longitude);
                params.put("pCompanyId", companyId);

                String response = null;

                String urlLocation = URLs.ADD_LOCATION_URL;

                if(isUpdate)
                    urlLocation = URLs.UPDATE_LOCATION_URL;

                try
                {
                    response = requestHandler.sendPostRequest(urlLocation ,params);
                }
                catch (MalformedURLException e)
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
                    }

                    if(!isError)
                    {
                        // send SMS to Staff maobile with CompanyID and password to login
                        Toast.makeText(getApplicationContext()
                                , "Location Added Successfully", Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext()
                                , "Invalid Details", Toast.LENGTH_SHORT).show();

                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }


            }
        }
        AddLocation al = new AddLocation();
        al.execute();
    }



}
