package com.example.eventelevate.Adapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.R;
import com.example.eventelevate.databinding.ActivityDisplayPhotoBinding;

public class DisplayPhoto extends AppCompatActivity {

    private ActivityDisplayPhotoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayPhotoBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Glide.with(this).load(PhotosListAdapter.image).into(binding.image);

    }
}