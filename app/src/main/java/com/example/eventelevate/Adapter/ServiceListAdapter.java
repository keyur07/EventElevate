package com.example.eventelevate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventelevate.Activity.CategoriesActivity;
import com.example.eventelevate.R;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ViewHolder> {
    public ServiceListAdapter(CategoriesActivity categoriesActivity) {
    }

    @NonNull
    @Override
    public ServiceListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_view,parent,false);
        return new ServiceListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceListAdapter.ViewHolder holder, int position) {

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
