package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.eventelevate.Adapter.ServiceAdapter;
import com.example.eventelevate.Adapter.ServiceTypeAdapter;
import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.ServiceProviderModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.databinding.ActivityListOfItemsBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOfItems extends AppCompatActivity {

    private ActivityListOfItemsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListOfItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
AppManager.changeStatusBarandBottomColor(this);
        AppManager.showProgress(this);
        getServiceProviderlist();

    }

    private void getServiceProviderlist() {
        Intent intent = getIntent();
        String tablename = intent.getStringExtra("tablename");
        AppManager.selectedService = tablename;
        try{
            APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
            Call<ServiceProviderModel> call = apiInterface.GetServicelistbytable(tablename);
            call.enqueue(new Callback<ServiceProviderModel>() {
                @Override
                public void onResponse(Call<ServiceProviderModel> call, Response<ServiceProviderModel> response) {

                    AppManager.hideProgress();
                    if(response.body().getStatusCode()==200){
                        ServiceAdapter adapter = new ServiceAdapter(ListOfItems.this, response.body().getServicetype()); // Pass your data list here
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(ListOfItems.this));
                        binding.recyclerView.setAdapter(adapter);

                    }else if(response.body().getStatusCode()==201){

                    }

                }

                @Override
                public void onFailure(Call<ServiceProviderModel> call, Throwable t) {
                    AppManager.hideProgress();
                    // Snackbar snackbar = Snackbar.make(binding.container, "Something Went Wrong", 0);
                    //snackbar.show();
                }
            });
        }catch (Exception e){
            Log.e("errorororo",e.getMessage());
        }

    }
}