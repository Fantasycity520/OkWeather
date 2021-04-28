package com.kite.okweather.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kite.okweather.R;
import com.kite.okweather.ui.adapter.Adapter_ViewPager2;
import com.kite.okweather.ui.fragment.Fg_01;
import com.kite.okweather.ui.fragment.Fg_02;
import com.kite.okweather.ui.fragment.Fg_03;
import com.kite.okweather.ui.fragment.Fg_04;
import com.kite.okweather.ui.fragment.Fg_City;
import com.kite.okweather.ui.fragment.Fg_Main;
import com.kite.okweather.utils.BaseActivity;
import com.kite.okweather.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NonConstantResourceId")
public class Main extends BaseActivity {


    @Override
    protected int layoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new Fg_Main()).commit();
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initSp() {

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.title_city:
//
//                break;
//            default:
//                break;
//        }
    }

}