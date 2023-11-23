package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.LoginModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();

        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppManager.changeStatusBarandBottomColor(LoginActivity.this);

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                intent.putExtra("google", "0");
                startActivity(intent);
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getuserVerified(binding.edtEmail.getText().toString(),binding.edtPassword.getText().toString());
              //  AppManager.changeActivity(LoginActivity.this,MainActivity.class);
            }
        });

    }

    private void getuserVerified(String username, String password) {

        AppManager.showProgress(this);
        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<LoginModel> call = apiInterface.VerifiedLoginData(username,password);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                AppManager.hideProgress();
                if(response.body().getStatusCode()==200){
                    if(response.body().getMessage().trim().equals("Success")){
                        StartUserSession(username,password);
                        AppManager.user = response.body().getUser();
                        AppManager.changeActivity(LoginActivity.this,MainActivity.class);
                    }
                }else if(response.body().getStatusCode()==201){
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                AppManager.hideProgress();
                Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.e("errror",t.getMessage());
            }
        });

    }

    private void StartUserSession(String username, String password) {

        AppManager.SaveShareData(this, String.valueOf(R.string.user_session_key_username),username);
        AppManager.SaveShareData(this, String.valueOf(R.string.user_session_key_password),password);

    }
}