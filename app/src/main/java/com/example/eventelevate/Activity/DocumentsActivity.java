package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.DocumentsModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.databinding.ActivityDocumentsBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentsActivity extends AppCompatActivity {

    private ActivityDocumentsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDocumentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        AppManager.showProgress(DocumentsActivity.this);
        getUserDocumentVerificationStatus();
        AppManager.changeStatusBarandBottomColor(DocumentsActivity.this);
        binding.homeScreen.setVisibility(View.VISIBLE);
        binding.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DocumentsActivity.this, DocumentsPolicyActivity.class);
                intent.putExtra("header", "Take a selfie");
                intent.putExtra("position", "1");
                startActivity(intent);
            }
        });

        binding.photoId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DocumentsActivity.this, DocumentsPolicyActivity.class);
                intent.putExtra("header", "Take a photo of your Photo ID");
                intent.putExtra("position", "2");
                startActivity(intent);
            }
        });
    }

    public void getUserDocumentVerificationStatus() {
        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<DocumentsModel> call = apiInterface.GetStatusofDocuments(AppManager.user.getUserId().toString());
        call.enqueue(new Callback<DocumentsModel>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<DocumentsModel> call, Response<DocumentsModel> response) {
                AppManager.hideProgress();
                if (response.body().getStatusCode() == 200) {
                    Log.e("responsese",""+response.body().getDocumentStatus().get(0).getPhotoStatus());
                    if (response.body().getDocumentStatus().get(0).getPhotoStatus().equals(2)) {
                        binding.photo.setBackgroundResource(R.color.verified);
                        binding.comments.setText("Verified");
                        binding.photo.setEnabled(false);
                        binding.statusPhoto.setImageResource(R.drawable.verified);
                    } else if (response.body().getDocumentStatus().get(0).getPhotoStatus().equals(1)) {
                        binding.photo.setBackgroundResource(R.color.pending);
                        binding.comments.setText("Pending");
                        binding.photo.setEnabled(false);
                    } else if (response.body().getDocumentStatus().get(0).getPhotoStatus().equals(0)) {
                        binding.photo.setBackgroundResource(R.color.not_submited);
                        binding.comments.setText("Need a attention");
                    }

                    if (response.body().getDocumentStatus().get(0).getPhotoidStatus().equals(2)) {
                        binding.photoId.setBackgroundResource(R.color.verified);
                        binding.photoId.setEnabled(false);
                        binding.comments2.setText("Verified");
                        binding.statusPhotoid.setImageResource(R.drawable.verified);
                    } else if (response.body().getDocumentStatus().get(0).getPhotoidStatus().equals(1)) {
                        binding.photoId.setBackgroundResource(R.color.pending);
                        binding.photoId.setEnabled(false);
                        binding.comments2.setText("Pending");
                    } else if (response.body().getDocumentStatus().get(0).getPhotoidStatus().equals(0)) {
                        binding.photoId.setBackgroundResource(R.color.not_submited);
                        binding.comments2.setText("Need a attention");
                    }
                } else {
                    if (response.body().getStatusCode() == 201) {
                        binding.photo.setBackgroundResource(R.color.not_submited);
                        binding.photoId.setBackgroundResource(R.color.not_submited);
                    }
                }
                binding.homeScreen.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<DocumentsModel> call, Throwable t) {
                AppManager.hideProgress();
                Toast.makeText(DocumentsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}