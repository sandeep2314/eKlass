package com.example.eklass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
    }

    @Override
    public int getItemCount() {
        return monthList.size();
    }

    public class ShowMonthViewHolder extends RecyclerView.ViewHolder {
        TextView tv_FeatureName, tv_guardID, tv_locationName, tv_ShowDuty, tvUpdate;
        public ShowMonthViewHolder(View view) {
            super(view);
            this.tv_FeatureName = itemView.findViewById(R.id.tvFeatureName_layout_dashboard);
        }
    }


}
