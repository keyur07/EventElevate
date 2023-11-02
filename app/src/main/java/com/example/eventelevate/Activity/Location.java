package com.example.eventelevate.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.eventelevate.Adapter.EventsListAdapter;
import com.example.eventelevate.Adapter.LocationListAdapter;
import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.CityModel;

import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.databinding.ActivityLocationBinding;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Location extends AppCompatActivity {
    ActivityLocationBinding binding;
    public static int position;
    int page =1;
    private List<CityModel.Loaction.Result> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppManager.changeStatusBarandBottomColor(Location.this);
        AppManager.showProgress(Location.this);
        getLocationList(page);

        binding.btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.SelectedLocation = list.get(position).getName();
                AppManager.changeActivity(Location.this,MainActivity.class);
            }
        });

        findViewById(R.id.backbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppManager.changeActivity(Location.this,MainActivity.class);
            }
        });


    }

    private void getLocationList(int number) {
        APIInterface apiInterface = RetrofitClient.getRetrofitInstanceforlocation().create(APIInterface.class);
        Call<CityModel> call = apiInterface.GetLocationList();
        call.enqueue(new Callback<CityModel>() {
            @Override
            public void onResponse(Call<CityModel> call, Response<CityModel> response) {
                if (response.body().getStatusCode() == 200) {
                    AppManager.hideProgress();
                    list = response.body().getLoactions().get(0).getResults();

                    GridLayoutManager manager = new GridLayoutManager(Location.this,2);
                    LocationListAdapter eventsListAdapter = new LocationListAdapter(Location.this, response.body().getLoactions().get(0).getResults());
                    binding.locationList.setLayoutManager(manager);
                    binding.locationList.setAdapter(eventsListAdapter);
                } else {
                    AppManager.hideProgress();
                    Toast.makeText(Location.this, "faile....", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<CityModel> call, Throwable t) {
                Log.e("errororor",t.getMessage());
            }
        });
    }
}