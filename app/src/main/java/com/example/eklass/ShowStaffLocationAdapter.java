package com.example.eklass;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowStaffLocationAdapter
        extends RecyclerView.Adapter<ShowStaffLocationAdapter.ShowStaffLocationViewHolder>
{

    Util util = new Util();
    private Context mCtx;
    private List<StaffLocation> staffLocationList;
    SparseBooleanArray itemStateArray= new SparseBooleanArray();

    interface OnItemCheckListener
    {
        void onItemCheck(int pos, StaffLocation item);
        void onItemUncheck(int pos, StaffLocation item);

    }

    @NonNull
    private ShowStaffLocationAdapter.OnItemCheckListener onItemCheckListener;

    public ShowStaffLocationAdapter(Context mCtx, List<StaffLocation> staffLocationList
            , @NonNull OnItemCheckListener onItemCheckListener) {
        this.mCtx = mCtx;
        this.staffLocationList = staffLocationList;
        this.onItemCheckListener = onItemCheckListener;
    }

    @NonNull
    @Override
    public ShowStaffLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.layout_dashboard, null);
        return new ShowStaffLocationAdapter.ShowStaffLocationViewHolder(view) ;


    }

    @Override
    public void onBindViewHolder(@NonNull final ShowStaffLocationViewHolder holder, final int position) {

        final StaffLocation staffLocation = staffLocationList.get(position);

        util.SetCardTheme(mCtx, holder.card, BaseActivity.themeNo);
        util.SetTVTheme(mCtx, holder.tv_FeatureName, BaseActivity.themeNo);
        util.SetTVTheme(mCtx, holder.tv_StaffDesignation, BaseActivity.themeNo);
        util.SetTVTheme(mCtx, holder.tv_guardID, BaseActivity.themeNo);
        util.SetTVTheme(mCtx, holder.tv_ManagerDesignation, BaseActivity.themeNo);
        util.SetTVTheme(mCtx, holder.tv_locationName, BaseActivity.themeNo);

        // binding the data with the viewholder views
        holder.tv_locationName.setText("Site: "+staffLocation.getLocationName());
        holder.tv_FeatureName.setText(staffLocation.getWorkerName());
        holder.tv_StaffDesignation.setText(staffLocation.getWorkerDesignation());
        holder.tv_guardID.setText("Reports To: "+staffLocation.getManagerName());
        holder.tv_ManagerDesignation.setText("Job Title: "+staffLocation.getManagerDesignation());
        //holder.tvUpdate.setMovementMethod(LinkMovementMethod.getInstance());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_profile_grey_24dp)
                .error(R.drawable.ic_profile_grey_24dp);

        Glide.with(mCtx).load(staffLocation.getWorkerImage()).apply(options).into(holder.imageWorker);


        holder.btnUpdateLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mCtx, "Update Clicked", Toast.LENGTH_LONG).show();

                Intent i = new Intent(v.getContext(), AddStaffLocationActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //i.putExtra("SLId", staffLocation.getSLId());

                i.putExtra("SLId", staffLocation.getSLId());
                i.putExtra("isUpdate","yes" );
                i.putExtra("locationId",staffLocation.getLocationId() );
                i.putExtra("workerId",staffLocation.getWorkerId() );
                i.putExtra("managerId",staffLocation.getManagerId());
                v.getContext().startActivity(i);


            }
        });




        holder.btnShowDuty.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), DutyActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("WorkerID", staffLocation.getWorkerId() );
                i.putExtra("WorkerName", staffLocation.getWorkerName() );
                v.getContext().startActivity(i);

            }
        });


        if (!itemStateArray.get(position, false))
        {
            holder.ckb.setChecked(false);
        }
        else
        {
            holder.ckb.setChecked(true);
        }

        holder.ckb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //holder.ckb.setChecked(! holder.ckb.isChecked());

                int adapterPosition = position;
                if (!itemStateArray.get(adapterPosition, false))
                {
                    holder.ckb.setChecked(true);
                    itemStateArray.put(adapterPosition, true);
                    onItemCheckListener.onItemCheck(adapterPosition,staffLocation);
                }
                else
                {
                    holder.ckb.setChecked(false);
                    itemStateArray.put(adapterPosition, false);
                    onItemCheckListener.onItemUncheck(adapterPosition, staffLocation);
                }

            }
        });

        holder.ckb.setVisibility(View.INVISIBLE);
        holder.btnUpdatePassword.setVisibility(View.INVISIBLE);


    }

    @Override
    public int getItemCount() {
        return staffLocationList.size();
    }

    public class ShowStaffLocationViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView tv_FeatureName, tv_guardID
                , tv_locationName, tvUpdate
                , tv_StaffDesignation, tv_ManagerDesignation;

        CircleImageView imageWorker;
        CheckBox ckb;
        Button btnShowDuty, btnUpdateLocation, btnUpdatePassword;

        public ShowStaffLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_locationName = itemView.findViewById(R.id.tvLocationName_layout_dashboard);
            this.tv_FeatureName = itemView.findViewById(R.id.tvFeatureName_layout_dashboard);
            tv_guardID = itemView.findViewById(R.id.tvGuardID_layout_dashboard);
            this.ckb = itemView.findViewById(R.id.ckb_layout_Dashboard);
            ckb.setClickable(false);
            this.tvUpdate = itemView.findViewById(R.id.btnUpdateLocation_layout_dashboard);
            imageWorker = itemView.findViewById(R.id.image_layout_dashboard);
            card = itemView.findViewById(R.id.cardViewFeature);
            tv_StaffDesignation = itemView.findViewById(R.id.tvStaffDesignation_layout_dashboard);
            tv_ManagerDesignation = itemView.findViewById(R.id.tvManagerDesignation_layout_dashboard);
            btnShowDuty = itemView.findViewById(R.id.btnShowDuty_layout_dashboard);
            btnUpdateLocation = itemView.findViewById(R.id.btnUpdateLocation_layout_dashboard);
            btnUpdatePassword = itemView.findViewById(R.id.btnUpdatePassword_layout_dashboard);

        }
    }
}
