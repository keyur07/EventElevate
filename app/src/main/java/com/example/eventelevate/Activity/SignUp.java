package com.example.eventelevate.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.LoginModel;
import com.example.eventelevate.Model.SignupModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.databinding.ActivitySignUpBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {
    ActivitySignUpBinding binding;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient signInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        binding.btnAccountCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateInputFields()) {

                    AppManager.showProgress(SignUp.this);
                    String firstName = binding.edtFirstname.getText().toString();
                    String lastName = binding.edtLastname.getText().toString();
                    String email = binding.edtEmail.getText().toString();
                    String password = binding.edtPassword.getText().toString();
                    APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
                    Call<SignupModel> call = apiInterface.CreateUser(firstName, lastName, email, password, AppManager.getDeviceID(SignUp.this));
                    call.enqueue(new Callback<SignupModel>() {
                        @Override
                        public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                            AppManager.hideProgress();
                            if (response.body().getStatusCode() == 200) {
                                if (response.body().getMessage().equals("User Created Successfully")) {
                                    APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
                                    Call<LoginModel> call2 = apiInterface.VerifiedLoginData(email, password);
                                    call2.enqueue(new Callback<LoginModel>() {
                                        @Override
                                        public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                                            if (response.body().getStatusCode() == 200) {
                                                if (response.body().getMessage().trim().equals("Success")) {
                                                    StartUserSession(email, password);
                                                    AppManager.user = response.body().getUser();
                                                    if(response.body().getUser().getMembershipStatus().equals(1)){
                                                        AppManager.changeActivity(SignUp.this, MainActivity.class);
                                                    }else {
                                                        AppManager.changeActivity(SignUp.this, PaymentActivity.class);
                                                    }

                                                }
                                            } else if (response.body().getStatusCode() == 201) {
                                                Toast.makeText(SignUp.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<LoginModel> call, Throwable t) {
                                            Snackbar snackbar = Snackbar.make(binding.coordinator, "Something Went Wrong", 0);
                                            snackbar.show();
                                            Log.e("errrororo",t.getMessage());
                                        }
                                    });
                                }
                            } else {

                                if (response.body().getStatusCode() == 201) {
                                    if (response.body().getMessage().equals("Email is already registered")) {
                                        Snackbar snackbar = Snackbar.make(binding.coordinator, "This email already Register", 0);
                                        snackbar.setAction("Sign In", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                AppManager.changeActivity(SignUp.this, LoginActivity.class);
                                            }
                                        });
                                        snackbar.show();
                                    } else {
                                        Snackbar snackbar = Snackbar.make(binding.coordinator, "Something Went Wrong", 0);
                                        snackbar.show();
                                        Log.e("errrororo",response.body().getMessage());
                                    }
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<SignupModel> call, Throwable t) {
                            AppManager.hideProgress();
                            Toast.makeText(SignUp.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {


                }

            }
        });
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, LoginActivity.class));
            }
        });

        Intent intent = getIntent();
        if (intent.getStringExtra("google").equals("1")) {
            getDataFromGoogleAccount();
        }
    }
    private void getDataFromGoogleAccount() {

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        signInClient = GoogleSignIn.getClient(SignUp.this, googleSignInOptions);

        GoogleSignInAccount signIn = GoogleSignIn.getLastSignedInAccount(SignUp.this);

        if (signIn != null) {

            binding.edtEmail.setText(signIn.getEmail());
            binding.edtFirstname.setText(signIn.getGivenName());
            binding.edtLastname.setText(signIn.getFamilyName());

        } else {
            Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
        }
    }


    private void StartUserSession(String username, String password) {

        AppManager.SaveShareData(this, String.valueOf(R.string.user_session_key_username), username);
        AppManager.SaveShareData(this, String.valueOf(R.string.user_session_key_password), password);

    }


    public boolean validateInputFields() {
        String firstName = null, lastName = null, email = null, password = null, confirmPassword = null;
        try {
            firstName = binding.edtFirstname.getText().toString();
            lastName = binding.edtLastname.getText().toString();
            email = binding.edtEmail.getText().toString();
            password = binding.edtPassword.getText().toString();
            confirmPassword = binding.edtConfirmPassword.getText().toString();

        } catch (Exception e) {

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