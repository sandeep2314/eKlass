package com.example.eklass;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharedPrefManager
{
    private static final String SHARED_PREF_NAME="eclasssharedpref";
    private static final String KEY_MOBILENO="keymobileno";

    private static final String KEY_STAFFID="keystaffid";
    private static final String KEY_STAFFTYPE="keystafftype";
    private static final String KEY_STAFFNAME="keystaffname";

    private static final String KEY_DESIGNATIONID="keydesignationid";
    private static final String KEY_DESIGNATIONNAME="keydesignationname";

    private static final String KEY_COMPANYID="keycompanyid";
    private static final String KEY_COMPANYNAME="keycompanyname";

    private static final String KEY_THEME="keytheme";


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
        editor.putString(KEY_STAFFTYPE, user.StaffType);
        editor.putString(KEY_STAFFID, user.getStaffId());
        editor.putString(KEY_STAFFNAME, user.getStaffName());

        editor.putString(KEY_DESIGNATIONID, user.getDesignationId());
        editor.putString(KEY_DESIGNATIONNAME, user.getDesignationName());


        editor.putString(KEY_COMPANYID, user.getCompanyId());
        editor.putString(KEY_COMPANYNAME, user.getCompanyName());
        editor.putInt(KEY_THEME,  user.getUserTheme());

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

        User usr = new User(
                sharedPreferences.getString(KEY_MOBILENO, null)
                , sharedPreferences.getString(KEY_STAFFTYPE, null)
                , sharedPreferences.getString(KEY_STAFFID, null)
                , sharedPreferences.getString(KEY_STAFFNAME, null)

                , sharedPreferences.getString(KEY_DESIGNATIONID, null)
                , sharedPreferences.getString(KEY_DESIGNATIONNAME, null)

                , sharedPreferences.getString(KEY_COMPANYID, null)
                , sharedPreferences.getString(KEY_COMPANYNAME, null)
                , sharedPreferences.getInt(KEY_THEME, Util.WHITE_THEME)
        );

        //return new User(sharedPreferences.getString(KEY_MOBILENO, null));
        return usr;
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
