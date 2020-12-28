package com.example.eklass;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CompanyProfileActivity extends BaseActivity
{
    public ImageView profileImage;
    public Bitmap bitmap;
    public TextView tvLogoHeading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        profileImage = findViewById(R.id.image_activity_profile);

        tvLogoHeading = findViewById(R.id.tvProfileHeading);
        tvLogoHeading.setText("Company Logo");

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext()
                        , "Please select a profile picture..", Toast.LENGTH_SHORT).show();

                selectImage();
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this
                , Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }

        loadData();

    }



    public void loadData()
    {
        User usr = SharedPrefManager.getInstance(getApplicationContext()).getUser();

        final String mobileNo = usr.getUserMobileNo();
        final  String CompanyId = usr.getCompanyId();
        final String StaffID = usr.getStaffId();


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
                        imageURL = URLs.GET_IMAGE_URL + o.getString("imageName");
                        imageURL += "&pIsLogo=1";
                        Log.w("sandeep444" , "imageURL "+imageURL);

                        // loading the image


                        RequestOptions options = new RequestOptions()
                                .centerCrop()
                                .placeholder(R.drawable.ic_profile_grey_24dp)
                                .error(R.drawable.ic_profile_grey_24dp);

                        //Glide.with(this).load(image_url).apply(options).into(imageView);

                        Glide.with(getApplicationContext()).load(imageURL).apply(options).into(profileImage);

                    }


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
        params.put("pStaffId", StaffID);
        params.put("pCompanyId", CompanyId);
        params.put("pIsLogo", "0");

        RequestHandler rh = new RequestHandler();
        String paramsStr = rh.getPostDataString(params);
        String theURL = URLs.GET_COMPANIES_URL +"?" + paramsStr;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, theURL
                , responseListener, errorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    public void selectImage()
    {
        // open  image chooser
        Intent i = new Intent(Intent.ACTION_PICK
                , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,100);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100 && resultCode==RESULT_OK && data != null)
        {
            // getting the image URI
            Uri imageUri  = data.getData();

            // getting bitmap object from URI
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver()
                        , imageUri);

                // displaying selected image to imageview
                String filePath = util.getRealPathFromURI(this, imageUri);

                profileImage.setRotation(util.GetImageRotation(bitmap, filePath));
                profileImage.setImageURI(imageUri);

                // uploading the image

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    public void saveProfile()
    {

        try {
            if(bitmap != null)
                uploadBitmap(bitmap);
            else {
                Toast.makeText(getApplicationContext()
                        , "Please select a profile picture..", Toast.LENGTH_SHORT).show();
                return;
            }

        }
        catch (Exception e)
        {

            Toast.makeText(getApplicationContext()
                    , "Some Error Occurred: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            return;

        }

    }


    private void uploadBitmap(final Bitmap bitmap) {

        //getting the tag from the edit text
        //   final String tags = editTextTags.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading picture...");
        progressDialog.show();

        User usr = SharedPrefManager.getInstance(this).getUser();
        final String mobileNo = usr.getUserMobileNo();
        final String companyId = usr.getCompanyId();

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest =
                new VolleyMultipartRequest(Request.Method.POST, URLs.UPLOAD_IMAGE_URL,
                        new Response.Listener<NetworkResponse>() {

                            @Override
                            public void onResponse(NetworkResponse response) {
                                progressDialog.dismiss();
                                try {
                                    JSONObject obj = new JSONObject(new String(response.data));
                                    // Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(getApplicationContext()
                                        , "Uploaded profile picture successfully", Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {

                    /*
                     * If you want to add more parameters with the image
                     * you can do it here
                     * here we have only one parameter with the image
                     * which is tags
                     * */
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("pCompanyId", companyId);
                        params.put("pMobileNo", mobileNo);
                        params.put("pIsLogo", "1");
                        return params;
                    }

                    /*
                     * Here we are passing image by renaming it with a unique name
                     * */
                    @Override
                    protected Map<String, DataPart> getByteData() {
                        Map<String, DataPart> params = new HashMap<>();
                        long image_name = System.currentTimeMillis();
                        params.put("pic", new DataPart(image_name + ".png"
                                , getFileDataFromDrawable(bitmap)));
                        return params;
                    }
                };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
        {
        }
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }



}
