package com.example.eklass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DutyAdapter extends RecyclerView.Adapter<DutyAdapter.DutyViewHolder>
{

    // this context we will use to inflate the layout
    private Context mCtx;

    private List<Duty> dutyList;


    // constructor
    public DutyAdapter(Context mCtx, List<Duty> duties)
    {
        this.mCtx = mCtx;
        this.dutyList = duties;
    }


    @NonNull
    @Override
    public DutyAdapter.DutyViewHolder onCreateViewHolder(@NonNull ViewGroup parent
            , int viewType)
    {

        // inflating and returning our View Holder
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.layout_dashboard, null);
        return new DutyAdapter.DutyViewHolder(view);
    }

    String feature_name ;

    @Override
    public void onBindViewHolder(@NonNull DutyAdapter.DutyViewHolder holder
            , final int position)
    {
        // getting the feature of the specified  position
        Duty duty = dutyList.get(position);

        // binding the data with the viewholder views
        holder.tv_FeatureName.setText( " Date:  " + duty.getDutyDateTime()+ " Name: " + duty.getGuardName());

        holder.tv_FeatureName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),  featureList.get(position).getFeatureName() + " Clicked ", Toast.LENGTH_LONG).show();

                // finish();
                //mCtx.startActivity(new Intent(mCtx, SchoolAttendanceActivity.class));


                // show  duty Latitude & Longitude on map


            }
        });


    }

    @Override
    public int getItemCount()
    {
        return dutyList.size();
    }



    public class DutyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_FeatureName;

        public DutyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_FeatureName = itemView.findViewById(R.id.tvFeatureName_layout_dashboard);
        }
    }


}
