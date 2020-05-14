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

import java.util.List;

public class ShowDesignationAdapter
        extends RecyclerView.Adapter<ShowDesignationAdapter.DesignationViewHolder>
{
    private Context mCtx;
    private List<Designation> designationList;
    SparseBooleanArray itemStateArray= new SparseBooleanArray();

    interface OnItemCheckListener
    {
        void onItemCheck(int pos, Designation item);
        void onItemUncheck(int pos, Designation item);

    }

    @NonNull
    private ShowDesignationAdapter.OnItemCheckListener onItemCheckListener;

    public ShowDesignationAdapter(Context mCtx, List<Designation> designationList
            , @NonNull OnItemCheckListener onItemCheckListener) {
        this.mCtx = mCtx;
        this.designationList = designationList;
        this.onItemCheckListener = onItemCheckListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShowDesignationAdapter.DesignationViewHolder holder, final int position) {
        final Designation designation = designationList.get(position);
        // binding the data with the viewholder views
        holder.tv_FeatureName.setText("     " + designation.getDesignationId());

        holder.tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, "Update Clicked", Toast.LENGTH_LONG).show();

                Intent i = new Intent(v.getContext(), LocationActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //i.putExtra("guardID",theLocation.getLocationId() );
                i.putExtra("isUpdate","yes" );
                i.putExtra("dName",designation.getDName() );
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

        //holder.ckb.setText("Select ");

        holder.ckb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //holder.ckb.setChecked(! holder.ckb.isChecked());


                if (!itemStateArray.get(position, false))
                {
                    //holder.ckb.setChecked(true);

                    holder.ckb.setChecked(true);
                    itemStateArray.put(position, true);
                    onItemCheckListener.onItemCheck(position, designation);
                }
                else
                {
                    holder.ckb.setChecked(false);
                    itemStateArray.put(position, false);
                    onItemCheckListener.onItemUncheck(position, designation);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return designationList.size();
    }

    @NonNull
    @Override
    //public ShowLocationsAdapter.LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    public  ShowDesignationAdapter.DesignationViewHolder
        onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.layout_dashboard, null);
        return new ShowDesignationAdapter.DesignationViewHolder(view);

    }

    public class DesignationViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_FeatureName, tv_guardID, tv_locationName, tvUpdate;
        CheckBox ckb;

        public DesignationViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.tv_FeatureName = itemView.findViewById(R.id.tvFeatureName_layout_dashboard);
            this.tvUpdate = itemView.findViewById(R.id.tvUpdate_layout_dashboard);
            ckb.setClickable(false);


        }

    }

}
