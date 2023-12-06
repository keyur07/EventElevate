package com.example.eventelevate.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.eventelevate.Activity.AboutUs;
import com.example.eventelevate.Activity.ContactUs;
import com.example.eventelevate.Activity.Location;
import com.example.eventelevate.Activity.LoginActivity;
import com.example.eventelevate.Activity.LoginScreen;
import com.example.eventelevate.Activity.SearchActivity;
import com.example.eventelevate.Adapter.BestOrganiserAdapter;
import com.example.eventelevate.Adapter.CategoriesListAdapter;
import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.ServiceModel;
import com.example.eventelevate.Model.ServiceProviderModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.databinding.FragmentEventsBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsFragment extends Fragment {

    FragmentEventsBinding binding;
    ArrayList<ServiceModel.Servicetype> servicetypes  = new ArrayList<>();
    private ActionBarDrawerToggle toggle;
    private int finalI = 0;
    private CategoriesListAdapter eventsListAdapter;

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
        binding.referesh.setRefreshing(true);


        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbarMain);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Event Peak");

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
                    GoogleSignInClient signInClient = GoogleSignIn.getClient(getActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN);
                    signInClient.signOut();
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    SharedPreferences sharedpreferences = getActivity().getSharedPreferences(String.valueOf(R.string.user_session_key), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(String.valueOf(R.string.user_session_key_username), "");
                    editor.putString(String.valueOf(R.string.user_session_key_password), "");
                    editor.apply();
                    startActivity(new Intent(getActivity(), LoginScreen.class));
                    getActivity().finish();
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

        binding.editTextSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
        binding.referesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finalI=0;
                if(servicetypes!=null){
                    servicetypes = null;
                    servicetypes  = new ArrayList<>();
                }
                init();
            }
        });
        init();
        return binding.getRoot();

    }

    public void init(){
        binding.container.setVisibility(View.INVISIBLE);
        LoadCategories();
    }

    private void LoadCategories() {
        getListofAllService();

    }

    private void getListofAllService() {

        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<ServiceModel> call = apiInterface.GetServiceType();
        call.enqueue(new Callback<ServiceModel>() {
            @Override
            public void onResponse(Call<ServiceModel> call, Response<ServiceModel> response) {

                if(response.body().getStatusCode()==200){


                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    binding.categoriesList.setLayoutManager(layoutManager);
                    BestOrganiserAdapter bestOrganiserAdapter = new BestOrganiserAdapter(EventsFragment.this,response.body().getServicetype());
                    binding.categoriesList.setAdapter(bestOrganiserAdapter);


                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
                    layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                    binding.categoriesListItems.setLayoutManager(layoutManager1);

                 getServicesList(response);

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

    private void getServicesList(Response<ServiceModel> response) {
        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<ServiceProviderModel> call1 = apiInterface.GetServicelistbytable(String.valueOf(response.body().getServicetype().get(finalI).getServiceId()));
        call1.enqueue(new Callback<ServiceProviderModel>() {
            @Override
            public void onResponse(Call<ServiceProviderModel> call, Response<ServiceProviderModel> responses) {
                if(responses.body().getStatusCode()==200){
                    if (responses.body().getServicetype().size() != 0) {
                        ServiceModel.Servicetype servicetype1 = new ServiceModel.Servicetype(response.body().getServicetype().get(finalI).getServiceName(),response.body().getServicetype().get(finalI).getServiceId());
                        servicetypes.add(servicetype1);

                    }
                }
                if((finalI+1)==response.body().getServicetype().size()){
                    eventsListAdapter  = new CategoriesListAdapter(EventsFragment.this,servicetypes);
                    binding.categoriesListItems.setAdapter(eventsListAdapter);
                    binding.referesh.setRefreshing(false);
                    binding.container.setVisibility(View.VISIBLE);
                }
                finalI++;
                if(finalI<response.body().getServicetype().size()){
                    getServicesList(response);
                }

            }

            @Override
            public void onFailure(Call<ServiceProviderModel> call, Throwable t) {

                Log.e("successsss","aaaaa"+servicetypes.size());
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
    private void updateRecyclerView(String query) {
        ArrayList<ServiceModel.Servicetype> filteredServiceTypes = new ArrayList<>();

        // Filter the list based on the search query
        for (ServiceModel.Servicetype serviceType : servicetypes) {
            if (serviceType.getServiceName().toLowerCase().contains(query)) {
                filteredServiceTypes.add(serviceType);
            }
        }

        // Update the adapter with the filtered list
        eventsListAdapter.updateList(filteredServiceTypes);
    }

}