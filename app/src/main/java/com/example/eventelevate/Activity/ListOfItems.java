package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
        binding.referesh.setRefreshing(true);
        getServiceProviderlist();

        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.referesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.recyclerView.setVisibility(View.INVISIBLE);
                getServiceProviderlist();
            }
        });
    }

    private void getServiceProviderlist() {
        Intent intent = getIntent();
        String tablename = intent.getStringExtra("tablename");
        String name = intent.getStringExtra("name");
        binding.name.setText(name);
        AppManager.selectedService = tablename;
        try {
            APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
            Call<ServiceProviderModel> call = apiInterface.GetServicelistbytable(tablename);
            call.enqueue(new Callback<ServiceProviderModel>() {
                @Override
                public void onResponse(Call<ServiceProviderModel> call, Response<ServiceProviderModel> response) {

                    AppManager.hideProgress();
                    binding.referesh.setRefreshing(false);
                    if (response.body().getStatusCode() == 200) {
                        ServiceAdapter adapter = new ServiceAdapter(ListOfItems.this, response.body().getServicetype()); // Pass your data list here
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(ListOfItems.this));
                        binding.recyclerView.setAdapter(adapter);
                        if(response.body().getServicetype().size()==0){
                            binding.recyclerView.setVisibility(View.INVISIBLE);
                            binding.noFound.setVisibility(View.VISIBLE);
                        }else {
                            binding.recyclerView.setVisibility(View.VISIBLE);
                            binding.noFound.setVisibility(View.GONE);
                        }


                    } else if (response.body().getStatusCode() == 201) {
                        binding.recyclerView.setVisibility(View.INVISIBLE);
                        binding.noFound.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<ServiceProviderModel> call, Throwable t) {
                    AppManager.hideProgress();
                    // Snackbar snackbar = Snackbar.make(binding.container, "Something Went Wrong", 0);
                    //snackbar.show();
                }
            });
        } catch (Exception e) {
            Log.e("errorororo", e.getMessage());
        }

    }
}