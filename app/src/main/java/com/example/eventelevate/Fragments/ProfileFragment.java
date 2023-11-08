package com.example.eventelevate.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.eventelevate.Activity.ProfileActivity;
import com.example.eventelevate.Activity.SettingActivity;
import com.example.eventelevate.Activity.UserProfileActivity;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.R;
import com.example.eventelevate.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = FragmentProfileBinding.inflate(getLayoutInflater());
       Init();
       return binding.getRoot();
    }

    private void Init() {
        binding.name.setText(AppManager.user.getFirstName()+" "+AppManager.user.getLastName());
        Glide.with(this).load(AppManager.user.getPhoto()).into(binding.profilePHOTO);
        binding.manageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserProfileActivity.class));
            }
        });

        binding.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        Init();
        super.onResume();
    }
}