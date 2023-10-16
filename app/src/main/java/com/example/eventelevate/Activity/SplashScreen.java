package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.LoginModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        CheckInternet();
    }

    public void init(){
        CheckUserloginStatus();
    }

    private void CheckUserloginStatus() {
        String username = AppManager.getSaveShareData(this, String.valueOf(R.string.user_session_key_username));
        String password = AppManager.getSaveShareData(this, String.valueOf(R.string.user_session_key_password));

        if(username.equals("") && password.equals("")){
            AppManager.changeActivity(this,LoginScreen.class);
            finish();
        }else {
            getUserData(username,password);
        }
    }


    public void CheckInternet(){


        if(isNetworkAvailable()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    init();
                }
            }, 2000);

        }else {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SplashScreen.this, R.style.CustomBottomSheetDialogTheme);
            View bottom = LayoutInflater.from(SplashScreen.this).inflate(R.layout.no_internet, null);
            bottomSheetDialog.setContentView(bottom);
            bottomSheetDialog.show();
            bottomSheetDialog.setCancelable(false);
            TextView refresh = bottom.findViewById(R.id.tryagain);

            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isNetworkAvailable()){
                        bottomSheetDialog.dismiss();
                        init();

                    }else {
                        Toast.makeText(SplashScreen.this, "Please Check Internet Connection Again..", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }
    private void getUserData(String username,String password){
        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<LoginModel> call = apiInterface.VerifiedLoginData(username,password);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if(response.body().getStatusCode()==200){
                    if (response.body().getMessage().equals("Success")){
                        AppManager.user = response.body().getUser();
                        AppManager.changeActivity(SplashScreen.this,MainActivity.class);
                        finish();

                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

            }
        });
    }

    public boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) SplashScreen.this.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}