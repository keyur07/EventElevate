package com.example.eventelevate.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.eventelevate.Adapter.EventsListAdapter;
import com.example.eventelevate.Adapter.PackagesListAdapter;
import com.example.eventelevate.databinding.FragmentPackagesBinding;

public class PackagesFragment extends Fragment {

FragmentPackagesBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      binding = FragmentPackagesBinding.inflate(getLayoutInflater());

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        PackagesListAdapter packagesFragment = new PackagesListAdapter(this);
        binding.recyclerview.setAdapter(packagesFragment);
      View view = binding.getRoot();
      return view;
    }
}