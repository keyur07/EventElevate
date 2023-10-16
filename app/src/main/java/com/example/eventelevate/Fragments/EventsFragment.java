package com.example.eventelevate.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eventelevate.Adapter.ViewPagerAdapter;
import com.example.eventelevate.R;
import com.example.eventelevate.databinding.FragmentEventBinding;
import com.example.eventelevate.databinding.FragmentEventsBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class EventsFragment extends Fragment {

    FragmentEventsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding  = FragmentEventsBinding.inflate(getLayoutInflater());

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(),getLifecycle());
        binding.viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            if(position==0){
                tab.setText("Event");
            }else {
                tab.setText("Packages");
            }

        }).attach();

        for (int i = 0; i < binding.tabLayout.getTabCount(); i++) {
            TextView tv=(TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab,null);
            binding.tabLayout.getTabAt(i).setCustomView(tv);
        }



        return binding.getRoot();




    }
}