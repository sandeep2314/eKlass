package com.example.eklass;

import android.content.Context;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ShowStaffAdapter
        extends RecyclerView.Adapter<ShowStaffAdapter.ShowStaffViewHolder>

    {

        // this context we will use to inflate the layout
        private Context mCtx;
        private List<Staff> staffList;
        SparseBooleanArray itemStateArray= new SparseBooleanArray();

        interface OnItemCheckListener
        {
            void onItemCheck(int pos, Staff item);
            void onItemUncheck(int pos, Staff item);
        }

        @NonNull
        private ShowStaffAdapter.OnItemCheckListener onItemCheckListener;

        // constructor
        /*public ShowStaffAdapter(Context mCtx, List<Staff> staff)
        {
            this.mCtx = mCtx;
            this.staffList = staff;
        }
*/
        public ShowStaffAdapter(Context mCtx, List<Staff> staffList
                , @NonNull OnItemCheckListener onItemCheckListener) {
            this.mCtx = mCtx;
            this.staffList = staffList;
            this.onItemCheckListener = onItemCheckListener;
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

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_profile_grey_24dp)
                    .error(R.drawable.ic_profile_grey_24dp);


            // loading the image
            Glide.with(mCtx).load(staff.getStaffImage()).apply(options).into(holder.imageStaff);

            //holder.ckbDelete.setVisibility(View.INVISIBLE);

            // binding the data with the viewholder views
            holder.tv_FeatureName.setText(staff.getStaffName());
            holder.tv_guardID.setText(staff.getDesignation());

            holder.tvUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), AddStaffActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //i.putExtra("SLId", staffLocation.getSLId());
                    i.putExtra("isUpdate","yes" );
                    i.putExtra("StaffID", staff.getStaffId());
                    i.putExtra("StaffName",staff.getStaffName() );
                    i.putExtra("StaffPassword",staff.getStaffPassword() );
                    i.putExtra("StaffMobile",staff.getMobileNo() );
                    i.putExtra("DesignationId",staff.getDesignationId() );
                    i.putExtra("IsActive",staff.getIsActive() );

                    v.getContext().startActivity(i);


                }
            });


            holder.tv_ShowDuty.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(v.getContext(), DutyActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("guardIDc", staff.getStaffId() );
                    v.getContext().startActivity(i);

                }
            });



            if (!itemStateArray.get(position, false))
            {
                holder.ckbDelete.setChecked(false);
            }
            else
            {
                holder.ckbDelete.setChecked(true);
            }

            holder.ckbDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //holder.ckb.setChecked(! holder.ckb.isChecked());


                    if (!itemStateArray.get(position, false))
                    {
                        holder.ckbDelete.setChecked(true);
                        itemStateArray.put(position, true);
                        onItemCheckListener.onItemCheck(position, staff);
                    }
                    else
                    {
                        holder.ckbDelete.setChecked(false);
                        itemStateArray.put(position, false);
                        onItemCheckListener.onItemUncheck(position, staff);
                    }

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
            TextView tv_FeatureName, tv_guardID, tv_locationName, tv_ShowDuty, tvUpdate;
            CircleImageView imageStaff;
            CheckBox ckbDelete;

            public ShowStaffViewHolder(@NonNull View itemView) {
                super(itemView);
                this.tv_FeatureName = itemView.findViewById(R.id.tvFeatureName_layout_dashboard);
                this.tv_guardID = itemView.findViewById(R.id.tvGuardID_layout_dashboard);
                this.tv_ShowDuty = itemView.findViewById(R.id.tvShowDuty_layout_dashboard);
                this.imageStaff = itemView.findViewById(R.id.image_layout_dashboard);
                this.ckbDelete = itemView.findViewById(R.id.ckb_layout_Dashboard);
                this.tvUpdate = itemView.findViewById(R.id.tvUpdate_layout_dashboard);
                //this.tv_locationName = itemView.findViewById(R.id.tvLocationName_layout_dashboard);

            }
        }


}
