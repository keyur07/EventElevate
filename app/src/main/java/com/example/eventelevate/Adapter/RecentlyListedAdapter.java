package com.example.eventelevate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventelevate.Fragments.EventsFragment;
import com.example.eventelevate.R;

public class RecentlyListedAdapter extends RecyclerView.Adapter<RecentlyListedAdapter.ViewHolder> {
    public RecentlyListedAdapter(EventsFragment eventsFragment) {

    }

    @NonNull
    @Override
    public RecentlyListedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recently_listed,parent,false);
        return new RecentlyListedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentlyListedAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
