package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.ContactModel;
import com.example.eventelevate.Model.LoginModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.databinding.ActivityContactUsBinding;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUs extends AppCompatActivity {

    private ActivityContactUsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppManager.changeStatusBarandBottomColor(ContactUs.this);

        binding.senddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.emailField3.getText().toString().equals("")){
                    Snackbar snackbar = Snackbar.make(binding.container, "Subject Should be not null", 0);
                    snackbar.show();
                    return;
                }
                if(binding.textArea.getText().toString().equals("")){
                    Snackbar snackbar = Snackbar.make(binding.container, "Message Should be not null", 0);
                    snackbar.show();
                    return;
                }
                AppManager.showProgress(ContactUs.this);
                SendUserContactMessage();
            }
        });

    }

    private void SendUserContactMessage() {
        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<ContactModel> call = apiInterface.SendUserContactMessage(String.valueOf(AppManager.user.getUserId()),AppManager.user.getFirstName()+" "+AppManager.user.getLastName(),AppManager.user.getEmail(),binding.emailField3.getText().toString(),binding.textArea.getText().toString());
        call.enqueue(new Callback<ContactModel>() {
            @Override
            public void onResponse(Call<ContactModel> call, Response<ContactModel> response) {

                AppManager.hideProgress();
                if(response.body().getStatusCode()==200){
                    if(response.body().getMessage().trim().equals("Contact request submitted successfully")){
                        binding.emailField3.setText("");
                        binding.textArea.setText("");
                        AppManager.StatusDialog(ContactUs.this,true,"Message Submitted Successfully");
                    }
                }else if(response.body().getStatusCode()==201){
                    Toast.makeText(ContactUs.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ContactModel> call, Throwable t) {
                AppManager.hideProgress();
                Snackbar snackbar = Snackbar.make(binding.container, "Something Went Wrong", 0);
                snackbar.show();
            }
        });
    }
}