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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eventelevate.Activity.AboutUs;
import com.example.eventelevate.Activity.ContactUs;
import com.example.eventelevate.Activity.Location;
import com.example.eventelevate.Activity.LoginActivity;
import com.example.eventelevate.Activity.MainActivity;
import com.example.eventelevate.Adapter.CategoriesListAdapter;
import com.example.eventelevate.Adapter.EventsListAdapter;
import com.example.eventelevate.Adapter.RecentlyListedAdapter;
import com.example.eventelevate.Adapter.ViewPagerAdapter;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.R;
import com.example.eventelevate.databinding.FragmentEventBinding;
import com.example.eventelevate.databinding.FragmentEventsBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayoutMediator;

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
        LoadRecentlyListedItem();
    }

    private void LoadCategories() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.categoriesList.setLayoutManager(layoutManager);
        CategoriesListAdapter eventsListAdapter = new CategoriesListAdapter(this);
        binding.categoriesList.setAdapter(eventsListAdapter);

        LinearLayoutManager layoutManagerr = new LinearLayoutManager(getActivity());
        layoutManagerr.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.locationList.setLayoutManager(layoutManagerr);
        binding.locationList.setAdapter(eventsListAdapter);

    }

    private void LoadRecentlyListedItem(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.recentlyListed.setLayoutManager(layoutManager);
        RecentlyListedAdapter listAdapter = new RecentlyListedAdapter(this);
        binding.recentlyListed.setAdapter(listAdapter);

    }

    public void openCloseNavigationDrawer(){
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        }
    }
}