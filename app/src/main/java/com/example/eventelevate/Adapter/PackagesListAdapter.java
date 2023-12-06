package com.example.eventelevate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventelevate.Activity.CategoriesActivity;
import com.example.eventelevate.Activity.ProfileActivity;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.EventtypeModel;
import com.example.eventelevate.Model.PackageModel;
import com.example.eventelevate.R;

import java.util.ArrayList;
import java.util.List;

public class PackagesListAdapter extends RecyclerView.Adapter<PackagesListAdapter.ViewHolder> {
    List<PackageModel.Servicetype> eventtypeModels;
    Context context;
    public PackagesListAdapter(FragmentActivity activity, List<PackageModel.Servicetype> eventtypeModels) {
        this.eventtypeModels =eventtypeModels;
        this.context = activity;
    }

    @NonNull
    @Override
    public PackagesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_layout_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackagesListAdapter.ViewHolder holder, int position) {
        holder.name.setText(eventtypeModels.get(position).getTitle().toString());
        holder.eventLocation.setText(eventtypeModels.get(position).getLocation().toString());
        holder.eventDescriptionTextView.setText(eventtypeModels.get(position).getDescription().toString());
        Glide.with(context).load(eventtypeModels.get(position).getImages().get(0).getImages() ).into(holder.eventPhotoImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("user_id",eventtypeModels.get(position).getUserid().toString());
                intent.putExtra("service_id",eventtypeModels.get(position).getId().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventtypeModels.size();
    }

    public class
    ViewHolder extends RecyclerView.ViewHolder {

        private TextView name,eventLocation,eventDescriptionTextView;
        private ImageView eventPhotoImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.heading);
            eventLocation=itemView.findViewById(R.id.location_name);
            eventPhotoImageView=itemView.findViewById(R.id.thumbnail);
            eventDescriptionTextView = itemView.findViewById(R.id.description);

        }
    }
}
