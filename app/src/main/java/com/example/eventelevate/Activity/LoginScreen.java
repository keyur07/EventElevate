package com.example.eventelevate.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.LoginModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.databinding.ActivityLoginScreenBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity {

    ActivityLoginScreenBinding binding;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient signInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();

        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        signInClient = GoogleSignIn.getClient(LoginScreen.this, googleSignInOptions);

        binding.btnLoginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.showProgress(LoginScreen.this);
                signIn();
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, LoginActivity.class));
            }
        });

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginScreen.this, SignUp.class));

            }
        });
    }

    private void signIn() {
        signInClient.signOut();
        Intent signInIntent = signInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AppManager.hideProgress();
        if (requestCode == 1) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d("GoogleSignIn", "Sign-in successful");
            navigatetoAnotherActivity(account);
        } catch (ApiException e) {
            Log.e("GoogleSignIn", "Sign-in failed with code: " + e.getStatusCode());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("GoogleSignIn", "Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void navigatetoAnotherActivity(GoogleSignInAccount account) {
        Intent intent = new Intent(LoginScreen.this, SignUp.class);
        AppManager.showProgress(LoginScreen.this);
        if (account.getEmail() != null) {
            APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
            Call<LoginModel> call = apiInterface.GetUserByEmailId(account.getEmail());
            call.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    AppManager.hideProgress();
                    if (response.body().getStatusCode().equals(200)) {
                        AppManager.user = response.body().getUser();
                        AppManager.SaveShareData(LoginScreen.this, String.valueOf(R.string.user_session_key_username),account.getId());
                        AppManager.SaveShareData(LoginScreen.this, String.valueOf(R.string.user_session_key_password),"");
                        AppManager.changeActivity(LoginScreen.this, MainActivity.class);
                        finish();
                    } else {
                        intent.putExtra("google", "1");
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    AppManager.hideProgress();
                    Toast.makeText(LoginScreen.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    Log.e("errororo",t.getMessage());
                }
            });
        }

    }
}