package com.example.eklass;

import android.content.Context;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
        // binding the data with the viewholder views
        holder.tv_locationName.setText(staffLocation.getLocationName());
        holder.tv_FeatureName.setText(staffLocation.getWorkerName());
        holder.tv_guardID.setText(staffLocation.getManagerName()+"["
                + staffLocation.getManagerDesignation() +"]");
        //holder.tvUpdate.setMovementMethod(LinkMovementMethod.getInstance());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_profile_grey_24dp)
                .error(R.drawable.ic_profile_grey_24dp);

        Glide.with(mCtx).load(staffLocation.getWorkerImage()).apply(options).into(holder.imageWorker);

        holder.tvUpdate.setOnClickListener(new View.OnClickListener() {
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


    }

    @Override
    public int getItemCount() {
        return staffLocationList.size();
    }

    public class ShowStaffLocationViewHolder extends RecyclerView.ViewHolder {
        TextView tv_FeatureName, tv_guardID, tv_locationName, tvUpdate;
        CircleImageView imageWorker;
        CheckBox ckb;

        public ShowStaffLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_locationName = itemView.findViewById(R.id.tvLocationName_layout_dashboard);
            this.tv_FeatureName = itemView.findViewById(R.id.tvFeatureName_layout_dashboard);
            tv_guardID = itemView.findViewById(R.id.tvGuardID_layout_dashboard);
            this.ckb = itemView.findViewById(R.id.ckb_layout_Dashboard);
            ckb.setClickable(false);
            this.tvUpdate = itemView.findViewById(R.id.tvUpdate_layout_dashboard);
            imageWorker = itemView.findViewById(R.id.image_layout_dashboard);

        }
    }
}
