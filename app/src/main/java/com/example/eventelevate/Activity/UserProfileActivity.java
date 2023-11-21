package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.LoginModel;
import com.example.eventelevate.Model.SignupModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.databinding.ActivityUserProfileBinding;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {
    private ActivityUserProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Init();
    }

    private void Init() {
        updateData();
        binding.updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUserProfile();
            }
        });
    }

    private void updateData() {
        try{
            binding.firstNameEditText.setText(AppManager.user.getFirstName());
            binding.lastNameEditText.setText(AppManager.user.getLastName());
            binding.emailTextView.setText(AppManager.user.getEmail());
            Glide.with(getApplicationContext()).load(AppManager.user.getPhoto()).into(binding.userPhoto);
        }catch (Exception e){

        }

    }
    private void UpdateUserProfile() {
        try {
            AppManager.showProgress(this);
            APIInterface userService = RetrofitClient.getRetrofitInstance().create(APIInterface.class);

            String userId =  String.valueOf(AppManager.user.getUserId());
            String firstName =  binding.firstNameEditText.getText().toString();
            String lastName =  binding.lastNameEditText.getText().toString();

            Call<SignupModel> call = userService.UpdateUserProfile(userId, firstName, lastName);
            call.enqueue(new Callback<SignupModel>() {
                @Override
                public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                    AppManager.hideProgress();
                    if (response.isSuccessful() && response.body().getStatusCode() == 200) {
                        AppManager.StatusDialog(UserProfileActivity.this, true,"Profile Updated Successfully");
                        CheckUserloginStatus();
                    } else {
                        // Handle errors
                    }
                }

                @Override
                public void onFailure(Call<SignupModel> call, Throwable t) {
                    AppManager.hideProgress();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
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

    private void getUserData(String username,String password){
        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<LoginModel> call = apiInterface.VerifiedLoginData(username,password);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if(response.body().getStatusCode()==200){
                    if (response.body().getMessage().equals("Success")){
                        AppManager.user = response.body().getUser();
                        updateData();

                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

            }
        });
    }

}