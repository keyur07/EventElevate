package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.R;
import com.example.eventelevate.databinding.ActivityDocumentsPolicyBinding;

public class DocumentsPolicyActivity extends AppCompatActivity {

    private ActivityDocumentsPolicyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDocumentsPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }
    private void init(){
        AppManager.changeStatusBarandBottomColor(DocumentsPolicyActivity.this);
        Intent intent = getIntent();
        binding.header.setText(intent.getStringExtra("header"));
        if(intent.getStringExtra("position").equals("1")){
            binding.title.setText("Profile Photo");
        }else {
            binding.title.setText("Photo ID");
        }
        binding.take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DocumentsPolicyActivity.this,CameraActivity.class);
                intent1.putExtra("position",intent.getStringExtra("position"));
                startActivity(intent1);
            }
        });
    }
}