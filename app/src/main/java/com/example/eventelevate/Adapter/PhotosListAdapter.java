package com.example.eventelevate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventelevate.Activity.ViewAllPhotosActivities;
import com.example.eventelevate.Model.ProviderProfileModel;
import com.example.eventelevate.R;

import java.util.List;

public class PhotosListAdapter extends RecyclerView.Adapter<PhotosListAdapter.ViewHolder> {

    private Context context;
    private List<ProviderProfileModel.Image> imageUris;
    public static String image;
    public PhotosListAdapter(ViewAllPhotosActivities viewAllPhotosActivities, List<ProviderProfileModel.Image> imagesList) {
        imageUris = imagesList;
        context = viewAllPhotosActivities;
    }

    @NonNull
    @Override
    public PhotosListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.viewall_photos_view, parent, false);
        return new PhotosListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosListAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(imageUris.get(position).getImages()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = imageUris.get(position).getImages();
                context.startActivity(new Intent(context,DisplayPhoto.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageUris.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview_);

        }
    }
}
