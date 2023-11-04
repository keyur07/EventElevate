package com.example.eventelevate.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventelevate.Model.ListOption;
import com.example.eventelevate.Model.MyServiceModel;
import com.example.eventelevate.R;

import java.util.Arrays;
import java.util.List;

public class MyEventListAdapter extends RecyclerView.Adapter<MyEventListAdapter.Viewholder> {
    private Activity activity;
    private List<MyServiceModel.Event> events;
    public MyEventListAdapter(FragmentActivity activity, List<MyServiceModel.Event> events) {
        this.activity = activity;
        this.events =events;
    }

    @NonNull
    @Override
    public MyEventListAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myevents_view,parent,false);
        return new MyEventListAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyEventListAdapter.Viewholder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(events.get(position).getImages().get(0).getImages()).into(holder.imageView);
        holder.header.setText(events.get(position).getTitle());
        holder.description.setText(events.get(position).getDescription());
        holder.price.setText("Price : $"+events.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView imageView,btn_edit;
        private TextView header,description,price;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            header = itemView.findViewById(R.id.header);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            btn_edit = itemView.findViewById(R.id.edit);
        }
    }
}
