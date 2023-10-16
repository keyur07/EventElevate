package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.R;

public class Introduction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        AppManager.HideActionBar(Introduction.this);
        AppManager.ChangeColorOfStatusBar(this,R.color.start_color);




    }
}