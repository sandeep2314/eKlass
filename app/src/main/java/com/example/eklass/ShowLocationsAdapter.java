package com.example.eklass;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.text.method.LinkMovementMethod;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShowLocationsAdapter extends RecyclerView.Adapter<ShowLocationsAdapter.LocationViewHolder>
{

    private Context mCtx;
    private List<LocationQR> locationList;

    SparseBooleanArray itemStateArray= new SparseBooleanArray();


    interface OnItemCheckListener
    {
        void onItemCheck(int pos, LocationQR item);
        void onItemUncheck(int pos, LocationQR item);

    }

    ShowLocationsAdapter(){}

    @NonNull
    private OnItemCheckListener onItemCheckListener;

    public ShowLocationsAdapter(Context mCtx, List<LocationQR> locationList
                      ,@NonNull OnItemCheckListener onItemCheckListener  )
    {
        this.mCtx = mCtx;
        this.locationList = locationList;
        this.onItemCheckListener = onItemCheckListener;

    }



    @Override
    public void onBindViewHolder(@NonNull final LocationViewHolder holder, final int position) {


        final LocationQR theLocation = locationList.get(position);
        // binding the data with the viewholder views
        holder.tv_FeatureName.setText("     " + theLocation.getLocationId());
       // holder.tv_guardID.setText(theLocation.getLocationId());
        //holder.tvUpdate.setMovementMethod(LinkMovementMethod.getInstance());

        holder.tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, "tbnUpdate Clicked", Toast.LENGTH_LONG).show();

                Intent i = new Intent(v.getContext(), LocationActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("guardID",theLocation.getLocationId() );
                i.putExtra("isUpdate","yes" );
                i.putExtra("locationName",theLocation.getLocationName() );
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

            int adapterPosition = position;
            if (!itemStateArray.get(adapterPosition, false))
                {
                holder.ckb.setChecked(true);
                itemStateArray.put(adapterPosition, true);
                onItemCheckListener.onItemCheck(adapterPosition,theLocation);
                }
            else
                {
                holder.ckb.setChecked(false);
                itemStateArray.put(adapterPosition, false);
                onItemCheckListener.onItemUncheck(adapterPosition, theLocation);
                }

            }
        });


    }

    @Override
    public int getItemCount()
    {
        if(locationList == null)
            return 0;
        return locationList.size();
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.layout_dashboard, null);
        return new ShowLocationsAdapter.LocationViewHolder(view);

    }


    public class LocationViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_FeatureName, tv_guardID, tv_locationName, tvUpdate;
        CheckBox ckb;

        public LocationViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.tv_FeatureName = itemView.findViewById(R.id.tvFeatureName_layout_dashboard);
            this.ckb = itemView.findViewById(R.id.ckb_layout_Dashboard);
            ckb.setClickable(false);
            this.tvUpdate = itemView.findViewById(R.id.tvUpdate_layout_dashboard);


        }


    }




}
