package com.example.eventelevate.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventelevate.Activity.AboutUs;
import com.example.eventelevate.Activity.CategoriesActivity;
import com.example.eventelevate.Activity.ContactUs;
import com.example.eventelevate.Activity.Location;
import com.example.eventelevate.Activity.LoginActivity;
import com.example.eventelevate.Activity.MainActivity;
import com.example.eventelevate.Adapter.BestOrganiserAdapter;
import com.example.eventelevate.Adapter.CategoriesListAdapter;
import com.example.eventelevate.Adapter.EventsListAdapter;
import com.example.eventelevate.Adapter.RecentlyListedAdapter;
import com.example.eventelevate.Adapter.ServiceListAdapter;
import com.example.eventelevate.Adapter.ViewPagerAdapter;
import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.ServiceModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.databinding.FragmentEventBinding;
import com.example.eventelevate.databinding.FragmentEventsBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsFragment extends Fragment {

    FragmentEventsBinding binding;
    private ActionBarDrawerToggle toggle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding  = FragmentEventsBinding.inflate(getLayoutInflater());



        toggle = new ActionBarDrawerToggle(getActivity(), binding.drawerLayout, binding.toolbarMain, R.string.open, R.string.close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        AppManager.showProgress(getActivity());
        binding.locationName.setText(AppManager.SelectedLocation);
        binding.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.changeActivity(getActivity(), Location.class);
            }
        });

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbarMain);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Event Peak");

        binding.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.share_app) {
                    AppManager.ShareApplication(getActivity());
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                } else if (itemId == R.id.rate_app) {
                    AppManager.RateUs(getActivity());
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                } else if (itemId == R.id.profile) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                }else if(itemId == R.id.logout){
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    SharedPreferences sharedpreferences = getActivity().getSharedPreferences(String.valueOf(R.string.user_session_key), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(String.valueOf(R.string.user_session_key_username), "");
                    editor.putString(String.valueOf(R.string.user_session_key_password), "");
                    editor.apply();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else if(itemId == R.id.contactus){
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    AppManager.changeActivity(getActivity(), ContactUs.class);
                }else if(itemId == R.id.about_app){
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    AppManager.changeActivity(getActivity(), AboutUs.class);
                }
                return true;
            }
        });

        binding.toolbarMain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCloseNavigationDrawer();
            }
        });
        init();
        return binding.getRoot();

    }

    public void init(){
        LoadCategories();
    }

    private void LoadCategories() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.categoriesList.setLayoutManager(layoutManager);
        BestOrganiserAdapter bestOrganiserAdapter = new BestOrganiserAdapter(this);
        binding.categoriesList.setAdapter(bestOrganiserAdapter);


        getListofAllService();
    }

    private void getListofAllService() {

        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<ServiceModel> call = apiInterface.GetServiceType();
        call.enqueue(new Callback<ServiceModel>() {
            @Override
            public void onResponse(Call<ServiceModel> call, Response<ServiceModel> response) {

                if(response.body().getStatusCode()==200){
                    ArrayList<ServiceModel.Servicetype> servicetypes = response.body().getServicetype();

                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
                    layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                    binding.categoriesListItems.setLayoutManager(layoutManager1);
                    CategoriesListAdapter eventsListAdapter = new CategoriesListAdapter(EventsFragment.this,servicetypes);
                    binding.categoriesListItems.setAdapter(eventsListAdapter);


                }else if(response.body().getStatusCode()==201){
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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


    public void openCloseNavigationDrawer(){
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        }
    }
}