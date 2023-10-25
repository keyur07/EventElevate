package com.example.eventelevate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventelevate.Activity.CategoriesActivity;
import com.example.eventelevate.Fragments.EventFragment;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.EventtypeModel;
import com.example.eventelevate.R;

import java.util.ArrayList;

public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.ViewHolder> {

    //private final Fragment eventFragment;
    ArrayList<EventtypeModel.EventType> eventtypeModels;
    Context context;

    public EventsListAdapter(ArrayList<EventtypeModel.EventType> eventFragment, Context context) {
        this.eventtypeModels =eventFragment;
        this.context = context;
    }

    @NonNull
    @Override
    public EventsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_layout_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsListAdapter.ViewHolder holder, int position) {
        EventtypeModel.EventType eventType = (EventtypeModel.EventType)  eventtypeModels.get(position);
        holder.name.setText(eventType.getEventTypeName().toString());
        holder.eventLocation.setText(eventType.getDescription().toString());
        Glide.with(context).load(eventType.getImage()).into(holder.eventPhotoImageView);
        holder.parent_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppManager.changeActivity(context, CategoriesActivity.class);
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventtypeModels.size();
    }

    public class
    ViewHolder extends RecyclerView.ViewHolder {

        private CardView parent_;
        private TextView name,eventLocation;
        private ImageView eventPhotoImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent_ = itemView.findViewById(R.id.event_items);
            name = itemView.findViewById(R.id.eventNameTextView);
            eventLocation=itemView.findViewById(R.id.eventLocationTextView);
            eventPhotoImageView=itemView.findViewById(R.id.eventPhotoImageView);

        }
    }
}
