package com.example.eklass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        View view = layoutInflater.inflate(R.layout.activity_dashboard, null);

        return new DashBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardAdapter.DashBoardViewHolder holder
            , int position)
    {
        // getting the feature of the specified  position
        Feature feature = featureList.get(position);

        // binding the data with the viewholder views
        holder.tv_FeatureName.setText(feature.getFeatureName());



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
}
