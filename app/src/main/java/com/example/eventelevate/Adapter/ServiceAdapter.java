package com.example.eventelevate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventelevate.Activity.ProfileActivity;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.ServiceProviderModel;
import com.example.eventelevate.R;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    private List<ServiceProviderModel.Servicetype> serviceList;
    private Context context;

    public ServiceAdapter(Context context, List<ServiceProviderModel.Servicetype> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {

        // Load the thumbnail image using Picasso (or any other image loading library)
        Glide.with(context)
               .load(serviceList.get(position).getImages().get(0).getImages())
               .placeholder(R.drawable.demo) // You can set a placeholder image
               .into(holder.thumbnailImageView);
        holder.titleTextView.setText(serviceList.get(position).getTitle());
        holder.priceTextView.setText(serviceList.get(position).getPrice());
        holder.locationTextView.setText(serviceList.get(position).getLocation());


        holder.serviceItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("datacatata",serviceList.get(position).getUserid().toString());
                Log.e("datacatata",serviceList.get(position).getId().toString());
                Log.e("datacatata",AppManager.selectedService);
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("user_id",serviceList.get(position).getUserid().toString());
                intent.putExtra("service_id",serviceList.get(position).getId().toString());
                intent.putExtra("serviceName", AppManager.selectedService);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnailImageView;
        TextView titleTextView;
        TextView priceTextView;
        TextView locationTextView;
        LinearLayout serviceItemLayout;

        public ServiceViewHolder(View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.serviceThumbnail);
            titleTextView = itemView.findViewById(R.id.serviceTitle);
            priceTextView = itemView.findViewById(R.id.servicePrice);
            locationTextView = itemView.findViewById(R.id.serviceLocation);
            serviceItemLayout = itemView.findViewById(R.id.serviceItemLayout);
        }
    }
}
