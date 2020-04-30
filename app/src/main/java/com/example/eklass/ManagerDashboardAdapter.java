package com.example.eklass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ManagerDashboardAdapter
        extends RecyclerView.Adapter<ManagerDashboardAdapter.DashBoardViewHolder>

    {

        // this context we will use to inflate the layout
        private Context mCtx;
        //private List<Feature> featureList;

        private List<Staff> workerList;


        // constructor
    public ManagerDashboardAdapter(Context mCtx, List<Staff> workers)
        {
            this.mCtx = mCtx;
            this.workerList = workers;
        }


        @NonNull
        @Override
        public ManagerDashboardAdapter.DashBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent
            , int viewType)
        {

            // inflating and returning our View Holder
            LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
            View view = layoutInflater.inflate(R.layout.layout_dashboard, null);

            return new ManagerDashboardAdapter.DashBoardViewHolder(view);
        }

        String feature_name ;

        @Override
        public void onBindViewHolder(@NonNull ManagerDashboardAdapter.DashBoardViewHolder holder
            , final int position)
        {
            // getting the feature of the specified  position
            Staff worker = workerList.get(position);

            // binding the data with the viewholder views
            holder.tv_FeatureName.setText(worker.getStaffName());

            holder.tv_FeatureName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(v.getContext(),  featureList.get(position).getFeatureName() + " Clicked ", Toast.LENGTH_LONG).show();

                    // finish();
                     mCtx.startActivity(new Intent(mCtx, SchoolAttendanceActivity.class));


                }
            });


        }

        @Override
        public int getItemCount()
        {
            return workerList.size();
        }

        public class DashBoardViewHolder extends RecyclerView.ViewHolder
        {
            TextView tv_FeatureName;

            public DashBoardViewHolder(@NonNull View itemView) {
                super(itemView);
                this.tv_FeatureName = itemView.findViewById(R.id.tvFeatureName_layout_dashboard);
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
