package com.example.eventelevate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eventelevate.Model.AboutUsModel;
import com.example.eventelevate.R;

import retrofit2.Callback;

public class CustomAdapter extends BaseAdapter {
    private final LayoutInflater infalter;
    public AboutUsModel worker_image;
    public String[] name;
    public String[] emailid;
    public String[] develop;

    public CustomAdapter(Context aboutUsModelCallback, AboutUsModel body) {
        this.worker_image = body;
        this.name = name;
        this.emailid = emailid;
        this.develop = develop;
        infalter= LayoutInflater.from(aboutUsModelCallback);

    }

    @Override
    public int getCount() {
        return worker_image.getAbout().size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = infalter.inflate(R.layout.about_us_view,parent,false);
        ImageView imageView = convertView.findViewById(R.id.imageView2);
        TextView namee = convertView.findViewById(R.id.textView21);
        TextView email = convertView.findViewById(R.id.textView20);
        TextView devlop = convertView.findViewById(R.id.textView23);

        Glide.with(parent.getContext()).load(worker_image.getAbout().get(position).getImage()).into(imageView);
       namee.setText(worker_image.getAbout().get(position).getFirstname()+" "+worker_image.getAbout().get(position).getLastname());
       email.setText(worker_image.getAbout().get(position).getDescription());
       devlop.setText(worker_image.getAbout().get(position).getProfession());

        return convertView;
    }
}
