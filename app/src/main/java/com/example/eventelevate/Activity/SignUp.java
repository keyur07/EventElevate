package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.LoginModel;
import com.example.eventelevate.Model.SignupModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.databinding.ActivityLoginBinding;
import com.example.eventelevate.databinding.ActivitySignUpBinding;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {
    ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppManager.changeStatusBarandBottomColor(SignUp.this);

        binding.btnAccountCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateInputFields()){
                    String firstName = binding.edtFirstname.getText().toString();
                    String lastName = binding.edtLastname.getText().toString();
                    String email = binding.edtEmail.getText().toString();
                    String password = binding.edtPassword.getText().toString();
                    APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
                    Call<SignupModel> call = apiInterface.CreateUser(firstName,lastName,email,password, AppManager.getDeviceID(SignUp.this));
                    call.enqueue(new Callback<SignupModel>() {
                        @Override
                        public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {

                            if(response.body().getStatusCode()==200){
                                if(response.body().getMessage().equals("User Created Successfully")){
                                    APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
                                    Call<LoginModel> call2 = apiInterface.VerifiedLoginData(email,password);
                                    call2.enqueue(new Callback<LoginModel>() {
                                        @Override
                                        public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                                            if(response.body().getStatusCode()==200){
                                                if(response.body().getMessage().trim().equals("Success")){
                                                    StartUserSession(email,password);
                                                    AppManager.user = response.body().getUser();
                                                    AppManager.changeActivity(SignUp.this,MainActivity.class);
                                                }
                                            }else if(response.body().getStatusCode()==201){
                                                Toast.makeText(SignUp.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<LoginModel> call, Throwable t) {
                                            Snackbar snackbar = Snackbar.make(binding.coordinator, "Something Went Wrong", 0);
                                            snackbar.show();
                                        }
                                    });
                                }
                            }else {

                                if(response.body().getStatusCode()==201){
                                    if(response.body().getMessage().equals("Email is already registered")){
                                        Snackbar snackbar = Snackbar.make(binding.coordinator, "This email already Register", 0);
                                        snackbar.setAction("Sign In", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                AppManager.changeActivity(SignUp.this,LoginActivity.class);
                                            }
                                        });
                                        snackbar.show();
                                    }else {
                                        Snackbar snackbar = Snackbar.make(binding.coordinator, "Something Went Wrong", 0);
                                        snackbar.show();
                                    }
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<SignupModel> call, Throwable t) {

                        }
                    });
                }else {


                }

            }
        });

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SignUp.this,LoginActivity.class));
            }
        });


    }

    private void StartUserSession(String username, String password) {

        AppManager.SaveShareData(this, String.valueOf(R.string.user_session_key_username),username);
        AppManager.SaveShareData(this, String.valueOf(R.string.user_session_key_password),password);

    }


    public boolean validateInputFields() {
        String firstName = null,lastName = null,email = null,password = null,confirmPassword = null;
        try{
             firstName = binding.edtFirstname.getText().toString();
             lastName = binding.edtLastname.getText().toString();
             email = binding.edtEmail.getText().toString();
             password = binding.edtPassword.getText().toString();
             confirmPassword = binding.edtConfirmPassword.getText().toString();

        }catch (Exception e){

        }

        if (firstName.isEmpty()) {
            Snackbar snackbar = Snackbar.make(binding.coordinator, "First name is required", 0);
            snackbar.show();
            return false;
        }

        if (lastName.isEmpty()) {
            Snackbar snackbar = Snackbar.make(binding.coordinator, "Last name is required", 0);
            snackbar.show();
            return false;
        }

        if (email.isEmpty()) {
            Snackbar snackbar = Snackbar.make(binding.coordinator, "Email is required", 0);
            snackbar.show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Snackbar snackbar = Snackbar.make(binding.coordinator, "Invalid email format", 0);
            snackbar.show();
            return false;
        }

        if (password.isEmpty()) {
            Snackbar snackbar = Snackbar.make(binding.coordinator, "Password is required", 0);
            snackbar.show();
            return false;
        } else if (password.length() < 8) {
            Snackbar snackbar = Snackbar.make(binding.coordinator, "Password must be at least \" + 8 + \" characters", 0);
            snackbar.show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Snackbar snackbar = Snackbar.make(binding.coordinator, "Passwords do not match", 0);
            snackbar.show();
            return false;
        }


        return true;
    }

}