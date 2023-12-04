package com.example.eventelevate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.eventelevate.Activity.ProfileActivity;
import com.example.eventelevate.Model.ServiceProviderModel;
import com.example.eventelevate.R;
import java.util.ArrayList;
import java.util.List;

public class AllServiceAdapter extends RecyclerView.Adapter<AllServiceAdapter.ServiceViewHolder> {

    private List<ServiceProviderModel.Servicetype> serviceList;
    private List<ServiceProviderModel.Servicetype> itemListCopy; // Copy of the original list
    private Context context;

    public AllServiceAdapter(Context context, List<ServiceProviderModel.Servicetype> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
        this.itemListCopy = new ArrayList<>(serviceList); // Create a copy of the original list
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_layout_view, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        holder.eventNameTextView.setText(serviceList.get(position).getTitle());
        holder.description.setText(serviceList.get(position).getLocation());
        holder.eventLocationTextView.setText(serviceList.get(position).getLocation());
        holder.price.setText("From: $ " + serviceList.get(position).getPrice());
        Glide.with(context).load(serviceList.get(position).getImages().get(0).getImages()).into(holder.thumbnail);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("user_id",serviceList.get(position).getUserid().toString());
                intent.putExtra("service_id",serviceList.get(position).getId().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public void filterList(String query) {
        serviceList.clear();
        if (query.isEmpty()) {
            serviceList.addAll(itemListCopy); // If the query is empty, restore the original list
        } else {
            for (ServiceProviderModel.Servicetype item : itemListCopy) {
                if (item.getDescription().toLowerCase().contains(query.toLowerCase()) ||
                        item.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    serviceList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumbnail;
        private TextView eventNameTextView, eventLocationTextView, price, description;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            eventNameTextView = itemView.findViewById(R.id.heading);
            description = itemView.findViewById(R.id.description);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            eventLocationTextView = itemView.findViewById(R.id.location_name);
            price = itemView.findViewById(R.id.price);
        }
    }
}
