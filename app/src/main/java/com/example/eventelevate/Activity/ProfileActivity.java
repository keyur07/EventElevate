package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.eventelevate.Adapter.PhotoAdapter;
import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.ProviderProfileModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.databinding.ActivityProfileBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private int ClientID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Init();
    }

    private void Init() {
        AppManager.changeStatusBarandBottomColor(this);
        GetServiceProviderProfile();

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfileActivity.this, FirebaseActivity.class);

                String message = "Hello, Second Activity!";
                intent.putExtra("msg", message);
                intent.putExtra("status", "User");
                intent.putExtra("userId", AppManager.user.getUserId().toString());
                intent.putExtra("clientId",String.valueOf(ClientID));
                startActivity(intent);
            }
        });
    }

    private void GetServiceProviderProfile() {

        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");
        String service_id = intent.getStringExtra("service_id");
        String serviceName = intent.getStringExtra("serviceName");

        Log.e("datacatata",user_id);
        Log.e("datacatata",service_id);
        Log.e("datacatata",serviceName);
        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);

        Call<ProviderProfileModel> call = apiInterface.GetAllDetailsOfProvider(user_id,service_id);
        call.enqueue(new Callback<ProviderProfileModel>() {
            @Override
            public void onResponse(Call<ProviderProfileModel> call, Response<ProviderProfileModel> response) {
                if (response.body().getStatusCode()==200){
                    Glide.with(getApplicationContext()).load(response.body().getImages().get(0).getImages()).into(binding.thumbnail);
                    binding.txtProviderName.setText(response.body().getUserData().get(0).getFirstName()+" "+response.body().getUserData().get(0).getLastName());
                    binding.txtLocation.setText(response.body().getServiceData().get(0).getLocation());
                    binding.txtPrice.setText("$"+response.body().getServiceData().get(0).getPrice());
                    binding.txtDescription.setText(response.body().getServiceData().get(0).getDescription());
                    ClientID = response.body().getUserData().get(0).getId();
                    PhotoAdapter photoAdapter = new PhotoAdapter(this,response.body().getImages());
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    binding.photoRecyclerview.setLayoutManager(layoutManager);
                    binding.photoRecyclerview.setAdapter(photoAdapter);
                }
            }
            @Override
            public void onFailure(Call<ProviderProfileModel> call, Throwable t) {
                Log.e("datadata",t.getMessage());
            }
        });

    }
}