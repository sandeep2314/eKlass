package com.example.eklass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
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
        holder.tv_FeatureName.setText(duty.getDutyDateTime());
        holder.tv_guard.setText(" Last Posted After ");
        holder.tv_LocationName.setText(duty.getLocationName());


        holder.ckbDelete.setVisibility(View.INVISIBLE);
        holder.tv_update.setVisibility(View.INVISIBLE);
        holder.tv_ShowDutySymbol.setVisibility(View.INVISIBLE);

    }

    @Override
    public int getItemCount()
    {
        return dutyList.size();
    }



    public class DutyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_FeatureName, tv_update, tv_guard, tv_ShowDutySymbol, tv_LocationName;
        CheckBox ckbDelete;

        public DutyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_FeatureName = itemView.findViewById(R.id.tvFeatureName_layout_dashboard);
            tv_guard =  itemView.findViewById(R.id.tvGuardID_layout_dashboard);
            tv_update = itemView.findViewById(R.id.tvUpdate_layout_dashboard);
            tv_ShowDutySymbol = itemView.findViewById(R.id.tvShowDuty_layout_dashboard);
            tv_LocationName = itemView.findViewById(R.id.tvLocationName_layout_dashboard);
            ckbDelete =  itemView.findViewById(R.id.ckb_layout_Dashboard);
        }
    }


}
