package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.R;
import com.example.eventelevate.databinding.ActivityDocumentsBinding;

public class DocumentsActivity extends AppCompatActivity {

    private ActivityDocumentsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDocumentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

    }
    private void init(){
        AppManager.changeStatusBarandBottomColor(DocumentsActivity.this);
        binding.homeScreen.setVisibility(View.VISIBLE);
        binding.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DocumentsActivity.this,DocumentsPolicyActivity.class);
                intent.putExtra("header","Take a selfie");
                intent.putExtra("position","1");
                startActivity(intent);
            }
        });

        binding.photoId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DocumentsActivity.this,DocumentsPolicyActivity.class);
                intent.putExtra("header","Take a photo of your Photo ID");
                intent.putExtra("position","2");
                startActivity(intent);
            }
        });
    }
}