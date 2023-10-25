package com.example.eventelevate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventelevate.Fragments.EventsFragment;
import com.example.eventelevate.R;

public class BestOrganiserAdapter extends RecyclerView.Adapter<BestOrganiserAdapter.ViewHolder>{

    public BestOrganiserAdapter(EventsFragment eventsFragment) {

    }

    @NonNull
    @Override
    public BestOrganiserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_categories_list,parent,false);
        return new BestOrganiserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BestOrganiserAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}