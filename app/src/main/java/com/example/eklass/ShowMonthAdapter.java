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
import java.util.concurrent.ExecutionException;

public class ShowMonthAdapter  extends RecyclerView.Adapter<ShowMonthAdapter.ShowMonthViewHolder>
{
    private Context mCtx;
    private List<String> monthList;

    public ShowMonthAdapter.ShowMonthViewHolder onCreateViewHolder(@NonNull ViewGroup parent
            , int viewType)
    {

        // inflating and returning our View Holder
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.layout_dashboard, null);

        return new ShowMonthAdapter.ShowMonthViewHolder(view);
    }

    public ShowMonthAdapter(Context mCtx, List<String> monthList) {
        this.mCtx = mCtx;
        this.monthList = monthList;

    }

    @Override
    public void onBindViewHolder(@NonNull ShowMonthViewHolder holder, int position) {

        final String mnth = monthList.get(position);
        holder.tv_FeatureName.setText(mnth);

        holder.tv_FeatureName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // get first 3 names for month and then get year

                String mnthSelected =  mnth.substring(0, 3);
                Integer yr = Integer.parseInt(mnth.substring(6));

                Integer mnthSelectedInt = 10;

                String[] mnths = {"JAN", "FEB", "MAR"
                        , "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
                for(int i=0 ; i< mnths.length; i++)
                {
                   // Log.w("Sandeep9292", "mnthSelected " + mnthSelected + "  " + mnths[i]);
                    if(mnthSelected.trim().equalsIgnoreCase(mnths[i]))
                        mnthSelectedInt=i+1;

                }
                Util util = new Util();

                try {
                    util.GetStaffTimeSheet(mCtx, mnthSelectedInt, yr);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Toast.makeText(mCtx, "Month Selected " + mnthSelectedInt + " "+yr, Toast.LENGTH_SHORT).show();


            }
        });


    }

    @Override
    public int getItemCount() {
        return monthList.size();
    }

    public class ShowMonthViewHolder extends RecyclerView.ViewHolder {
        TextView tv_FeatureName;
        public ShowMonthViewHolder(View view) {
            super(view);
            this.tv_FeatureName = itemView.findViewById(R.id.tvFeatureName_layout_dashboard);
        }
    }


}
