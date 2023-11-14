package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.LoginModel;
import com.example.eventelevate.Model.SettingModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient signInClient;

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

    public void init() {
        CheckUserloginStatus();
    }

    private void CheckUserloginStatus() {
        String username = AppManager.getSaveShareData(this, String.valueOf(R.string.user_session_key_username));
        String password = AppManager.getSaveShareData(this, String.valueOf(R.string.user_session_key_password));
        String policy = AppManager.getSaveShareData(SplashScreen.this, "policy");

        if (policy.equals("1")) {

            if (username.equals("") && password.equals("")) {
                AppManager.changeActivity(this, LoginScreen.class);
                finish();
            } else {
                if (getDataFromGoogleAccount()) {
                    googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
                    signInClient = GoogleSignIn.getClient(SplashScreen.this, googleSignInOptions);
                    GoogleSignInAccount signIn = GoogleSignIn.getLastSignedInAccount(SplashScreen.this);
                    getUSerDataWithGoogle(signIn.getEmail());
                } else {
                    getUserData(username, password);
                }
            }

        } else {
            AppManager.changeActivity(SplashScreen.this, TermsAndConditions.class);
            finish();
        }

    }

    private boolean getDataFromGoogleAccount() {

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        signInClient = GoogleSignIn.getClient(SplashScreen.this, googleSignInOptions);

        GoogleSignInAccount signIn = GoogleSignIn.getLastSignedInAccount(SplashScreen.this);

        if (signIn != null) {
            return true;
        } else {
            return false;
        }

    }

    private void getUSerDataWithGoogle(String username) {
        AppManager.showProgress(SplashScreen.this);
        if (username != null) {
            APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
            Call<LoginModel> call = apiInterface.GetUserByEmailId(username);
            call.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    AppManager.hideProgress();
                    if (response.body().getStatusCode().equals(200)) {
                        AppManager.user = response.body().getUser();
                        AppManager.changeActivity(SplashScreen.this, MainActivity.class);
                        finish();
                    } else {
                        AppManager.changeActivity(SplashScreen.this, LoginScreen.class);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    AppManager.hideProgress();
                    Toast.makeText(SplashScreen.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    Log.e("errororo", t.getMessage());
                }
            });
        }
    }

    public void showunderMaintenanceDialog() {

        final Dialog dialog = new Dialog(SplashScreen.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.maintenance_dialog);
        dialog.setCancelable(false);
        final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.rating);
        dialog.findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() { // from class: m.a.a.b.c0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                finish();
            }
        });
        dialog.show();
    }

    private void getApplicationSetting() {
        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<SettingModel> call = apiInterface.GetApplicationSetting();
        call.enqueue(new Callback<SettingModel>() {
            @Override
            public void onResponse(Call<SettingModel> call, Response<SettingModel> response) {
                if (response.body().getStatusCode() == 200) {
                    if (response.body().getMessage().equals("Success")) {
                        AppManager.setting = response.body().getSetting();

                        if (response.body().getSetting().getMaintenance() == 1) {
                            showunderMaintenanceDialog();
                        } else {
                            init();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<SettingModel> call, Throwable t) {

            }
        });
    }


    public void CheckInternet() {


        if (isNetworkAvailable()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getApplicationSetting();
                }
            }, 2000);

        } else {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SplashScreen.this, R.style.CustomBottomSheetDialogTheme);
            View bottom = LayoutInflater.from(SplashScreen.this).inflate(R.layout.no_internet, null);
            bottomSheetDialog.setContentView(bottom);
            bottomSheetDialog.show();
            bottomSheetDialog.setCancelable(false);
            TextView refresh = bottom.findViewById(R.id.tryagain);

            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetworkAvailable()) {
                        bottomSheetDialog.dismiss();
                        init();

                    } else {
                        Toast.makeText(SplashScreen.this, "Please Check Internet Connection Again..", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }

    private void getUserData(String username, String password) {
        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<LoginModel> call = apiInterface.VerifiedLoginData(username, password);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.body().getStatusCode() == 200) {
                    if (response.body().getMessage().equals("Success")) {
                        AppManager.user = response.body().getUser();
                        AppManager.changeActivity(SplashScreen.this, MainActivity.class);
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