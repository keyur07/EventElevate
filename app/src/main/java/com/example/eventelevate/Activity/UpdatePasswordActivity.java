package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.SignupModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.databinding.ActivityUpdatePasswordBinding;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePasswordActivity extends AppCompatActivity {
    private ActivityUpdatePasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdatePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppManager.changeStatusBarandBottomColor(UpdatePasswordActivity.this);



        binding.btnChangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVallidation()){
                    AppManager.showProgress(UpdatePasswordActivity.this);
                    try{
                        UpdatePassword();
                    }catch (Exception e){
                        Log.e("errrororo",e.getMessage());
                    }

                }
            }
        });

        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void UpdatePassword() {
        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<SignupModel> call = apiInterface.UpdatePassword(AppManager.user.getEmail(),binding.lpassword.getText().toString(),binding.opassword.getText().toString());
        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                AppManager.hideProgress();
                try{
                    if(response.body().getStatusCode().equals(200)){
                        Snackbar snackbar = Snackbar.make(binding.coordinator, "Password Updated Successfully", 0);
                        snackbar.show();
                    }else {
                        if(response.body().getStatusCode().equals(204)){
                            Snackbar snackbar = Snackbar.make(binding.coordinator, "Incorrect Old Password", 0);
                            snackbar.show();
                        }

                    }
                }catch (Exception e){
                    Log.e("errrororo",e.getMessage()+" "+response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                AppManager.hideProgress();
                Snackbar snackbar = Snackbar.make(binding.coordinator, "Something went wrong", 0);
                snackbar.show();
            }
        });

    }

    private boolean isVallidation() {

        if (binding.fpassword.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(binding.coordinator, "Password is required", 0);
            snackbar.show();
            return false;
        } else if (binding.fpassword.length() < 8) {
            Snackbar snackbar = Snackbar.make(binding.coordinator, "Password must be at least \" + 8 + \" characters", 0);
            snackbar.show();
            return false;
        }
        if (binding.opassword.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(binding.coordinator, "Password is required", 0);
            snackbar.show();
            return false;
        }

        if (!binding.fpassword.getText().toString().equals(binding.lpassword.getText().toString())) {
            Snackbar snackbar = Snackbar.make(binding.coordinator, "Passwords do not match", 0);
            snackbar.show();
            return false;
        }
        return true;
    }
}