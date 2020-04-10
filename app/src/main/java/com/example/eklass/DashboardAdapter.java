package com.example.eklass;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashBoardViewHolder>
{


    // this context we will use to inflate the layout
    private Context mCtx;

    private List<Feature> featureList;


    // constructor
    public DashboardAdapter(Context mCtx, List<Feature> features)
    {
        this.mCtx = mCtx;
        this.featureList = features;
    }


    @NonNull
    @Override
    public DashboardAdapter.DashBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent
            , int viewType)
    {

        // inflating and returning our View Holder
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.layout_dashboard, null);

        return new DashBoardViewHolder(view);
    }

    String feature_name ;

    @Override
    public void onBindViewHolder(@NonNull DashboardAdapter.DashBoardViewHolder holder
            , final int position)
    {
        // getting the feature of the specified  position
        Feature feature = featureList.get(position);

        // binding the data with the viewholder views
        holder.tv_FeatureName.setText(feature.getFeatureName());

        holder.tv_FeatureName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),  featureList.get(position).getFeatureName() + " Clicked ", Toast.LENGTH_LONG).show();

                if(featureList.get(position).getFeatureName() == "School Attendance")
                {
                   // finish();
                    mCtx.startActivity(new Intent(mCtx, SchoolAttendanceActivity.class));

                }

                if(featureList.get(position).getFeatureName() == "Bus Attendance")
                {
                    mCtx.startActivity(new Intent(mCtx, BusAttendanceActivity.class));
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return featureList.size();
    }

    public class DashBoardViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_FeatureName;

        public DashBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_FeatureName = itemView.findViewById(R.id.tvFeatureName);
        }



    }


    public void OpenSchoolAttendance()
    {
        //Toast.makeText(mCtx,    "School Attendance Clicked ", Toast.LENGTH_LONG).show();

    }


    public void OpenBusAttendance()
    {
        Toast.makeText(mCtx,    "Bus Attendance Clicked ", Toast.LENGTH_LONG).show();
    }
}
