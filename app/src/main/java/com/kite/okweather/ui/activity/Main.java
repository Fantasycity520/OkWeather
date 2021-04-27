package com.kite.okweather.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kite.okweather.R;
import com.kite.okweather.ui.fragment.Fg_City;
import com.kite.okweather.ui.fragment.Fg_Main;
import com.kite.okweather.utils.BaseActivity;

public class Main extends BaseActivity {


    @Override
    protected int layoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fg_main, new Fg_Main()).commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initSp() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_city:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fg_main, new Fg_City()).commit();
                break;
            default:
                break;
        }
    }
}