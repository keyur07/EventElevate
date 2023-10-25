package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.eventelevate.Adapter.ServiceListAdapter;
import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.ServiceModel;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.databinding.ActivityCategoriesBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesActivity extends AppCompatActivity {


    private ActivityCategoriesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppManager.changeStatusBarandBottomColor(CategoriesActivity.this);

        AppManager.showProgress(this);
        getServiceList();
    }

    public void getServiceList(){

        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<ServiceModel> call = apiInterface.GetServiceType();
        call.enqueue(new Callback<ServiceModel>() {
            @Override
            public void onResponse(Call<ServiceModel> call, Response<ServiceModel> response) {

                AppManager.hideProgress();
                if(response.body().getStatusCode()==200){
                    ArrayList<ServiceModel.Servicetype> servicetypes = response.body().getServicetype();
                    //    Log.d("TAG", "onResponse:.......................................... "+servicetypes.get(0).getServiceName());
                    binding.serviceList.setLayoutManager(new GridLayoutManager(CategoriesActivity.this,3));
                    ServiceListAdapter serviceAdapter = new ServiceListAdapter(CategoriesActivity.this,servicetypes);
                    binding.serviceList.setAdapter(serviceAdapter);

                }else if(response.body().getStatusCode()==201){
                    Toast.makeText(CategoriesActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ServiceModel> call, Throwable t) {
                AppManager.hideProgress();
                // Snackbar snackbar = Snackbar.make(binding.container, "Something Went Wrong", 0);
                //snackbar.show();
            }
        });
    }
}