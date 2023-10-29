package com.example.eventelevate.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.eventelevate.Adapter.EventsListAdapter;
import com.example.eventelevate.Adapter.PackagesListAdapter;
import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.EventtypeModel;
import com.example.eventelevate.Model.PackageModel;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.databinding.FragmentPackagesBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackagesFragment extends Fragment {

FragmentPackagesBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      binding = FragmentPackagesBinding.inflate(getLayoutInflater());
      View view = binding.getRoot();
      init();
      return view;
    }

    public void init(){
        GetAllPackage();
    }

    private void GetAllPackage() {

        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<PackageModel> call = apiInterface.GetAllPackages();
        call.enqueue(new Callback<PackageModel>() {
            @Override
            public void onResponse(Call<PackageModel> call, Response<PackageModel> response) {

                AppManager.hideProgress();
                if(response.body().getStatusCode()==200){
                    List<PackageModel.EventType> eventtypeModels = response.body().getEventTypes();
                    binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                    PackagesListAdapter packagesFragment = new PackagesListAdapter(getActivity(),eventtypeModels);
                    binding.recyclerview.setAdapter(packagesFragment);

                }else if(response.body().getStatusCode()==201){
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PackageModel> call, Throwable t) {
                AppManager.hideProgress();
            }
        });
    }
}