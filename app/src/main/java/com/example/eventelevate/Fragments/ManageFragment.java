package com.example.eventelevate.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventelevate.R;
import com.example.eventelevate.databinding.FragmentManageBinding;

public class ManageFragment extends Fragment {

    private FragmentManageBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentManageBinding.inflate(getLayoutInflater());
        // Inflate the layout for this fragment
        init();
        return binding.getRoot();
    }

    private void init() {
        binding.newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}