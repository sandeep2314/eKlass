package com.example.eklass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DutyAdapter extends RecyclerView.Adapter<DutyAdapter.DutyViewHolder>
{

    // this context we will use to inflate the layout
    private Context mCtx;
    private List<Duty> dutyList;

    Util util = new Util();

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

        //holder.tv_LocationName.setText(duty.getLocationName());

       /* holder.tv_LocationName.setText("Day IN: "
                + util.GetDayIN(duty, dutyList)
                + "\n Day OUT: "
                + util.GetDayPosting(duty, dutyList, Util.ATTENDANCE_DAY_OUT)
                + "\n Day BETWEEN: "
                + util.GetDayPosting(duty, dutyList, Util.ATTENDANCE_BETWEEN)
        );*/

        holder.btnUpdateLocation.setVisibility(View.INVISIBLE);
        holder.btnUpdatePassword.setVisibility(View.INVISIBLE);
        holder.btnShowDuty.setVisibility(View.INVISIBLE);

        util.SetCardTheme(mCtx, holder.card, BaseActivity.themeNo);
        util.SetTVTheme(mCtx, holder.tv_FeatureName, BaseActivity.themeNo, true);
        util.SetTVTheme(mCtx, holder.tv_guard, BaseActivity.themeNo, false);
        util.SetTVTheme(mCtx, holder.tv_latitude, BaseActivity.themeNo, false);
        util.SetTVTheme(mCtx, holder.tv_longitude, BaseActivity.themeNo, false);
        util.SetTVTheme(mCtx, holder.tv_LocationName, BaseActivity.themeNo, false);
        util.SetTVTheme(mCtx, holder.tv_posted, BaseActivity.themeNo, false);

        holder.tv_DayIN.setTextColor(ContextCompat.getColor(mCtx, R.color.colorGreen));
        holder.tv_DAYOUT.setTextColor(ContextCompat.getColor(mCtx, R.color.colorRed));
        holder.tv_DAYBETWEEN.setTextColor(ContextCompat.getColor(mCtx, R.color.colorWhite));


        String dayINTime = duty.getInTime();
        String dayOUTTime =  duty.getOutTime();

        // do not show OUT time when staff is IN
        // and also when Attendance Type is not Attendance Out
        if(dayOUTTime == null  || dayOUTTime == "null"
            || duty.getPostType() != Util.ATTENDANCE_DAY_OUT)
            dayOUTTime = "";

        holder.tv_FeatureName.setText(duty.getDutyDateTime());
        holder.tv_guard.setText("Posted After: "
                + util.GetLastPostedDateTime(duty.getDutyDateTime(), dutyList));


        holder.tv_DayIN.setText("Day IN: " + dayINTime);
        holder.tv_DAYOUT.setText("Day OUT: " + dayOUTTime);
        holder.tv_DAYBETWEEN.setText("Hours IN: " + duty.getWorkingHrs());
        holder.tv_LocationName.setText("Location: " + duty.getLocationName());


        holder.tv_latitude.setText("Latitude: " + duty.getLatitude());
        holder.tv_longitude.setText("Longitude: " + duty.getLongitude());

        String postMethod = "";
        if(duty.getIsScan() == 1)
            postMethod = "QR Scan Post";
                else
            postMethod = "Manual Post";

        String attendanceType = "";
         if(duty.getPostType() == Util.ATTENDANCE_DAY_IN)
         {
             attendanceType = "DAY_IN";
             holder.tv_AttendanceType.setTextColor(ContextCompat.getColor(mCtx
                     , R.color.colorBlueGreen));
         }
         else if(duty.getPostType() == Util.ATTENDANCE_DAY_OUT)
         {
             attendanceType = "DAY_OUT";
             holder.tv_AttendanceType.setTextColor(ContextCompat.getColor(mCtx
                     , R.color.colorRed));
         }
         else if(duty.getPostType() == Util.ATTENDANCE_BETWEEN)
         {
             attendanceType = "BETWEEN";
             holder.tv_AttendanceType.setTextColor(ContextCompat.getColor(mCtx
                     , R.color.colorWhite));
         }

        holder.tv_posted.setText("Post Method: " + postMethod);

        holder.tv_AttendanceType.setText("Attendance Type: "+ attendanceType );

        int marginLeft  = 50;
        int marginBottom  = 120;



        RelativeLayout.LayoutParams parameter =  (RelativeLayout.LayoutParams) holder.tv_FeatureName.getLayoutParams();
        parameter.setMargins(marginLeft, parameter.topMargin, parameter.rightMargin, parameter.bottomMargin);
        parameter.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        holder.tv_FeatureName.setLayoutParams(parameter);

        parameter =  (RelativeLayout.LayoutParams) holder.tv_guard.getLayoutParams();
        parameter.setMargins(marginLeft, marginBottom, parameter.rightMargin, parameter.bottomMargin);
        holder.tv_guard.setLayoutParams(parameter);

        parameter =  (RelativeLayout.LayoutParams) holder.tv_DayIN.getLayoutParams();
        parameter.setMargins(marginLeft, parameter.topMargin, parameter.rightMargin, parameter.bottomMargin);
        holder.tv_DayIN.setLayoutParams(parameter);

        parameter =  (RelativeLayout.LayoutParams) holder.tv_DAYOUT.getLayoutParams();
        parameter.setMargins(marginLeft, parameter.topMargin, parameter.rightMargin, parameter.bottomMargin);
        holder.tv_DAYOUT.setLayoutParams(parameter);

        parameter =  (RelativeLayout.LayoutParams) holder.tv_DAYBETWEEN.getLayoutParams();
        parameter.setMargins(marginLeft, parameter.topMargin, parameter.rightMargin, parameter.bottomMargin);
        holder.tv_DAYBETWEEN.setLayoutParams(parameter);

        parameter =  (RelativeLayout.LayoutParams) holder.tv_LocationName.getLayoutParams();
        parameter.setMargins(marginLeft, parameter.topMargin, parameter.rightMargin, parameter.bottomMargin);
        holder.tv_LocationName.setLayoutParams(parameter);


        parameter =  (RelativeLayout.LayoutParams) holder.tv_latitude.getLayoutParams();
        parameter.setMargins(marginLeft, parameter.topMargin, parameter.rightMargin, parameter.bottomMargin);
        holder.tv_latitude.setLayoutParams(parameter);

        parameter =  (RelativeLayout.LayoutParams) holder.tv_longitude.getLayoutParams();
        parameter.setMargins(marginLeft, parameter.topMargin, parameter.rightMargin, parameter.bottomMargin);
        holder.tv_longitude.setLayoutParams(parameter);

        parameter =  (RelativeLayout.LayoutParams) holder.tv_posted.getLayoutParams();
        parameter.setMargins(marginLeft, parameter.topMargin, parameter.rightMargin, parameter.bottomMargin);
        holder.tv_posted.setLayoutParams(parameter);

        parameter =  (RelativeLayout.LayoutParams) holder.tv_AttendanceType.getLayoutParams();
        parameter.setMargins(marginLeft, parameter.topMargin, parameter.rightMargin, parameter.bottomMargin);
        holder.tv_AttendanceType.setLayoutParams(parameter);

        holder.ckbDelete.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount()
    {
        return dutyList.size();
    }



    public class DutyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_FeatureName
                ,  tv_guard
                 , tv_latitude, tv_longitude, tv_posted, tv_AttendanceType
                , tv_DayIN, tv_DAYOUT, tv_DAYBETWEEN
                , tv_LocationName;
        CheckBox ckbDelete;
        CardView card;
        Button btnUpdateLocation, btnUpdatePassword, btnShowDuty;

        public DutyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_FeatureName = itemView.findViewById(R.id.tvFeatureName_layout_dashboard);
            tv_guard =  itemView.findViewById(R.id.tvGuardID_layout_dashboard);
            tv_LocationName = itemView.findViewById(R.id.tvLocationName_layout_dashboard);
            tv_DayIN = itemView.findViewById(R.id.tvDayIN_layout_dashboard);
            tv_DAYOUT = itemView.findViewById(R.id.tvDayOUT_layout_dashboard);
            tv_DAYBETWEEN = itemView.findViewById(R.id.tvDayBETWEEN_layout_dashboard);

            tv_latitude = itemView.findViewById(R.id.tvLatitude_layout_dashboard);
            tv_longitude = itemView.findViewById(R.id.tvLongitude_layout_dashboard);
            tv_posted = itemView.findViewById(R.id.tvPostedMethod_layout_dashboard);
            tv_AttendanceType = itemView.findViewById(R.id.tvAttendanceType_layout_dashboard);
            ckbDelete =  itemView.findViewById(R.id.ckb_layout_Dashboard);
            card= itemView.findViewById(R.id.cardViewFeature);

            btnUpdateLocation =  itemView.findViewById(R.id.btnUpdateLocation_layout_dashboard);
            btnUpdatePassword =  itemView.findViewById(R.id.btnUpdatePassword_layout_dashboard);
            btnShowDuty =  itemView.findViewById(R.id.btnShowDuty_layout_dashboard);

        }
    }


}
