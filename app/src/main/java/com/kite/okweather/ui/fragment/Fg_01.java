package com.kite.okweather.ui.fragment;

import android.view.View;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kite.okweather.R;
import com.kite.okweather.utils.BaseFragment;
import com.kite.okweather.utils.Utils;

public class Fg_01 extends BaseFragment {

    SwipeRefreshLayout SwipeRefreshLayout_01;

    @Override
    protected int initLayout() {
        return R.layout.fg_01;
    }

    @Override
    protected void initView(View view) {
        SwipeRefreshLayout_01 = view.findViewById(R.id.SwipeRefreshLayout_01);
        SwipeRefreshLayout_01.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                upDate();
            }
        });
    }

    private void upDate() {
        Utils.toast("s");
        SwipeRefreshLayout_01.setRefreshing(false);
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initSp() {

    }

    @Override
    protected void title() {

    }

    @Override
    public void onClick(View v) {

    }
}
