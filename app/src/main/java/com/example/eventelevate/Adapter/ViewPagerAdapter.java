package com.example.eventelevate.Adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.eventelevate.Fragments.EventFragment;
import com.example.eventelevate.Fragments.PackagesFragment;


public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(FragmentManager supportFragmentManager, Lifecycle lifecycle) {
        super(supportFragmentManager,lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position)  {
        switch (position){
            case 0:
                return new EventFragment();
            case 1:
                return  new PackagesFragment();
            default:
                return new EventFragment();
        }
    }


    @Override
    public int getItemCount() {
        return 2;
    }
}
