package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.example.eventelevate.Adapter.ServiceListAdapter;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.databinding.ActivityCategoriesBinding;

public class CategoriesActivity extends AppCompatActivity {


    private ActivityCategoriesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppManager.changeStatusBarandBottomColor(CategoriesActivity.this);


        binding.serviceList.setLayoutManager(new GridLayoutManager(CategoriesActivity.this,3));
        ServiceListAdapter serviceAdapter = new ServiceListAdapter(CategoriesActivity.this);
        binding.serviceList.setAdapter(serviceAdapter);
    }
}