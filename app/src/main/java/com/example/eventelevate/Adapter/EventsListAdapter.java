package com.example.eventelevate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventelevate.Activity.CategoriesActivity;
import com.example.eventelevate.Fragments.EventFragment;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.R;

public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.ViewHolder> {

    private final Fragment eventFragment;

    public EventsListAdapter(Fragment eventFragment) {
            this.eventFragment =eventFragment;
    }

    @NonNull
    @Override
    public EventsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_layout_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsListAdapter.ViewHolder holder, int position) {

        holder.parent_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppManager.changeActivity(eventFragment.getActivity(), CategoriesActivity.class);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class
    ViewHolder extends RecyclerView.ViewHolder {

        private CardView parent_;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent_ = itemView.findViewById(R.id.event_items);

        }
    }
}
