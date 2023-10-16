package com.example.eventelevate.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventelevate.Adapter.EventsListAdapter;
import com.example.eventelevate.databinding.FragmentEventBinding;

public class EventFragment extends Fragment {

    FragmentEventBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEventBinding.inflate(getLayoutInflater());

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        EventsListAdapter eventsListAdapter = new EventsListAdapter(this);
        binding.recyclerview.setAdapter(eventsListAdapter);

       View view = binding.getRoot();
       return view;
    }
}