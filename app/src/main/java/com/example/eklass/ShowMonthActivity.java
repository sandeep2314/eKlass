package com.example.eklass;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowMonthActivity extends BaseActivity
{
    public RecyclerView recyclerView;
    public List<String> monthList;
    Util util = new Util();
    public ShowMonthAdapter showMonthAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managers_worker);


        TextView pageHeading  = findViewById(R.id.tvHeader_activity_managers_workers);
        CircleImageView imageViewProfileHeading, imageViewLogoHeading;
        imageViewLogoHeading=findViewById(R.id.imageLogo_activity_managers_worker);
        imageViewProfileHeading=findViewById(R.id.imageProfile_activity_managers_worker);

        util.SetHeadings(getApplicationContext(), pageHeading
                , "Last 12 Months"
                , imageViewLogoHeading
                , imageViewProfileHeading
                , BaseActivity.themeNo);

        recyclerView =  findViewById(R.id.rvManagerWorker);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        monthList = new ArrayList<>();

        loadData2();

    }

    private void loadData2()
    {

        // get last 12 months starting from

        int mnth = Calendar.getInstance().get(Calendar.MONTH);
        int yr = Calendar.getInstance().get(Calendar.YEAR);
        String maxDate = mnth+"-"+yr;
        SimpleDateFormat monthDate = new SimpleDateFormat("MMM-yyyy");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(monthDate.parse(maxDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 1; i <= 12; i++) {
            String month_name1 = monthDate.format(cal.getTime());
            monthList.add(month_name1);
            cal.add(Calendar.MONTH, -1);
        }


     showMonthAdapter = new ShowMonthAdapter(getApplicationContext(), monthList);
     recyclerView.setAdapter(showMonthAdapter);

    }
}
