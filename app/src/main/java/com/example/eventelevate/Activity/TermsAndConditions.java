package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.R;
import com.example.eventelevate.databinding.ActivityTermsAndConditionsBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TermsAndConditions extends AppCompatActivity {

    private ActivityTermsAndConditionsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTermsAndConditionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String htmlContent = loadHtmlFile("policy.html");
        AppManager.changeStatusBarandBottomColor(this);
        // Display HTML content in the TextView
        if (htmlContent != null) {
            binding.policy.setText(Html.fromHtml(htmlContent));
        }
        binding.btAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.SaveShareData(TermsAndConditions.this,"policy","1");
                AppManager.changeActivity(TermsAndConditions.this,IntroActivity.class);
            }
        });

        binding.btDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.SaveShareData(TermsAndConditions.this,"policy","0");
                finish();
            }
        });

    }

    private String loadHtmlFile(String filename) {
        try {
            InputStream is = getAssets().open(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}