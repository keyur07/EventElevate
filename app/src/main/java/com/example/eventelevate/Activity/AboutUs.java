package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.AboutUsModel;
import com.example.eventelevate.Model.SettingModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.databinding.ActivityAboutUsBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUs extends AppCompatActivity {

    private ActivityAboutUsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    public void init(){
        GetAboutUsPage();
        AppManager.changeStatusBarandBottomColor(AboutUs.this);
    }

    public void GetAboutUsPage(){
        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<AboutUsModel> call = apiInterface.GetAboutDetails();
        call.enqueue(new Callback<AboutUsModel>() {
            @Override
            public void onResponse(Call<AboutUsModel> call, Response<AboutUsModel> response) {
                if(response.body().getStatusCode()==200){
                    if (response.body().getMessage().equals("Success")){

                    }
                }
            }

            @Override
            public void onFailure(Call<AboutUsModel> call, Throwable t) {

            }
        });
    }
}