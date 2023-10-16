package com.example.eventelevate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventelevate.R;

public class PackagesListAdapter extends RecyclerView.Adapter<PackagesListAdapter.ViewHolder> {

    public PackagesListAdapter(Fragment eventFragment) {

    }

    @NonNull
    @Override
    public PackagesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.packages_layout_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackagesListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class
    ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {

            super(itemView);

        }
    }
}
