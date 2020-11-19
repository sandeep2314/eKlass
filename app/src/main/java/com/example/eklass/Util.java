package com.example.eklass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.PublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutionException;


import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class Util {

    public static final int BLACK_THEME = 1;
    public static final int WHITE_THEME = 2;

    public static final String USER_TYPE_ADMIN = "-1";
    public static final String USER_TYPE_WORKER = "1";
    public static final String USER_TYPE_MANAGER = "2";

    public static final String HAS_SCANNED_QR = "1";
    public static final String HAS_NOT_SCANNED_QR = "0";

    public static final int ATTENDANCE_DAY_IN = 1;
    public static final int ATTENDANCE_DAY_OUT = 2;
    public static final int ATTENDANCE_BETWEEN = 3;

    public static final String NO_COMPANY = "-1";


    public String[] ConvertListToStringArray(List<String> theList) {
        String[] items = new String[theList.size()];
        for (int i = 0; i < theList.size(); i++)
            items[i] = theList.get(i).toString();
        return items;
    }

    public void SetImage(final Context ctx, final ImageView imageView, final boolean isLogo) {

        Log.w("Sandeep4444", "SetImage");

        User usr = SharedPrefManager.getInstance(ctx).getUser();

        final String staffId = usr.getStaffId();
        final String CompanyId = usr.getCompanyId();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //{"a": [{"StudentName": "Mahi", "MobileF": "8923579979"}, {"StudentName": "ANURAG VERMA", "MobileF": "9837402809"}
                // {'a':[{'StudentMasterID':'50215','StudentName':'ARJUN','dey':'7','mnth':'3'}]}
                Log.w("Sandeep4444", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("a");

                    Staff staff_fromDB;
                    //http://103.233.24.31:8080/getimage?fileName=bpslogo.jpg
                    //http://103.233.24.31:8080/getimage?fileName=bpslogo.jpg&pIsLogo=1
                    String imageURL = "";


                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = array.getJSONObject(i);

                        String theURL = "";
                        if (isLogo) {
                            imageURL = URLs.GET_IMAGE_URL + o.getString("imageName");
                            imageURL += "&pIsLogo=1";
                        } else {
                            imageURL = URLs.GET_IMAGE_URL + o.getString("imageURL");
                            imageURL += "&pIsLogo=0";
                        }
                    }

                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.ic_profile_grey_24dp)
                            .error(R.drawable.ic_profile_grey_24dp);

                    Glide.with(ctx).load(imageURL).apply(options).into(imageView);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, error.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        };

        HashMap<String, String> params = new HashMap<>();
        params.put("pStaffId", staffId);
        params.put("pCompanyId", CompanyId);

        String the_url = "";
        if (isLogo) {
            params.put("pIsLogo", "1");
            the_url = URLs.GET_COMPANIES_URL;
        } else {
            params.put("pIsLogo", "0");
            the_url = URLs.GET_STAFF_URL;
        }

        RequestHandler rh = new RequestHandler();
        String paramsStr = rh.getPostDataString(params);

        String theURL = the_url + "?" + paramsStr;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, theURL
                , responseListener, errorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

    }

    public void SetHeadings(Context ctx, TextView tvPageHeading
            , String pageName
            , ImageView logoImage
            , ImageView profileImage
            , int themeNo) {
        User user = SharedPrefManager.getInstance(ctx).getUser();

        String heading = user.getStaffName();
        heading += "\n" + user.getCompanyName();
        heading += "\n" + pageName;


        //mTextView.setTextColor(ContextCompat.getColor(context, R.color.<name_of_color>));
        // mTextView.setTextColor(Color.parseColor("#bdbdbd"))
        // black theme = 1
        // grey theme = 2
        if (themeNo == BLACK_THEME) {
            tvPageHeading.setTextColor(ContextCompat.getColor(ctx, android.R.color.white));
        } else {
            // black font color
            //pageHeading.setTextColor(Color.parseColor("#000000"));
            tvPageHeading.setTextColor(ContextCompat.getColor(ctx, R.color.colorDarkGrey));
        }

        SetImage(ctx, logoImage, true);
        SetImage(ctx, profileImage, false);

        // loading the image
        //Glide.with(ctx).load(staff.getStaffImage()).apply(options).into(holder.imageStaff);
        //Glide.with(ctx).load(imageURL_logo).apply(options).into(logo);

        tvPageHeading.setText(heading);

    }

    public void CheckAll(CheckBox ckb) {
        if (ckb.isChecked())
            ckb.setChecked(false);
        else
            ckb.setChecked(true);
    }

    public String GetCheckedIds() {
        String selectedIds = "";

        return selectedIds;

    }

    public void DeleteRecord(final Context ctx, String ids, final String theURL) {
/*
        final ProgressDialog progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("loading data...");
        progressDialog.show();
*/

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progressDialog.dismiss();
                //{"a": [{"IsDeleted": "yes"}]}
                Log.w("Sandeep444", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("a");

                    String deleted = "no";
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = array.getJSONObject(i);
                        deleted = o.getString("IsDeleted");
                    }

                    if (deleted.equals("yes"))
                        Toast.makeText(ctx, "Records Deleted Successfully"
                                , Toast.LENGTH_LONG).show();
                    else {
                        if (theURL.equals(URLs.DEL_STAFF_URL))
                            Toast.makeText(ctx, "Records can not be Deleted  " +
                                            "Please first delete from Staff-Assigned To Location"
                                    , Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, error.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        };


        User user = SharedPrefManager.getInstance(ctx).getUser();

        HashMap<String, String> params = new HashMap<>();
        //params.put("pStaffId", staffId);

        params.put("pCompanyId", user.getCompanyId());
        params.put("pDelIds", ids);

        RequestHandler rh = new RequestHandler();
        String paramsStr = rh.getPostDataString(params);
        String delURL = theURL + "?" + paramsStr;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, delURL
                , responseListener, errorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);


    }


    public Bitmap generateQrCode(String myCodeText) throws WriterException {

        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // H = 30% damage

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        int size = 256;

        BitMatrix bitMatrix = qrCodeWriter.encode(myCodeText
                , BarcodeFormat.QR_CODE, size, size);

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        return bitmap;
    }

    public String storeImage(Context ctx, Bitmap image, String QRCodeName) {
        File pictureFile = getOutputMediaFile(ctx, QRCodeName);

        String fileName = pictureFile.getName();

        Log.d("Sandeep7878", "fileName : " + pictureFile.getAbsolutePath());

        if (pictureFile == null) {
            Log.d("Sandeep",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return "";
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("Sandeep", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("Sandeep", "Error accessing file: " + e.getMessage());
        }
        return fileName;
    }

    private File getOutputMediaFile(Context ctx, String QRCodeName) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + ctx.getPackageName()
                + "/Files");

        //+ getGalleryPath());

        //File mediaStorageDir = getGalleryPath();

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = QRCodeName + "_" + timeStamp + ".bmp";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    private File getGalleryPath() {

        File folder = Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DCIM);

        if (!folder.exists()) {
            folder.mkdir();
        }

        return folder;
    }


    public String GenerateLocationQRCode(final Context ctx) {
        String pathName = "";

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("a");

                    String qrCodeFileName = "";

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = array.getJSONObject(i);

                        Bitmap bitmap = generateQrCode(o.getString("LocationID"));
                        if (bitmap != null)
                            qrCodeFileName = storeImage(ctx
                                    , bitmap, o.getString("LocationName"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };


        User user = SharedPrefManager.getInstance(ctx).getUser();

        HashMap<String, String> params = new HashMap<>();

        params.put("pCompanyId", user.getCompanyId());
        RequestHandler rh = new RequestHandler();
        String paramsStr = rh.getPostDataString(params);
        String theURL = URLs.GET_LOCATION_URL + "?" + paramsStr;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, theURL
                , responseListener, errorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

        return pathName;

    }



/*
    public void addStaff(final Context ctx, View v )
    {
        User usr = SharedPrefManager.getInstance(ctx).getUser();

        TextView etStaffName = v.findViewById(R.id.etStaffName_activity_staff);
        final TextView etStaffMobile = v.findViewById(R.id.etStaffMobileNo_activity_staff);
        TextView etStaffPassword= v.findViewById(R.id.etStaffPassword_activity_staff);
        final TextView rdGroupStaffType = v.findViewById(R.id.radioGroup_staff_activity_main);

        final  String userCompantId = usr.getCompanyId();
        final String  userName = etStaffName.getText().toString();
        final String  userMobileNo = etStaffMobile.getText().toString();
        final  String userPassword = etStaffPassword.getText().toString();

        final  String IsStaff;
        // 1 is worker, 2 is Manager
        IsStaff = rdManager.isChecked()?"2":"1";

        if(TextUtils.isEmpty(userMobileNo))
        {
            etStaffMobile.setError("Please Enter Your Mobile Number");
            etStaffMobile.requestFocus();

            return;
        }


        // if everything is fine

        class AddStaff extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();

                params.put("pCompanyId", userCompantId);
                params.put("pMobileNo", userMobileNo);
                params.put("pPassword", userPassword);
                params.put("pStaffName", userName);
                params.put("pIsStaff", IsStaff);

                String response = null;
                try
                {
                    response = requestHandler.sendPostRequest(URLs.ADDSTAFF_URL ,params);
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
                        Toast.makeText(ctx
                                , "Staff Added Sucessfully", Toast.LENGTH_LONG).show();

        */
/*                etStaffName.setText("");
                        etStaffMobile.setText("");
                        etStaffPassword.setText("");

                        etStaffName.requestFocus();
*//*


                    }
                    else
                    {
                        Toast.makeText(ctx
                                , "Invalid Staf Details", Toast.LENGTH_SHORT).show();

                        etStaffMobile.setText(userMobileNo);


                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }


            }
        }

        AddStaff as = new AddStaff();
        as.execute();
    }
*/

    public String GetString(Object str) {
        if (str == null)
            return "";
        return str.toString();
    }

    public int GetSpinnerPosition(String[] idsArray, int param) {
        int position = 0;

        if (idsArray != null) {
            for (int i = 0; i < idsArray.length; i++) {
                if (idsArray[i].equals(String.valueOf(param))) {
                    position = i;
                    break;
                }

            }
        }
        return position;
    }

    public String GetPostOfDay(String currentDateTime, List<Duty> dutyList, Boolean isFirst) {
        String firstPostedAt = "";
        String pattern = "dd-MM-yyyy";

        Date dt = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            dt = sdf.parse(currentDateTime);
            //  dtOrg = sdfOrg.parse(currentDateTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date[] dtArray = ConvertListToDateArray(dutyList, pattern);

        String patternOrg = "dd-MM-yyyy HH:mm:ss";
        Date[] dtArrayOrg = ConvertListToDateArray(dutyList, patternOrg);

        List<String> postingsOfDay;
        postingsOfDay = new ArrayList<>();

        // put all postings of one day in one date array
        for (int i = 0; i < dtArray.length; i++) {

            if (dt != null && dt.equals(dtArray[i])) {
                sdf = new SimpleDateFormat("HH:mm:ss");
                postingsOfDay.add(sdf.format(dtArrayOrg[i]));
                Log.w("sandeep777", "dt " + dt + "dtArray[i] " + dtArrayOrg[i]);
            }
        }


        if (!isFirst)
            firstPostedAt = "" + postingsOfDay.get(0);
        else
            firstPostedAt = "" + postingsOfDay.get(postingsOfDay.size() - 1);


        return firstPostedAt;
    }


    public String GetLastPostedDateTime(String currentDateTime, List<Duty> dutyList) {
        String lastPosted = "";
        String pattern = "dd-MM-yyyy HH:mm:ss";

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date dt = null;
        Date dtBefore = null;
        try {
            dt = sdf.parse(currentDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date[] dtArray = ConvertListToDateArray(dutyList, pattern);

        // get the datetime before the current date time
        for (int i = 0; i < dtArray.length; i++) {
            if (dt != null && dt.compareTo(dtArray[i]) == 0) {
                if (dtArray.length == i + 1)
                    dtBefore = dtArray[i];
                else
                    dtBefore = dtArray[i + 1];
                break;
            }
        }
        lastPosted = GetDuration(dt, dtBefore);

        return lastPosted;
    }


    public String GetDuration(Date dt, Date dtBefore) {
        String lastPosted = "";

        long diff = 1;
        if (dt != null && dtBefore != null)
            diff = dt.getTime() - dtBefore.getTime();

        long diffSeconds = diff / (1000);
        double d = diffSeconds / 60;
        long diffMinutes = (long) Math.floor(d);
        diffSeconds = diffSeconds - (diffMinutes * 60);

        d = diffMinutes / 60;
        long diffHours = (long) Math.floor(d);
        diffMinutes = diffMinutes - (diffHours * 60);

        d = diffHours / 24;
        long diffDays = (long) Math.floor(d);
        diffHours = diffHours - (diffDays * 24);

        lastPosted = "d:" + diffDays + " H:" + diffHours + " M:" + diffMinutes + " S:" + diffSeconds;

        return lastPosted;

    }

    public Date[] ConvertListToDateArray(List<Duty> theList, String pattern) {
        Date[] items = new Date[theList.size()];
        //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        Date dt = null;
        Duty duty = null;
        for (int i = 0; i < theList.size(); i++) {
            duty = (Duty) theList.get(i);
            try {
                dt = sdf.parse(duty.getDutyDateTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            items[i] = dt;
        }
        return items;
    }

    public void CreateMonthlyReport(String monthYear, String pattern) {

        String mnth = "JUN";
        String yr = "20";
        pattern = "dd-MM-yyyy";

        Integer[] dys = new Integer[31];

        String dtStr = "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date dt = null;

        // create valid days of a month
        for (int i = 1; i < 32; i++) {
            dtStr = i + "/" + mnth + "/" + yr;
            try {
                dt = sdf.parse(dtStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dys[i] = i;
        }
    }

    public String GetDayPosting(Duty duty, List<Duty> dutyList, int postType) {

        String firstPostedAt = "";

        Date dt = null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            dt = sdf.parse(duty.getDutyDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (dt != null)
            //&& duty.getPostType() == postType)
            firstPostedAt = sdf.format(dt);

        if (duty.getPostType() == postType)
            firstPostedAt = duty.getDutyDateTime();

        return firstPostedAt;
    }

    public String GetDayPostingOld(Duty duty, List<Duty> dutyList, int postType) {

        String firstPostedAt = "";
        String pattern = "dd-MM-yyyy";

        Date dt = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            dt = sdf.parse(duty.getDutyDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date[] dtArray = ConvertListToDateArray(dutyList, pattern);

        String patternOrg = "dd-MM-yyyy HH:mm:ss";
        Date[] dtArrayOrg = ConvertListToDateArray(dutyList, patternOrg);

        List<String> postingsOfDay;
        postingsOfDay = new ArrayList<>();

        // put all postings of one day in one date array
        for (int i = 0; i < dtArray.length; i++) {


            if (dt != null && dt.equals(dtArray[i])
                    && duty.getPostType() == postType) {

                sdf = new SimpleDateFormat("HH:mm:ss");
                postingsOfDay.add(sdf.format(dtArrayOrg[i]));
            }

            Log.w("Sandeep777  22 ", sdf.format(dtArrayOrg[i]));
        }

        if (postingsOfDay.size() >= 1)
            firstPostedAt = "" + postingsOfDay.get(postingsOfDay.size() - 1);

        // if post type is DAY IN then show blank
        if (duty.getPostType() == ATTENDANCE_DAY_IN)
            firstPostedAt = "";

        return firstPostedAt;
    }

    public String GetDayIN(Duty duty, List<Duty> dutyList) {
        String firstDayIN = "";
        String pattern = "dd-MM-yyyy";

        Date dt = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            dt = sdf.parse(duty.getDutyDateTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date[] dtArray = ConvertListToDateArray(dutyList, pattern);

        String patternOrg = "dd-MM-yyyy HH:mm:ss";
        Date[] dtArrayOrg = ConvertListToDateArray(dutyList, patternOrg);

        List<String> postingsOfDay;
        postingsOfDay = new ArrayList<>();

        // put all postings of one day in one date array

        // get first DayIN post in the day
        for (int i = 0; i < dtArray.length; i++) {
            if (dt != null && dt.equals(dtArray[i]))
            //&& duty.getPostType() == ATTENDANCE_DAY_IN)
            {
                //sdf =  new SimpleDateFormat("HH:mm:ss");
                sdf = new SimpleDateFormat(patternOrg);
                postingsOfDay.add(sdf.format(dtArrayOrg[i]));
            }
        }

        if (postingsOfDay.size() >= 1)
            firstDayIN = "" + postingsOfDay.get(postingsOfDay.size() - 1);

        return firstDayIN;
    }


    public void UpdateAttendanceType(final Context ctx
            , int attendanceType, final String theURL) {
        // update attendance status to tblStaff (PostType)

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progressDialog.dismiss();
                //{"a": [{"IsDeleted": "yes"}]}
                Log.w("Sandeep65", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("a");

                    String deleted = "no";
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = array.getJSONObject(i);
                        deleted = o.getString("IsUpdateds");
                    }

                    if (deleted.equals("yes"))
                        Toast.makeText(ctx, "Records Updated Successfully"
                                , Toast.LENGTH_LONG).show();
                    else {
                        if (theURL.equals(URLs.DEL_STAFF_URL))
                            Toast.makeText(ctx, "Records can not be Updated  "
                                    , Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, error.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        };


        User user = SharedPrefManager.getInstance(ctx).getUser();

        HashMap<String, String> params = new HashMap<>();
        //params.put("pStaffId", staffId);

        params.put("pCompanyId", user.getCompanyId());
        params.put("pStaffId", user.getStaffId());
        params.put("pAttendanceType", String.valueOf(attendanceType));

        RequestHandler rh = new RequestHandler();
        String paramsStr = rh.getPostDataString(params);
        String delURL = theURL + "?" + paramsStr;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, delURL
                , responseListener, errorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);


    }

    public Date StrToDate(String dtStr, String pattern) {

        Date dt = null;
        //String pattern = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            dt = sdf.parse(dtStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }

    public String GetDayHours(String InTime, String OutTime) {
        String lastPosted = "";

        Date dt, dtBefore = null;

        dt = StrToDate(InTime, "dd-MM-yyyy HH:mm:ss");
        dtBefore = StrToDate(InTime, "dd-MM-yyyy HH:mm:ss");

        long diff = 1;
        if (dt != null && dtBefore != null)
            diff = dt.getTime() - dtBefore.getTime();

        long diffSeconds = diff / (1000);
        double d = diffSeconds / 60;
        long diffMinutes = (long) Math.floor(d);
        diffSeconds = diffSeconds - (diffMinutes * 60);

        d = diffMinutes / 60;
        long diffHours = (long) Math.floor(d);
        diffMinutes = diffMinutes - (diffHours * 60);

        d = diffHours / 24;
        long diffDays = (long) Math.floor(d);
        diffHours = diffHours - (diffDays * 24);

        lastPosted = "d:" + diffDays + " H:" + diffHours + " M:" + diffMinutes + " S:" + diffSeconds;

        return lastPosted;

    }


    public String GenerateTimeSheet(Context ctx, String fileName
            , Integer mnth, Integer yr, List<TimeSheet> timeSheetList) {

        String path;
        File dir;
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e("Failed", "Storage not available or read only");
            return "Failed";
        }
        boolean success = false;

        //New Workbook
        Workbook wb = new HSSFWorkbook();

        Cell c = null;

        //Cell style for header row
        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

        //New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet("TimeSheet");

        // Generate column headings
        Row row = null;

        row = sheet1.createRow(0);

        c = row.createCell(0);
        c.setCellValue("Serial Number");
        c.setCellStyle(cs);

        c = row.createCell(1);
        c.setCellValue("Staff ID");
        c.setCellStyle(cs);

        c = row.createCell(2);
        c.setCellValue("Staff Name");
        c.setCellStyle(cs);

        c = row.createCell(3);
        c.setCellValue("Designation");
        c.setCellStyle(cs);

        c = row.createCell(4);
        c.setCellValue("Staff Hierarchy");
        c.setCellStyle(cs);

        for (int i = 1; i < 35; i++) {
            c = row.createCell(i + 4);
            c.setCellValue(i + "-" + mnth + "-2020");
            c.setCellStyle(cs);
        }

        sheet1.setColumnWidth(0, (15 * 100));
        sheet1.setColumnWidth(1, (15 * 100));
        sheet1.setColumnWidth(2, (15 * 100));
        sheet1.setColumnWidth(3, (15 * 100));
        sheet1.setColumnWidth(4, (15 * 100));
        // Get all staff in a column
        // Get attendance of one staff for the given month

        int colNo = 0;
        int rw = 1;
        String cellValue = "";

        TimeSheet timeSheet;

        for (int i = 0; i < timeSheetList.size(); i++) {
            row = sheet1.createRow(rw);
            timeSheet = timeSheetList.get(i);
            Log.w("Sandeep9922", " " + timeSheet.getStaffId());
            for (int col = 0; col < 35; col++) {
                if(col==0)
                {
                    cellValue =  String.valueOf(col);
                }
                else if (col == 1) // Staff Name
                {
                    cellValue =  timeSheet.getStaffId();
                                    }
                else if(col == 2)
                {
                    cellValue =  timeSheet.getStaffName();
                    colNo +=1;
                }
                else if(col == 3)
                {
                    cellValue =  timeSheet.getDesignation();
                    colNo +=1;
                }
                else if(col == 4)
                {
                    cellValue =  timeSheet.getHierarchy();
                    colNo +=1;
                }
                else if (col == 5) {
                    cellValue =  FormatCellValue(timeSheet.getDAY1().getINTIME(),timeSheet.getDAY1().getINTIME(), "IN");
                    cellValue += FormatCellValue(timeSheet.getDAY1().getOUTTIME(),timeSheet.getDAY1().getOUTTIME(), "OUT");
                    cellValue += FormatCellValue(timeSheet.getDAY1().getOUTTIME(),timeSheet.getDAY1().getHrs(), "HRS");
                    //cellValue += FormatCellValue(timeSheet.getDay1_INTIME(),timeSheet.getDAY1_lat(), "LAT");
                    //cellValue += FormatCellValue(timeSheet.getDay1_INTIME(),timeSheet.getDAY1_long(), "LONG");

                    colNo +=1;

                } else if (col == 6) {
                    //cellValue = timeSheet.getDay18_INTIME();

                    colNo +=1;

                } else if (col == 26+4) {

                    cellValue =  FormatCellValue(timeSheet.getDAY26().getINTIME(),timeSheet.getDAY26().getINTIME(), "IN");
                    cellValue += FormatCellValue(timeSheet.getDAY26().getOUTTIME(),timeSheet.getDAY26().getOUTTIME(), "OUT");
                    cellValue += FormatCellValue(timeSheet.getDAY26().getOUTTIME(),timeSheet.getDAY26().getHrs(), "HRS");

                    //cellValue += "\nLsttitude: " + timeSheet.getDAY26_lat();
                    //cellValue += "\nLongitude: " + timeSheet.getDAY26_long();

                    colNo +=1;

                } else
                    cellValue = "";
                //cellValue = String.valueOf(val);
                c = row.createCell(col);
                c.setCellValue(cellValue);
                c.setCellStyle(cellStyle);
                colNo++;
            }
            sheet1.setColumnWidth(i, (15 * 500));
            rw++;
        }

        //path = Environment.getExternalStorageDirectory()+"/Sandeep/";
        path = "/storage/emulated/0/Android/data/com.example.eklass/Files/";
        //File file = new File(Environment.getRootDirectory()
        //+ "/Sandeep/" + File.separator + fileName);

        //File mediaStorageDir = new File(
        //      Environment.getExternalStorageDirectory().getAbsolutePath()+"/Sandeep/");
        //File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, fileName);
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
            success = true;
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }
        return file.getAbsolutePath();
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    //////////////////////////////////////////////////////////////////////////
    /*public String syncCall(){

        String URL = "http://192.168.1.35:8092/rest";
        String response = new String();

        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, new JSONObject(), future, future);
        requestQueue.add(request);

        try {
            response = future.get().toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
            }

         Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {

                                        String response = syncCall();

                                    }
                                });
                                thread.start();





    */
    /////////////////////////////////////////////////////////////////////////


    public void GetStaffTimeSheet(final Context ctx, final Integer mnth, final Integer yr) throws ExecutionException, InterruptedException {
        final List<TimeSheet> timeSheetList = new ArrayList<>();
        User usr = SharedPrefManager.getInstance(ctx.getApplicationContext()).getUser();

        final String staffId = usr.getStaffId();
        final String CompanyId = usr.getCompanyId();
        final String DesignationId = usr.getDesignationId();

           /* final ProgressDialog progressDialog = new ProgressDialog(ctx);
            progressDialog.setMessage("loading data...");
            progressDialog.show();
*/
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progressDialog.dismiss();

                // {'a':[{'StudentMasterID':'50215','StudentName':'ARJUN','dey':'7','mnth':'3'}]}
                Log.w("Sandeep44444", response);


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("a");

                    TimeSheet timesheet_fromDB;
                    final DEY dey1;
                    final DEY dey2;
                    //http://103.233.24.31:8080/getimage?fileName=bpslogo.jpg
                    //http://103.233.24.31:8080/getimage?fileName=bpslogo.jpg&pIsLogo=1
                    String imageURL = "";
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = array.getJSONObject(i);

                        timesheet_fromDB = new TimeSheet(
                                o.getString("StaffID")
                                , o.getString("StaffName")
                                , o.getString("DName")
                                , o.getString("HNo"),

                                new DEY(o.getString("DAY1_INTIME")
                                       , o.getString("DAY1_OUTTIME")
                                       , o.getString("DAY1_hrs")
                                       , o.getString("DAY1_INTIME_latitude")
                                       , o.getString("DAY1_OUTTIME_latitude")
                                       , o.getString("DAY1_INTIME_longitude")
                                       , o.getString("DAY1_OUTTIME_longitude")
                                       , o.getString("DAY1_INTIME_location")
                                       , o.getString("DAY1_OUTTIME_location")

                               ),

                               new DEY(o.getString("DAY26_INTIME")
                                , o.getString("DAY26_OUTTIME")
                                , o.getString("DAY26_hrs")
                                , o.getString("DAY26_INTIME_latitude")
                                , o.getString("DAY26_OUTTIME_latitude")
                                , o.getString("DAY26_INTIME_longitude")
                                , o.getString("DAY26_OUTTIME_longitude")
                                , o.getString("DAY26_INTIME_location")
                                , o.getString("DAY26_OUTTIME_location")

                        )

                        /*
                                , o.getString("DAY2_INTIME")
                                , o.getString("DAY2_OUTTIME")
                                , o.getString("DAY2_hrs")
                                , o.getString("DAY2_INTIME_latitude")
                                , o.getString("DAY2_OUTTIME_latitude")
                                , o.getString("DAY2_INTIME_longitude")
                                , o.getString("DAY2_OUTTIME_longitude")
                                , o.getString("DAY2_INTIME_location")
                                , o.getString("DAY2_OUTTIME_location")

                                , o.getString("DAY3_INTIME")
                                , o.getString("DAY3_OUTTIME")
                                , o.getString("DAY3_hrs")
                                , o.getString("DAY3_INTIME_latitude")
                                , o.getString("DAY3_OUTTIME_latitude")
                                , o.getString("DAY3_INTIME_longitude")
                                , o.getString("DAY3_OUTTIME_longitude")
                                , o.getString("DAY3_INTIME_location")
                                , o.getString("DAY3_OUTTIME_location")


                                , o.getString("DAY4_INTIME")
                                , o.getString("DAY4_OUTTIME")
                                , o.getString("DAY4_hrs")
                                , o.getString("DAY4_INTIME_latitude")
                                , o.getString("DAY4_OUTTIME_latitude")
                                , o.getString("DAY4_INTIME_longitude")
                                , o.getString("DAY4_OUTTIME_longitude")
                                , o.getString("DAY4_INTIME_location")
                                , o.getString("DAY4_OUTTIME_location")

                             , o.getString("DAY5_INTIME")
                                , o.getString("DAY5_OUTTIME")
                                , o.getString("DAY5_hrs")
                                , o.getString("DAY5_INTIME_latitude")
                                , o.getString("DAY5_OUTTIME_latitude")
                                , o.getString("DAY5_INTIME_longitude")
                                , o.getString("DAY5_OUTTIME_longitude")
                                , o.getString("DAY5_INTIME_location")
                                , o.getString("DAY5_OUTTIME_location")

                                , o.getString("DAY6_INTIME")
                                , o.getString("DAY6_OUTTIME")
                                , o.getString("DAY6_hrs")
                                , o.getString("DAY6_INTIME_latitude")
                                , o.getString("DAY6_OUTTIME_latitude")
                                , o.getString("DAY6_INTIME_longitude")
                                , o.getString("DAY6_OUTTIME_longitude")
                                , o.getString("DAY6_INTIME_location")
                                , o.getString("DAY6_OUTTIME_location")

                                , o.getString("DAY7_INTIME")
                                , o.getString("DAY7_OUTTIME")
                                , o.getString("DAY7_hrs")
                                , o.getString("DAY7_INTIME_latitude")
                                , o.getString("DAY7_OUTTIME_latitude")
                                , o.getString("DAY7_INTIME_longitude")
                                , o.getString("DAY7_OUTTIME_longitude")
                                , o.getString("DAY7_INTIME_location")
                                , o.getString("DAY7_OUTTIME_location")

                                , o.getString("DAY8_INTIME")
                                , o.getString("DAY8_OUTTIME")
                                , o.getString("DAY8_hrs")
                                , o.getString("DAY8_INTIME_latitude")
                                , o.getString("DAY8_OUTTIME_latitude")
                                , o.getString("DAY8_INTIME_longitude")
                                , o.getString("DAY8_OUTTIME_longitude")
                                , o.getString("DAY8_INTIME_location")
                                , o.getString("DAY8_OUTTIME_location")

                                , o.getString("DAY9_INTIME")
                                , o.getString("DAY9_OUTTIME")
                                , o.getString("DAY9_hrs")
                                , o.getString("DAY9_INTIME_latitude")
                                , o.getString("DAY9_OUTTIME_latitude")
                                , o.getString("DAY9_INTIME_longitude")
                                , o.getString("DAY9_OUTTIME_longitude")
                                , o.getString("DAY9_INTIME_location")
                                , o.getString("DAY9_OUTTIME_location")

                                , o.getString("DAY10_INTIME")
                                , o.getString("DAY10_OUTTIME")
                                , o.getString("DAY10_hrs")
                                , o.getString("DAY10_INTIME_latitude")
                                , o.getString("DAY10_OUTTIME_latitude")
                                , o.getString("DAY10_INTIME_longitude")
                                , o.getString("DAY10_OUTTIME_longitude")
                                , o.getString("DAY10_INTIME_location")
                                , o.getString("DAY10_OUTTIME_location")

                                , o.getString("DAY11_INTIME")
                                , o.getString("DAY11_OUTTIME")
                                , o.getString("DAY11_hrs")
                                , o.getString("DAY11_INTIME_latitude")
                                , o.getString("DAY11_OUTTIME_latitude")
                                , o.getString("DAY11_INTIME_longitude")
                                , o.getString("DAY11_OUTTIME_longitude")
                                , o.getString("DAY11_INTIME_location")
                                , o.getString("DAY11_OUTTIME_location")

                                , o.getString("DAY12_INTIME")
                                , o.getString("DAY12_OUTTIME")
                                , o.getString("DAY12_hrs")
                                , o.getString("DAY12_INTIME_latitude")
                                , o.getString("DAY12_OUTTIME_latitude")
                                , o.getString("DAY12_INTIME_longitude")
                                , o.getString("DAY12_OUTTIME_longitude")
                                , o.getString("DAY12_INTIME_location")
                                , o.getString("DAY12_OUTTIME_location")

                                , o.getString("DAY13_INTIME")
                                , o.getString("DAY13_OUTTIME")
                                , o.getString("DAY13_hrs")
                                , o.getString("DAY13_INTIME_latitude")
                                , o.getString("DAY13_OUTTIME_latitude")
                                , o.getString("DAY13_INTIME_longitude")
                                , o.getString("DAY13_OUTTIME_longitude")
                                , o.getString("DAY13_INTIME_location")
                                , o.getString("DAY13_OUTTIME_location")

                                , o.getString("DAY14_INTIME")
                                , o.getString("DAY14_OUTTIME")
                                , o.getString("DAY14_hrs")
                                , o.getString("DAY14_INTIME_latitude")
                                , o.getString("DAY14_OUTTIME_latitude")
                                , o.getString("DAY14_INTIME_longitude")
                                , o.getString("DAY14_OUTTIME_longitude")
                                , o.getString("DAY14_INTIME_location")
                                , o.getString("DAY14_OUTTIME_location")

                                , o.getString("DAY15_INTIME")
                                , o.getString("DAY15_OUTTIME")
                                , o.getString("DAY15_hrs")
                                , o.getString("DAY15_INTIME_latitude")
                                , o.getString("DAY15_OUTTIME_latitude")
                                , o.getString("DAY15_INTIME_longitude")
                                , o.getString("DAY15_OUTTIME_longitude")
                                , o.getString("DAY15_INTIME_location")
                                , o.getString("DAY15_OUTTIME_location")

                                , o.getString("DAY16_INTIME")
                                , o.getString("DAY16_OUTTIME")
                                , o.getString("DAY16_hrs")
                                , o.getString("DAY16_INTIME_latitude")
                                , o.getString("DAY16_OUTTIME_latitude")
                                , o.getString("DAY16_INTIME_longitude")
                                , o.getString("DAY16_OUTTIME_longitude")
                                , o.getString("DAY16_INTIME_location")
                                , o.getString("DAY16_OUTTIME_location")

                                , o.getString("DAY17_INTIME")
                                , o.getString("DAY17_OUTTIME")
                                , o.getString("DAY17_hrs")
                                , o.getString("DAY17_INTIME_latitude")
                                , o.getString("DAY17_OUTTIME_latitude")
                                , o.getString("DAY17_INTIME_longitude")
                                , o.getString("DAY17_OUTTIME_longitude")
                                , o.getString("DAY17_INTIME_location")
                                , o.getString("DAY17_OUTTIME_location")

                                , o.getString("DAY18_INTIME")
                                , o.getString("DAY18_OUTTIME")
                                , o.getString("DAY18_hrs")
                                , o.getString("DAY18_INTIME_latitude")
                                , o.getString("DAY18_OUTTIME_latitude")
                                , o.getString("DAY18_INTIME_longitude")
                                , o.getString("DAY18_OUTTIME_longitude")
                                , o.getString("DAY18_INTIME_location")
                                , o.getString("DAY18_OUTTIME_location")

                                , o.getString("DAY19_INTIME")
                                , o.getString("DAY19_OUTTIME")
                                , o.getString("DAY19_hrs")
                                , o.getString("DAY19_INTIME_latitude")
                                , o.getString("DAY19_OUTTIME_latitude")
                                , o.getString("DAY19_INTIME_longitude")
                                , o.getString("DAY19_OUTTIME_longitude")
                                , o.getString("DAY19_INTIME_location")
                                , o.getString("DAY19_OUTTIME_location")

                                , o.getString("DAY20_INTIME")
                                , o.getString("DAY20_OUTTIME")
                                , o.getString("DAY20_hrs")
                                , o.getString("DAY20_INTIME_latitude")
                                , o.getString("DAY20_OUTTIME_latitude")
                                , o.getString("DAY20_INTIME_longitude")
                                , o.getString("DAY20_OUTTIME_longitude")
                                , o.getString("DAY20_INTIME_location")
                                , o.getString("DAY20_OUTTIME_location")

                                , o.getString("DAY21_INTIME")
                                , o.getString("DAY21_OUTTIME")
                                , o.getString("DAY21_hrs")
                                , o.getString("DAY21_INTIME_latitude")
                                , o.getString("DAY21_OUTTIME_latitude")
                                , o.getString("DAY21_INTIME_longitude")
                                , o.getString("DAY21_OUTTIME_longitude")
                                , o.getString("DAY21_INTIME_location")
                                , o.getString("DAY21_OUTTIME_location")

                                , o.getString("DAY22_INTIME")
                                , o.getString("DAY22_OUTTIME")
                                , o.getString("DAY22_hrs")
                                , o.getString("DAY22_INTIME_latitude")
                                , o.getString("DAY22_OUTTIME_latitude")
                                , o.getString("DAY22_INTIME_longitude")
                                , o.getString("DAY22_OUTTIME_longitude")
                                , o.getString("DAY22_INTIME_location")
                                , o.getString("DAY22_OUTTIME_location")


                                , o.getString("DAY23_INTIME")
                                , o.getString("DAY23_OUTTIME")
                                , o.getString("DAY23_hrs")
                                , o.getString("DAY23_INTIME_latitude")
                                , o.getString("DAY23_OUTTIME_latitude")
                                , o.getString("DAY23_INTIME_longitude")
                                , o.getString("DAY23_OUTTIME_longitude")
                                , o.getString("DAY23_INTIME_location")
                                , o.getString("DAY23_OUTTIME_location")

                                , o.getString("DAY24_INTIME")
                                , o.getString("DAY24_OUTTIME")
                                , o.getString("DAY24_hrs")
                                , o.getString("DAY24_INTIME_latitude")
                                , o.getString("DAY24_OUTTIME_latitude")
                                , o.getString("DAY24_INTIME_longitude")
                                , o.getString("DAY24_OUTTIME_longitude")
                                , o.getString("DAY24_INTIME_location")
                                , o.getString("DAY24_OUTTIME_location")

                                , o.getString("DAY25_INTIME")
                                , o.getString("DAY25_OUTTIME")
                                , o.getString("DAY25_hrs")
                                , o.getString("DAY25_INTIME_latitude")
                                , o.getString("DAY25_OUTTIME_latitude")
                                , o.getString("DAY25_INTIME_longitude")
                                , o.getString("DAY25_OUTTIME_longitude")
                                , o.getString("DAY25_INTIME_location")
                                , o.getString("DAY25_OUTTIME_location")


                                , o.getString("DAY26_INTIME")
                                , o.getString("DAY26_OUTTIME")
                                , o.getString("DAY26_hrs")
                                , o.getString("DAY26_INTIME_latitude")
                                , o.getString("DAY26_OUTTIME_latitude")
                                , o.getString("DAY26_INTIME_longitude")
                                , o.getString("DAY26_OUTTIME_longitude")
                                , o.getString("DAY26_INTIME_location")
                                , o.getString("DAY26_OUTTIME_location")

                                , o.getString("DAY27_INTIME")
                                , o.getString("DAY27_OUTTIME")
                                , o.getString("DAY27_hrs")
                                , o.getString("DAY27_INTIME_latitude")
                                , o.getString("DAY27_OUTTIME_latitude")
                                , o.getString("DAY27_INTIME_longitude")
                                , o.getString("DAY27_OUTTIME_longitude")
                                , o.getString("DAY27_INTIME_location")
                                , o.getString("DAY27_OUTTIME_location")

                                , o.getString("DAY28_INTIME")
                                , o.getString("DAY28_OUTTIME")
                                , o.getString("DAY28_hrs")
                                , o.getString("DAY28_INTIME_latitude")
                                , o.getString("DAY28_OUTTIME_latitude")
                                , o.getString("DAY28_INTIME_longitude")
                                , o.getString("DAY28_OUTTIME_longitude")
                                , o.getString("DAY28_INTIME_location")
                                , o.getString("DAY28_OUTTIME_location")

                                , o.getString("DAY29_INTIME")
                                , o.getString("DAY29_OUTTIME")
                                , o.getString("DAY29_hrs")
                                , o.getString("DAY29_INTIME_latitude")
                                , o.getString("DAY29_OUTTIME_latitude")
                                , o.getString("DAY29_INTIME_longitude")
                                , o.getString("DAY29_OUTTIME_longitude")
                                , o.getString("DAY29_INTIME_location")
                                , o.getString("DAY29_OUTTIME_location")

                                , o.getString("DAY30_INTIME")
                                , o.getString("DAY30_OUTTIME")
                                , o.getString("DAY30_hrs")
                                , o.getString("DAY30_INTIME_latitude")
                                , o.getString("DAY30_OUTTIME_latitude")
                                , o.getString("DAY30_INTIME_longitude")
                                , o.getString("DAY30_OUTTIME_longitude")
                                , o.getString("DAY30_INTIME_location")
                                , o.getString("DAY30_OUTTIME_location")

                                , o.getString("DAY31_INTIME")
                                , o.getString("DAY31_OUTTIME")
                                , o.getString("DAY31_hrs")
                                , o.getString("DAY31_INTIME_latitude")
                                , o.getString("DAY31_OUTTIME_latitude")
                                , o.getString("DAY31_INTIME_longitude")
                                , o.getString("DAY31_OUTTIME_longitude")
                                , o.getString("DAY31_INTIME_location")
                                , o.getString("DAY31_OUTTIME_location")
*/

                        );
                        timeSheetList.add(timesheet_fromDB);

                        // Create timesheet.xls file
                        GenerateTimeSheet(ctx, "MonthSheet_"+mnth + yr +".xls"
                                , mnth, yr, timeSheetList);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx.getApplicationContext(), error.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        };

        HashMap<String, String> params = new HashMap<>();
        params.put("pCompanyId", CompanyId);
        params.put("pMonthNo", mnth.toString());
        params.put("pYearNo", yr.toString());

        RequestHandler rh = new RequestHandler();
        String paramsStr = rh.getPostDataString(params);
        String theURL = URLs.GET_TIMESHEET_URL + "?" + paramsStr;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, theURL
                , responseListener, errorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

    }


    public String FormatCellValue(String intime, String cellValue, String InOutHrsLat) {
        String formattedValue = "";
        // if no intime
        if (intime == "00:00:00")
        {
            return "Absent";
        } else {
            if (InOutHrsLat == "IN") {
                formattedValue =  " IN Time: " + cellValue;
            }
            else if(InOutHrsLat == "OUT")
            {
                if (cellValue == "00:00:00")
                {
                    return "-";
                }
                else
                {
                    formattedValue =  "\nOUT Time: " + cellValue;
                }
            }
            else if(InOutHrsLat == "HRS" )
            {
                if (cellValue == "-1")
                {
                    return "-";
                }
                else
                {
                    formattedValue =  "\nHours IN: "+ cellValue;
                }
            }

            else if(InOutHrsLat == "LAT" )
            {
                if (cellValue == "-1")
                {
                    return "-";
                }
                else
                {
                    formattedValue =  "\nLatitude: "+ cellValue;
                }
            }

            else if(InOutHrsLat == "LONG" )
            {
                if (cellValue == "-1")
                {
                    return "-";
                }
                else
                {
                    formattedValue =  "\nLongitude: "+ cellValue;
                }
            }
            return formattedValue;
        }
    }

}
