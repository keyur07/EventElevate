package com.example.eventelevate.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventelevate.R;
import com.example.eventelevate.databinding.FragmentCreateBinding;

public class CreateFragment extends Fragment {

    FragmentCreateBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      binding = FragmentCreateBinding.inflate(getLayoutInflater());
      return binding.getRoot();
    }
}