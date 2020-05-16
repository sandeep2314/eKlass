package com.example.eklass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShowStaffAdapter
        extends RecyclerView.Adapter<ShowStaffAdapter.ShowStaffViewHolder>

    {

        // this context we will use to inflate the layout
        private Context mCtx;
        //private List<Feature> featureList;

        private List<Staff> staffList;


        // constructor
    public ShowStaffAdapter(Context mCtx, List<Staff> staff)
        {
            this.mCtx = mCtx;
            this.staffList = staff;
        }


        @NonNull
        @Override
        public ShowStaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent
            , int viewType)
        {

            // inflating and returning our View Holder
            LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
            View view = layoutInflater.inflate(R.layout.layout_dashboard, null);

            return new ShowStaffViewHolder(view);
        }

        String feature_name ;

        @Override
        public void onBindViewHolder(@NonNull final ShowStaffViewHolder holder
            , final int position)
        {
            // getting the feature of the specified  position
            final Staff staff = staffList.get(position);

            // binding the data with the viewholder views
            holder.tv_FeatureName.setText("    " + staff.getStaffName());
            holder.tv_guardID.setText("    " + staff.getStaffId());


            holder.tv_FeatureName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(v.getContext(), DutyActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("guardID",staff.getStaffId() );
                    v.getContext().startActivity(i);

                }
            });

        }

        @Override
        public int getItemCount()
        {
            return staffList.size();
        }

        public class ShowStaffViewHolder extends RecyclerView.ViewHolder
        {
            TextView tv_FeatureName, tv_guardID, tv_locationName;

            public ShowStaffViewHolder(@NonNull View itemView) {
                super(itemView);
                this.tv_FeatureName = itemView.findViewById(R.id.tvFeatureName_layout_dashboard);
                this.tv_guardID = itemView.findViewById(R.id.tvGuardID_layout_dashboard);
                //this.tv_locationName = itemView.findViewById(R.id.tvLocationName_layout_dashboard);

            }
        }


}
