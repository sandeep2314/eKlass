package com.example.eklass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import java.security.PublicKey;
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


    public String[] ConvertListToStringArray(List<String> theList)
    {
        String[] items = new String[theList.size()];
        for(int i=0; i < theList.size(); i++)
            items[i] = theList.get(i).toString();
        return items;
    }


    public void SetHeadings(Context ctx, TextView pageHeading
            , String pageName, int themeNo)
    {
        User user = SharedPrefManager.getInstance(ctx).getUser();

        String heading = "Welcome Back: "+user.getStaffName()+"("+user.getCompanyName()+")";
        heading += "\n\n" +pageName;

        //mTextView.setTextColor(ContextCompat.getColor(context, R.color.<name_of_color>));
        // mTextView.setTextColor(Color.parseColor("#bdbdbd"))
        // black theme = 1
        // grey theme = 2
        if(themeNo == BLACK_THEME)
        {
            pageHeading.setTextColor(ContextCompat.getColor(ctx, R.color.colorBlueGreen));
        }
        else
        {
            // black font color
            //pageHeading.setTextColor(Color.parseColor("#000000"));
            pageHeading.setTextColor(ContextCompat.getColor(ctx, R.color.colorDarkGrey));
        }

        pageHeading.setText(heading);

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

    public void DeleteRecord(final Context ctx, String ids, String theURL)
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




}
