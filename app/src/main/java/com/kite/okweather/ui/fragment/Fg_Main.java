package com.kite.okweather.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kite.okweather.R;
import com.kite.okweather.utils.BaseFragment;

public class Fg_Main extends BaseFragment {


    @Override
    protected int initLayout() {
        return R.layout.fg_main;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initSp() {

    }

    @Override
    protected void title() {
        ImageView title_city = getActivity().findViewById(R.id.title_city);
        title_city.setImageResource(R.drawable.title_01);
        title_city.setOnClickListener(this);
        
        LinearLayout ll_ed_title, ll_title_location;
        ll_ed_title = getActivity().findViewById(R.id.ll_ed_title);
        ll_ed_title.setVisibility(View.GONE);
        ll_title_location = getActivity().findViewById(R.id.ll_title_location);
        ll_title_location.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_city:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fg_main, new Fg_City()).commit();
                break;
            default:
                break;
        }
    }
}
