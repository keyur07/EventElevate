package com.example.eventelevate.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventelevate.Activity.Location;
import com.example.eventelevate.Model.LocationModel;
import com.example.eventelevate.R;

import java.util.List;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.ViewHolder> {

    private Location location;
    private List<LocationModel.Result> list;
    int selectedPosition = -1;
    int lastSelectedPosition = -1;
    public LocationListAdapter(Location location, List<LocationModel.Result> body) {
        this.list = body;
        this.location = location;
    }

    @NonNull
    @Override
    public LocationListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_view,parent,false);
        return new LocationListAdapter.ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull LocationListAdapter.ViewHolder holder, int position) {
        Glide.with(location).load(list.get(position).getPhoto()).into(holder.imageView);
        holder.textView.setText(list.get(position).getName());
        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Location.position = position;
                lastSelectedPosition = selectedPosition;
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(lastSelectedPosition);
                notifyItemChanged(selectedPosition);
                holder.frameLayout.setBackgroundColor(R.color.start_color);
            }
        });

        if (selectedPosition == holder.getAdapterPosition()) {
            holder.frameLayout.setBackgroundColor(Color.BLACK);
        } else {
            holder.frameLayout.setBackgroundColor(Color.WHITE);

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;
        private FrameLayout frameLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview_);
            textView = itemView.findViewById(R.id.cityname);
            frameLayout = itemView.findViewById(R.id.selected);
        }
    }
}
