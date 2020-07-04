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

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class Util
{

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




    public String[] ConvertListToStringArray(List<String> theList)
    {
        String[] items = new String[theList.size()];
        for(int i=0; i < theList.size(); i++)
            items[i] = theList.get(i).toString();
        return items;
    }

    public void SetImage(final Context ctx, final ImageView imageView, final boolean isLogo)
    {

        Log.w("Sandeep4444", "SetImage");

        User usr = SharedPrefManager.getInstance(ctx).getUser();

        final String staffId = usr.getStaffId();
        final  String CompanyId = usr.getCompanyId();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //{"a": [{"StudentName": "Mahi", "MobileF": "8923579979"}, {"StudentName": "ANURAG VERMA", "MobileF": "9837402809"}
                // {'a':[{'StudentMasterID':'50215','StudentName':'ARJUN','dey':'7','mnth':'3'}]}
                Log.w("Sandeep4444",response);

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

                        String theURL = "";
                        if(isLogo)
                        {
                            imageURL = URLs.GET_IMAGE_URL + o.getString("imageName");
                            imageURL += "&pIsLogo=1";
                        }
                        else
                        {
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

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(ctx,  error.getMessage()
                        , Toast.LENGTH_LONG ).show();
            }
        };

        HashMap<String, String> params = new HashMap<>();
        params.put("pStaffId", staffId);
        params.put("pCompanyId", CompanyId);

        String the_url="";
        if(isLogo) {
            params.put("pIsLogo", "1");
            the_url = URLs.GET_COMPANIES_URL;
        }
        else
        {
            params.put("pIsLogo", "0");
            the_url= URLs.GET_STAFF_URL;
        }

        RequestHandler rh = new RequestHandler();
        String paramsStr = rh.getPostDataString(params);

        String theURL = the_url +"?" + paramsStr;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, theURL
                , responseListener, errorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

    }

    public void SetHeadings(Context ctx, TextView tvPageHeading
            , String pageName
            , ImageView logoImage
            , ImageView profileImage
            , int themeNo)
    {
        User user = SharedPrefManager.getInstance(ctx).getUser();

        String heading = user.getStaffName();
        heading += "\n" + user.getCompanyName();
        heading += "\n" + pageName;



        //mTextView.setTextColor(ContextCompat.getColor(context, R.color.<name_of_color>));
        // mTextView.setTextColor(Color.parseColor("#bdbdbd"))
        // black theme = 1
        // grey theme = 2
        if(themeNo == BLACK_THEME)
        {
            tvPageHeading.setTextColor(ContextCompat.getColor(ctx, android.R.color.white));
        }
        else
        {
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

    public void CheckAll(CheckBox ckb)
    {
         if(ckb.isChecked())
            ckb.setChecked(false);
        else
            ckb.setChecked(true);
    }

    public String GetCheckedIds()
    {
        String selectedIds = "";

        return selectedIds;

    }

    public void DeleteRecord(final Context ctx, String ids, final String theURL)
    {
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
                Log.w("Sandeep444",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("a");

                    String deleted = "no";
                    for(int i=0; i< array.length(); i++)
                    {
                        JSONObject o = array.getJSONObject(i);
                        deleted = o.getString("IsDeleted");
                    }

                    if(deleted.equals("yes"))
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

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(ctx,  error.getMessage()
                        , Toast.LENGTH_LONG ).show();
            }
        };


        User user = SharedPrefManager.getInstance(ctx).getUser();

        HashMap<String, String> params = new HashMap<>();
        //params.put("pStaffId", staffId);

        params.put("pCompanyId", user.getCompanyId());
        params.put("pDelIds", ids);

        RequestHandler rh = new RequestHandler();
        String paramsStr = rh.getPostDataString(params);
        String delURL = theURL +"?" + paramsStr;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, delURL
                , responseListener, errorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);


    }


    public Bitmap generateQrCode(String myCodeText) throws WriterException
    {

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

        Bitmap  bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        return bitmap;
    }

    public String storeImage(Context ctx, Bitmap image, String QRCodeName) {
        File pictureFile = getOutputMediaFile(ctx, QRCodeName);

        String fileName = pictureFile.getName();
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
        return  fileName;
    }

    private  File getOutputMediaFile(Context ctx, String QRCodeName){
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
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = QRCodeName+"_"+ timeStamp +".bmp";
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


    public String GenerateLocationQRCode(final Context ctx)
    {
        String pathName="";

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("a");

                    String qrCodeFileName="";

                    for(int i=0; i< array.length(); i++)
                    {
                        JSONObject o = array.getJSONObject(i);

                        Bitmap bitmap = generateQrCode(o.getString("LocationID"));


                        if(bitmap != null)
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

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        };


        User user = SharedPrefManager.getInstance(ctx).getUser();

        HashMap<String, String> params = new HashMap<>();

        params.put("pCompanyId", user.getCompanyId());
        RequestHandler rh = new RequestHandler();
        String paramsStr = rh.getPostDataString(params);
        String theURL = URLs.GET_LOCATION_URL+"?" + paramsStr;

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

    public String GetString(Object str)
    {
        if(str == null)
            return "";
        return str.toString();
    }

    public int GetSpinnerPosition(String [] idsArray, int param)
    {
        int position=0;

        if(idsArray!=null) {
            for (int i = 0; i < idsArray.length; i++) {
                if (idsArray[i].equals(String.valueOf(param))) {
                    position = i;
                    break;
                }

            }
        }
        return position;
    }

    public String GetPostOfDay(String currentDateTime, List<Duty> dutyList, Boolean isFirst)
    {
        String firstPostedAt = "";
        String pattern = "dd-MM-yyyy";

        Date dt = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try
        {
            dt = sdf.parse(currentDateTime);
          //  dtOrg = sdfOrg.parse(currentDateTime);

        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        Date[] dtArray = ConvertListToDateArray(dutyList, pattern);

        String patternOrg =  "dd-MM-yyyy HH:mm:ss";
        Date[] dtArrayOrg = ConvertListToDateArray(dutyList, patternOrg);

        List<String> postingsOfDay ;
        postingsOfDay = new ArrayList<>();

        // put all postings of one day in one date array
        for(int i=0; i< dtArray.length; i++)
        {

            if(dt != null && dt.equals(dtArray[i]))
            {
                sdf =  new SimpleDateFormat("HH:mm:ss");
                postingsOfDay.add(sdf.format(dtArrayOrg[i]));
                Log.w("sandeep777","dt " + dt + "dtArray[i] " + dtArrayOrg[i]);
            }
        }


        if (!isFirst)
            firstPostedAt = "" + postingsOfDay.get(0);
        else
            firstPostedAt = "" +  postingsOfDay.get(postingsOfDay.size()-1);


        return firstPostedAt;
    }


    public String GetLastPostedDateTime(String currentDateTime, List<Duty> dutyList)
             {
        String lastPosted = "";
        String pattern = "dd-MM-yyyy HH:mm:ss";

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date dt= null;
        Date dtBefore = null;
         try
         {
             dt = sdf.parse(currentDateTime);
         }
         catch (ParseException e)
         {
             e.printStackTrace();
         }

         Date[] dtArray = ConvertListToDateArray(dutyList, pattern);

         // get the datetime before the current date time
          for(int i=0; i< dtArray.length; i++)
          {
                if(dt != null && dt.compareTo(dtArray[i]) == 0 )
                {
                    if(dtArray.length == i+1)
                        dtBefore = dtArray[i];
                    else
                        dtBefore = dtArray[i+1];
                    break;
                }
          }
        lastPosted = GetDuration(dt, dtBefore);

        return  lastPosted;
    }


   public String GetDuration(Date dt, Date dtBefore)
   {
       String lastPosted = "";

       long diff = 1;
       if(dt != null && dtBefore!= null)
           diff = dt.getTime() - dtBefore.getTime() ;

       long diffSeconds = diff / (1000);
       double d = diffSeconds / 60 ;
       long diffMinutes = (long)Math.floor(d);
       diffSeconds = diffSeconds - (diffMinutes * 60);

       d = diffMinutes / 60;
       long diffHours = (long)Math.floor(d);
       diffMinutes = diffMinutes - (diffHours * 60);

       d = diffHours / 24;
       long diffDays = (long)Math.floor(d);
       diffHours = diffHours - (diffDays * 24);

       lastPosted = "d:" + diffDays + " H:" + diffHours + " M:"+ diffMinutes + " S:"+ diffSeconds;

       return  lastPosted;

   }


    public Date[] ConvertListToDateArray(List<Duty> theList, String pattern)
    {
        Date[] items = new Date[theList.size()];
        //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        Date dt = null;
        Duty duty = null;
        for(int i=0; i < theList.size(); i++) {
            duty =  (Duty)theList.get(i);
            try {
                dt = sdf.parse(duty.getDutyDateTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            items[i] = dt;
        }
        return items;
    }

    public void CreateMonthlyReport(String monthYear, String pattern)
    {

        String mnth = "JUN";
        String yr = "20";
        pattern = "dd-MM-yyyy";

        Integer[] dys = new Integer[31];

        String dtStr = "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date dt = null;

        // create valid days of a month
        for(int i=1; i < 32; i++)
        {
            dtStr = i+"/"+mnth+"/"+yr;
            try {
                dt = sdf.parse(dtStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dys[i] = i;
        }

    }





}
