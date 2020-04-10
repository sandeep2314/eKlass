package com.example.eklass;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class BusAttendanceActivity extends AppCompatActivity {

    List<Attendance> attendancesList;
    RecyclerView recyclerView;
    SchoolAttendanceAdapter attendanceAdapter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busattendance);

        attendancesList = new ArrayList<>();
        //attendancesList.add(new Attendance("Your Child has boarded the Bus"));
        //attendancesList = loadData();





        recyclerView = findViewById(R.id.rvSchoolAttendance);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadData2();


        Log.w("sandeep",  "555" + attendancesList.size());

        /*attendancesList = new ArrayList<>();

        attendancesList.add(new Attendance("Your Child has boarded the Bus"));
        attendancesList.add(new Attendance("Your child has deboarded the Bus"));
*/


        //DashboardAdapter dashboardAdapter = new DashboardAdapter(this, featureList);

       /* SchoolAttendanceAdapter schoolAttendanceAdapter =
                new SchoolAttendanceAdapter(this, loadData());
        recyclerView.setAdapter(schoolAttendanceAdapter);*/
    }


    public List<Attendance> loadData() {

        final String userStudentId = "48800";

        final List<Attendance> attendanceList2;

        attendanceList2 = new ArrayList<>();

        class LoadData extends AsyncTask<Void, Void, String>
        {
            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();

                //params.put("studentId", userStudentId);
                params.put("mobileNo", "9219484030");
                params.put("password", "2532");


                String response = null;
                try
                {
                    response = requestHandler.sendPostRequest(URLs.LOGIN_URL ,params);
                } catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }


                return response;
            }


            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);


                Log.w("sandeep ", " 777 "+s);

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s);
                    JSONArray array = jsonObject.getJSONArray("a");


                    boolean isError = true;
                    String userMobileFromDB="-1" ;
                    String StudentId = "";
                    String StudentName = "";
                    String StudentClass = "";

                    Attendance attendance;
                    for(int i=0; i< array.length(); i++)
                    {
                        JSONObject o = array.getJSONObject(i);
                        userMobileFromDB = o.getString("MobileNo");

                        StudentId =  o.getString("StudentMasterID");
                        StudentName =  o.getString("StudentName");
                        StudentClass = o.getString("StudentClass");

                        attendance = new Attendance(o.getString("StudentName"));
                        Log.w("Sandeep", "888" + o.getString("StudentName"));
                        attendanceList2.add(attendance);
                    }

                    Log.w("Sandeep", "666" + attendanceList2.size());
                    attendanceList2.add(new Attendance("Your Child222 has boarded the Bus"));

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }





            }
        }


        LoadData load = new LoadData();
        load.execute();

        return  attendanceList2;
    }



    private void loadData2()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading data...");
        progressDialog.show();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                Log.w("Sandeep444",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("a");

                    Attendance attendance;
                    for(int i=0; i< array.length(); i++)
                    {
                        JSONObject o = array.getJSONObject(i);
                         attendance = new Attendance(
                                o.getString("StudentName")
                        );

                        //attendance = new Attendance("This is sandeep");
                        attendancesList.add(attendance);
                    }

                    //studentAdapter = new StudentAdapter(students, getApplicationContext());

                     attendanceAdapter  =
                            new SchoolAttendanceAdapter (getApplicationContext(), attendancesList);
                    recyclerView.setAdapter(attendanceAdapter);



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

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.LOGIN_URL
                , responseListener, errorListener);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}
