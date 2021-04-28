package com.kite.okweather.ui.fragment;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kite.okweather.R;

import com.kite.okweather.beans.Db_Bean_Now;
import com.kite.okweather.services.HttpGetService;

import com.kite.okweather.utils.BaseActivity;
import com.kite.okweather.utils.BaseFragment;
import com.kite.okweather.utils.Utils;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;


public class Fg_01 extends BaseFragment {

    static SwipeRefreshLayout SwipeRefreshLayout_01;
    static TextView tv_test;

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
        tv_test = view.findViewById(R.id.tv_test);
    }

    private void upDate() {

        Intent intent = new Intent(getActivity(), HttpGetService.class);
        getActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
        SwipeRefreshLayout_01.setRefreshing(false);

    }

    public void showData() {
        List<Db_Bean_Now> list = LitePal.findAll(Db_Bean_Now.class);
        Utils.log(list.size());
        tv_test.setText("现有数据" + list.size() + "条");
        httpGetBinder.d();
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
//        Intent intent = new Intent(getActivity(), HttpGetService.class);
//        switch (v.getId()) {
//            case R.id.bindService:
//                getActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
//                break;
//            case R.id.unbindService:
//                getActivity().unbindService(connection);
//                break;
//        }
    }

    public static HttpGetService.HttpGetBinder httpGetBinder;

    public static ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            httpGetBinder = (HttpGetService.HttpGetBinder) service;
            httpGetBinder.startGet();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}
