package com.example.eklass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ShowMyCompaniesAdapter extends
        RecyclerView.Adapter<ShowMyCompaniesAdapter.ShowMyCompaniesViewHolder>
{

    // this context we will use to inflate the layout
    private Context mCtx;
    private List<Company> companyList;



    public ShowMyCompaniesAdapter(Context mCtx, List<Company> companyList) {
        this.mCtx = mCtx;
        this.companyList = companyList;
    }

    @NonNull
    @Override
    public ShowMyCompaniesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.layout_dashboard, null);

        return new ShowMyCompaniesViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ShowMyCompaniesViewHolder holder, int position) {

        final User usr = SharedPrefManager.getInstance(mCtx).getUser();

        final Company company = companyList.get(position);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_profile_grey_24dp)
                .error(R.drawable.ic_profile_grey_24dp);


        // loading the image
        Glide.with(mCtx).load(company.getCompanyLogo()).apply(options).into(holder.logo);


        holder.tv_FeatureName.setText(company.getCompanyName());
        holder.tv_FeatureName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                //Intent i = new Intent(v.getContext(), ShowStaffActivity.class);
                Intent i = new Intent(v.getContext(), ShowStaffActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                //i.putExtra("companyID_fromShowCompany_activity",company.getCompanyID());
                //i.putExtra("companyName_fromShowCompany_activity",company.getCompanyName());

                usr.setCompanyId(company.getCompanyID().toString());
                usr.setCompanyName(company.getCompanyName());
                SharedPrefManager.getInstance(mCtx).userLogin(usr);

                v.getContext().startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public class ShowMyCompaniesViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_FeatureName, tv_guardID, tv_locationName, tv_ShowDuty;
        CircleImageView logo;
        public ShowMyCompaniesViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_FeatureName = itemView.findViewById(R.id.tvFeatureName_layout_dashboard);
            this.logo = itemView.findViewById(R.id.image_layout_dashboard);
        }
    }


}
