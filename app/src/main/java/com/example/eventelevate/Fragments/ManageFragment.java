package com.example.eventelevate.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.example.eventelevate.Activity.CreatePost;
import com.example.eventelevate.Activity.DocumentsActivity;
import com.example.eventelevate.Activity.MainActivity;
import com.example.eventelevate.Activity.SplashScreen;
import com.example.eventelevate.Adapter.MyEventListAdapter;
import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.MyServiceModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.databinding.FragmentManageBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageFragment extends Fragment {

    private FragmentManageBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentManageBinding.inflate(getLayoutInflater());

        binding.referesh.setRefreshing(true);
        binding.myeventlist.setVisibility(View.INVISIBLE);
        binding.referesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.myeventlist.setVisibility(View.INVISIBLE);
                init();
            }
        });
        // Inflate the layout for this fragment
        init();
        return binding.getRoot();
    }

    private void init() {
        binding.newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppManager.user.getIsVerified().equals(1)){
                    ShowBottomDialog();
                }else {
                    ShowVerifyYourSelf();
                }

            }
        });
        Getallmyeventlist();
    }

    private void ShowVerifyYourSelf() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.verify_dialog);
        dialog.setCancelable(false);
        dialog.show();

        dialog.findViewById(R.id.notnow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(getActivity(), DocumentsActivity.class));

            }
        });
    }

    private void Getallmyeventlist(){

        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<MyServiceModel> call = apiInterface.GetAllPostedService(String.valueOf(AppManager.user.getUserid()));
        call.enqueue(new Callback<MyServiceModel>() {
            @Override
            public void onResponse(Call<MyServiceModel> call, Response<MyServiceModel> response) {
                binding.referesh.setRefreshing(false);

                if (response.body().getStatusCode().equals(200)){
                    binding.myeventlist.setLayoutManager(new LinearLayoutManager(getActivity()));
                    MyEventListAdapter adapter = new MyEventListAdapter(getActivity(),response.body().getEvents());
                    binding.myeventlist.setAdapter(adapter);
                    if(response.body().getEvents().size()==0){
                        binding.myeventlist.setVisibility(View.GONE);
                        binding.noFound.setVisibility(View.VISIBLE);
                    }else {
                        binding.myeventlist.setVisibility(View.VISIBLE);
                        binding.noFound.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<MyServiceModel> call, Throwable t) {
                binding.referesh.setRefreshing(false);
            }
        });
    }

    @SuppressLint("MissingInflatedId")
    private void ShowBottomDialog() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialog);
        View bottom = LayoutInflater.from(getActivity()).inflate(R.layout.items_of_create_service_bootom_dialog, null);
        bottomSheetDialog.setContentView(bottom);
        bottomSheetDialog.show();

        bottom.findViewById(R.id.btn_Events).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                Intent intent = new Intent(getActivity(), CreatePost.class);
                intent.putExtra("types",0);
                startActivity(intent);
            }
        });

        bottom.findViewById(R.id.btn_packages).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog.dismiss();
                Intent intent = new Intent(getActivity(), CreatePost.class);
                intent.putExtra("types",1);
                startActivity(intent);
            }
        });
    }
}