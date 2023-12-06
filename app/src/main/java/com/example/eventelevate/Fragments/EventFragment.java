package com.example.eventelevate.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eventelevate.Adapter.EventsListAdapter;
import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.EventtypeModel;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.databinding.FragmentEventBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventFragment extends Fragment {

    FragmentEventBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEventBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        AppManager.showProgress(getActivity());
        binding.referesh.setRefreshing(true);
        binding.recyclerview.setVisibility(View.INVISIBLE);
        binding.referesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.recyclerview.setVisibility(View.INVISIBLE);
                getEventType();
            }
        });
        getEventType();

        return view;
    }

    public void getEventType(){

        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<EventtypeModel> call = apiInterface.GetEventType();
        call.enqueue(new Callback<EventtypeModel>() {
            @Override
            public void onResponse(Call<EventtypeModel> call, Response<EventtypeModel> response) {

                AppManager.hideProgress();
                if(response.body().getStatusCode()==200){
                    ArrayList<EventtypeModel.EventType> eventtypeModels = response.body().getEventTypes();
                    binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                    EventsListAdapter eventsListAdapter = new EventsListAdapter(eventtypeModels,getContext());
                    binding.recyclerview.setAdapter(eventsListAdapter);
                    binding.referesh.setRefreshing(false);
                    binding.recyclerview.setVisibility(View.VISIBLE);

                }else if(response.body().getStatusCode()==201){
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<EventtypeModel> call, Throwable t) {
                AppManager.hideProgress();
            }
        });
    }
}