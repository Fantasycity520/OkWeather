package com.kite.okweather.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class Adapter_ViewPager2 extends FragmentStateAdapter {

    List<Fragment> fragments;

    public Adapter_ViewPager2(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        //Utils.log(position + "\t" + fragments.get(position).getClass().getSimpleName());
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}