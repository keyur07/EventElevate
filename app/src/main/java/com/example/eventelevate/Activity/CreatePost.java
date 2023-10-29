package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.eventelevate.Fragments.CreateFragment;
import com.example.eventelevate.Fragments.CreatePackageFragment;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.R;
import com.example.eventelevate.databinding.ActivityCreatePostBinding;

public class CreatePost extends AppCompatActivity {

    private ActivityCreatePostBinding binding;
    private int types;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Init();

    }

    private void Init() {
        AppManager.changeStatusBarandBottomColor(this);
        getDataFromIntent();
        LoadFragments(types);

    }

    private void LoadFragments(int types) {

        if (types == 0) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new CreateFragment())
                    .commit();

        }else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new CreatePackageFragment())
                    .commit();
        }
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        types = intent.getIntExtra("types",0);

    }
}