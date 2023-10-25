package com.example.eventelevate.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventelevate.Model.ServiceModel;
import com.example.eventelevate.Model.ServiceProviderModel;
import com.example.eventelevate.R;

import java.util.ArrayList;

public class ServiceTypeAdapter extends RecyclerView.Adapter<ServiceTypeAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<ServiceProviderModel.Servicetype> servicetypes;
    public ServiceTypeAdapter(FragmentActivity activity, ArrayList<ServiceProviderModel.Servicetype> servicetypes) {
        this.activity = activity;
        this.servicetypes = servicetypes;
    }

    @NonNull
    @Override
    public ServiceTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainpage_item_view,parent,false);
        return new ServiceTypeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceTypeAdapter.ViewHolder holder, int position) {
        holder.name.setText(servicetypes.get(position).getTitle());
        holder.price.setText(servicetypes.get(position).getPrice());
        Glide.with(activity).load(servicetypes.get(position).getImages().get(0).getImages()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return servicetypes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name,price;
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            imageView =itemView.findViewById(R.id.icon);

        }
    }
}
