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
import com.example.eventelevate.Model.Location.CityName;
import com.example.eventelevate.Model.Location.Loaction;
import com.example.eventelevate.Model.Location.MainLoction;
import com.example.eventelevate.Model.LocationModel;
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
    private List<LocationModel.Result> list = new ArrayList<>();
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
        Call<MainLoction> call = apiInterface.GetLocationList();
        call.enqueue(new Callback<MainLoction>() {
            @Override
            public void onResponse(Call<MainLoction> call, Response<MainLoction> response) {

                if (response.body().getStatusCode()==200) {
                    Toast.makeText(Location.this, "success....", Toast.LENGTH_SHORT).show();
                    ArrayList<CityName> cityNames = new ArrayList<>();
                    List<Loaction> location = response.body().getLoactions();
                    AppManager.hideProgress();
                    for (int i = 0; i < location.size(); i++) {
                        for (int j = 0; j < location.get(i).getResults().size(); j++) {
                            cityNames.add(new CityName(location.get(i).getResults().get(j).getName().toString(), location.get(i).getResults().get(j).getPhoto().toString()));
                        }
                    }


                    LocationListAdapter eventsListAdapter = new LocationListAdapter(Location.this, cityNames);
                    binding.locationList.setAdapter(eventsListAdapter);
                } else {

                    Toast.makeText(Location.this, "faile....", Toast.LENGTH_SHORT).show();
                }

              /*  if (response.isSuccessful() && response.body() != null) {
                    list.addAll(response.body().getResults());
                    Log.e("CombinedResponse", ""+list.size());
                } else {
                    Log.e("Error", "Request for page " + number + " failed.");
                }

                if(page==5){


                }else {
                    page++;
                    getLocationList(page);
                }
*/

            }

            @Override
            public void onFailure(Call<MainLoction> call, Throwable t) {

                Log.e("errrororo", t.getMessage().toString());

            }
        });
    }

    public int SplitText(String val){

        String input = val;
        int number = 1;

        Pattern pattern = Pattern.compile("page=(\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String numberString = matcher.group(1); // Group 1 contains the number
            number = Integer.parseInt(numberString); // Convert the string to an integer
            return number;
        } else {
            // Handle the case where the pattern is not found
           return 1;
        }
    }
}