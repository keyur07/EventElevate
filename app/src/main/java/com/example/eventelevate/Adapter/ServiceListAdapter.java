package com.example.eventelevate.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventelevate.Activity.CategoriesActivity;
import com.example.eventelevate.Activity.ListOfItems;
import com.example.eventelevate.Model.ServiceModel;
import com.example.eventelevate.R;

import java.util.ArrayList;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ViewHolder> {

    Activity context;
    ArrayList<ServiceModel.Servicetype> servicetypes;

    public ServiceListAdapter(Activity categoriesActivity, ArrayList<ServiceModel.Servicetype> servicetypes) {
        this.context=categoriesActivity;
        this.servicetypes=servicetypes;
    }

    @NonNull
    @Override
    public ServiceListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_view,parent,false);
        return new ServiceListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ServiceModel.Servicetype servicetype = (ServiceModel.Servicetype) servicetypes.get(position);
        holder.name.setText(servicetype.getServiceName());
        Glide.with(context).load(servicetype.getImage()).into(holder.icon);
        holder.cvService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListOfItems.class);
                intent.putExtra("tablename",servicetypes.get(position).getServiceId().toString());
                try{
                    context.startActivity(intent);
                }catch (Exception e){

                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return servicetypes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvService;
        ImageView icon;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvService=itemView.findViewById(R.id.cv_Service);
            icon=itemView.findViewById(R.id.icon);
            name=itemView.findViewById(R.id.name);
        }
    }
}
