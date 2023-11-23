package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.eventelevate.Adapter.LocationListAdapter;
import com.example.eventelevate.Adapter.PhotosListAdapter;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.R;
import com.example.eventelevate.databinding.ActivityViewAllPhotosActivitiesBinding;

public class ViewAllPhotosActivities extends AppCompatActivity {

    private ActivityViewAllPhotosActivitiesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAllPhotosActivitiesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();
    }
    private void init(){
        AppManager.changeStatusBarandBottomColor(this);
        TextView textView = findViewById(R.id.name);
        textView.setText("Photos");
        findViewById(R.id.backbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        GridLayoutManager manager = new GridLayoutManager(ViewAllPhotosActivities.this,2);
        PhotosListAdapter eventsListAdapter = new PhotosListAdapter(ViewAllPhotosActivities.this, ProfileActivity.ImagesList);
        binding.listImages.setLayoutManager(manager);
        binding.listImages.setAdapter(eventsListAdapter);
    }
}