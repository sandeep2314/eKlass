package com.example.eklass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.net.MalformedURLException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    EditText et_MobileNo, et_Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_MobileNo = (EditText) findViewById(R.id.etMobileNo);
        et_Password = (EditText) findViewById(R.id.etPassword);


        // if the user is logged in it will go to mychildren activity
        if(SharedPrefManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity( new Intent(this, MyChildrenActivity.class));

            return;
        }


        findViewById(R.id.btnLogin).setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {

                        // login user




                    }
                }

        );


    }

    private void userLogin()
    {
        final String userMobileNo = et_MobileNo.getText().toString();
        final  String userPassword = et_Password.getText().toString();

        if(TextUtils.isEmpty(userMobileNo))
        {
            et_MobileNo.setError("Please Enter Your Mobile Number");
            et_MobileNo.requestFocus();

            return;
        }


        // if everything is fine

        class UserLogin extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();

                params.put("mobileNo", userMobileNo);
                params.put("password", userPassword);

                String reponse = null;
                try
                {
                    reponse = requestHandler.sendPostRequest(URLs.LOGIN_URL ,params);
                } catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }

                return reponse;
            }
        }


    }




}
