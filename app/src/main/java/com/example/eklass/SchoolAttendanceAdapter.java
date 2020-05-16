package com.example.eklass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SchoolAttendanceAdapter extends RecyclerView.Adapter<SchoolAttendanceAdapter.SchoolAttendanceHolder> {


    private Context mCtx;
    private List<Attendance> attendanceList;

    public SchoolAttendanceAdapter(Context mCtx, List<Attendance> attendanceList) {
        this.mCtx = mCtx;
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public SchoolAttendanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        //View view = layoutInflater.inflate(R.layout.layout_attendance, null);
        View view = layoutInflater.inflate(R.layout.layout_dashboard, null);

        return new SchoolAttendanceAdapter.SchoolAttendanceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolAttendanceHolder holder, int position) {

        //Feature feature = featureList.get(position);

        Attendance attd = attendanceList.get(position);

        // binding the data with the viewholder views
        //holder.tv_FeatureName.setText(feature.getFeatureName());

        holder.tv_SchoolAttendance.setText(attd.getTheSMS());
        //holder..setText("sandeep");
    }



    public class SchoolAttendanceHolder extends RecyclerView.ViewHolder {

        TextView tv_SchoolAttendance;

        public SchoolAttendanceHolder(@NonNull View itemView) {
            super(itemView);
            //this.tv_SchoolAttendance = itemView.findViewById(R.id.tvSchoolAttd);
            this.tv_SchoolAttendance = itemView.findViewById(R.id.tvFeatureName_layout_dashboard);

        }
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }
}





