package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.eventelevate.Adapter.CategoriesListAdapter;
import com.example.eventelevate.Adapter.ServiceTypeAdapter;
import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.ServiceProviderModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.databinding.ActivityListOfItemsBinding;
import com.example.eventelevate.databinding.ActivityShowAllServicesBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowAllServices extends AppCompatActivity {

    private ActivityShowAllServicesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowAllServicesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Init();
    }

    private void Init() {
        Intent intent = getIntent();
        AppManager.changeStatusBarandBottomColor(this);
      //  getSupportActionBar().setTitle(intent.getStringExtra("table_name"));
        getDatabyTable(intent.getStringExtra("table_name"));
        Toast.makeText(this, ""+intent.getStringExtra("table_name"), Toast.LENGTH_SHORT).show();
    }

    private void getDatabyTable(String serviceName) {
        AppManager.showProgress(this);
        try{
            APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
            Call<ServiceProviderModel> call = apiInterface.GetServicelistbytable(serviceName);
            call.enqueue(new Callback<ServiceProviderModel>() {
                @Override
                public void onResponse(Call<ServiceProviderModel> call, Response<ServiceProviderModel> response) {

                    AppManager.hideProgress();
                    if(response.body().getStatusCode()==200){
                        AppManager.hideProgress();
                        ServiceProviderModel servicetypes =  response.body();
                        GridLayoutManager layoutManager = new GridLayoutManager(ShowAllServices.this,2);
                        binding.recyclerView.setLayoutManager(layoutManager);
                        ServiceTypeAdapter eventsListAdapter = new ServiceTypeAdapter(ShowAllServices.this,servicetypes,0);
                        binding.recyclerView.setAdapter(eventsListAdapter);

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
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }

    }
}