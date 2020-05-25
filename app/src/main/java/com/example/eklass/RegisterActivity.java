package com.example.eklass;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity
{
    EditText etName_Register, etPersonName, etMobileNo_Register, etPassword_Register;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);

        etName_Register = findViewById(R.id.etName_Register);
        etPersonName  = findViewById(R.id.etPersonName_Register);
        etMobileNo_Register = findViewById(R.id.etMobileNo_Register);
        etPassword_Register = findViewById(R.id.etPassword_Register);

        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });

    }

    private void RegisterUser()
    {
        final String register_name = etName_Register.getText().toString();
        final String person_name = etPersonName.getText().toString();
        final String register_MobileNo = etMobileNo_Register.getText().toString();
        final  String register_Password = etPassword_Register.getText().toString();


        if(TextUtils.isEmpty(register_name))
        {
            etName_Register.setError("Please Enter Your Mobile Number");
            etName_Register.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(person_name))
        {
            etPersonName.setError("Please Enter Your Name");
            etPersonName.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(register_MobileNo))
        {
            etMobileNo_Register.setError("Please Enter Your Mobile No");
            etMobileNo_Register.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(register_Password))
        {
            etPassword_Register.setError("Please Create Your Password" );
            etPassword_Register.requestFocus();
            return;
        }


        final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Registering User...");
            progressDialog.show();

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    //{"a": [{"StudentName": "Mahi", "MobileF": "8923579979"}, {"StudentName": "ANURAG VERMA", "MobileF": "9837402809"}

                    Log.w("Register444",response);

                    Toast.makeText(getApplicationContext()
                            , "Registration Completed Successfully", Toast.LENGTH_LONG).show();

                    // starting the login activity
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

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

            params.put("rName", register_name);
            params.put("rPersonName", person_name);
            params.put("rMobileNo", register_MobileNo);
            params.put("rPassword", register_Password);

            RequestHandler rh = new RequestHandler();
            String paramsStr = rh.getPostDataString(params);

            String theURL = URLs.REGISTER_URL +"?" + paramsStr;

            StringRequest stringRequest = new StringRequest(Request.Method.POST, theURL
                    , responseListener, errorListener);

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }







}
