package com.example.eklass;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharedPrefManager
{
    private static final String SHARED_PREF_NAME="eclasssharedpref";
    private static final String KEY_MOBILENO="keymobileno";


    private static SharedPrefManager mInstance;

    private static Context mCtx;

    private SharedPrefManager(Context context)
    {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context)
    {
        if(mInstance == null)
            mInstance = new SharedPrefManager(context);
        return mInstance;
    }

    // this method to let user login
    // this method will store the user data in shared preferneces

    public void userLogin(User user)
    {
        SharedPreferences sharedPreferences
                    = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_MOBILENO, user.UserMobileNo);
        editor.apply();

    }

    // this method will check if the user is already logged in or not
    public boolean isLoggedIn()
    {

        SharedPreferences sharedPreferences
                = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return  sharedPreferences.getString(KEY_MOBILENO, null)!= null;

    }

    // this method will give the logged in User
    public User getUser() {

        SharedPreferences sharedPreferences =
                                            mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        User theUser = new User(sharedPreferences.getString(KEY_MOBILENO, null));
         return theUser;
    }

    // this method will logout the user and will show main screen

    public void logout()
    {
        SharedPreferences sharedPreferences
                = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor  = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        mCtx.startActivity(new Intent(mCtx, MainActivity.class));

    }

}