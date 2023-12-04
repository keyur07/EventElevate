package com.example.eventelevate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventelevate.Fragments.EventsFragment;
import com.example.eventelevate.Model.ServiceModel;
import com.example.eventelevate.R;

import java.util.ArrayList;

public class BestOrganiserAdapter extends RecyclerView.Adapter<BestOrganiserAdapter.ViewHolder> {

    private ArrayList<ServiceModel.Servicetype> servicetype;
    private EventsFragment eventsFragment;

    public BestOrganiserAdapter(EventsFragment eventsFragment, ArrayList<ServiceModel.Servicetype> servicetype) {
        this.eventsFragment = eventsFragment;
        this.servicetype = servicetype;
    }

    @NonNull
    @Override
    public BestOrganiserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_categories_list, parent, false);
        return new BestOrganiserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BestOrganiserAdapter.ViewHolder holder, int position) {
        Glide.with(eventsFragment).load(servicetype.get(position).getImage()).into(holder.imageView);
        holder.name.setText(servicetype.get(position).getServiceName());

    }

    @Override
    public int getItemCount() {
        return servicetype.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
        }
    }
}