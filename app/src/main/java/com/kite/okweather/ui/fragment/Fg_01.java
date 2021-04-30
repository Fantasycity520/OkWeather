package com.kite.okweather.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kite.okweather.R;

import com.kite.okweather.beans.Db_Bean_3Day;
import com.kite.okweather.beans.Db_Bean_7Day;
import com.kite.okweather.beans.Db_Bean_City;
import com.kite.okweather.beans.Db_Bean_Hours;
import com.kite.okweather.beans.Db_Bean_My_City_List;
import com.kite.okweather.beans.Db_Bean_Now;
import com.kite.okweather.beans.Weather_Bean_3Day;
import com.kite.okweather.beans.Weather_Bean_7Day;
import com.kite.okweather.beans.Weather_Bean_City;
import com.kite.okweather.beans.Weather_Bean_Hours;
import com.kite.okweather.beans.Weather_Bean_Now;
import com.kite.okweather.services.HttpGetService;

import com.kite.okweather.utils.BaseFragment;
import com.kite.okweather.utils.HttpUtil;
import com.kite.okweather.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class Fg_01 extends BaseFragment {

    static SwipeRefreshLayout SwipeRefreshLayout_01;
    static TextView tv_test;
    Intent intent;
    Db_Bean_My_City_List db_bean_my_city;

    //判断是否允许刷新
    Boolean isB = false;
    //判断是否是第一次刷新
    int isI = 0;

    public Fg_01(Db_Bean_My_City_List db_bean_my_city) {
        this.db_bean_my_city = db_bean_my_city;
    }

    public Fg_01() {

    }

    @Override
    public void onStart() {
        super.onStart();
        initDataOnStart();

    }


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
                List<Db_Bean_My_City_List> lists = LitePal.findAll(Db_Bean_My_City_List.class);
                upDate(lists.get(0).getCityId());
                stopS();
            }
        });
        tv_test = view.findViewById(R.id.tv_test);
    }

    /**
     * 停止网络请求服务
     */
    private void stopS() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isB = true;
                if (intent != null) {
                    getActivity().stopService(intent);
                } else {
                    isB = true;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SwipeRefreshLayout_01.setRefreshing(false);
                        }
                    });
                }
            }
        }, 1000);
    }

    /**
     * 启动服务 网络请求
     */
    private void upDate(String location) {
        if (isI == 0) {
            isI++;
            isB = true;
            upDate(location);
        }
        if (isB) {
            intent = new Intent(getActivity(), HttpGetService.class);
            intent.putExtra("city", location);
//        getActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
            getActivity().startService(intent);
        } else {
            Utils.toast("不要频繁刷新");

        }

    }

    /**
     * 网络请求成功 广播发回 处理ui逻辑
     */
    List<Db_Bean_Now> db_bean_nows;
    List<Db_Bean_3Day> db_bean_3Days;
    List<Db_Bean_7Day> db_bean_7Days;
    List<Db_Bean_Hours> db_bean_hours;
    List<Db_Bean_City> db_bean_cities;

    List<Db_Bean_My_City_List> db_bean_my_city_lists;

    public void showData() {


        db_bean_nows = LitePal.findAll(Db_Bean_Now.class);
        db_bean_3Days = LitePal.findAll(Db_Bean_3Day.class);
        db_bean_7Days = LitePal.findAll(Db_Bean_7Day.class);
        db_bean_hours = LitePal.findAll(Db_Bean_Hours.class);
        db_bean_cities = LitePal.findAll(Db_Bean_City.class);
        Gson gson = new Gson();
        Weather_Bean_Now now = gson.fromJson(db_bean_nows.get(0).getNow(), Weather_Bean_Now.class);
        Weather_Bean_City city = gson.fromJson(db_bean_cities.get(0).getCity(), Weather_Bean_City.class);
        Weather_Bean_3Day day3 = gson.fromJson(db_bean_3Days.get(0).getDay3(), Weather_Bean_3Day.class);
        Weather_Bean_7Day day7 = gson.fromJson(db_bean_7Days.get(0).getDay7(), Weather_Bean_7Day.class);
        Weather_Bean_Hours hours = gson.fromJson(db_bean_hours.get(0).getHours(), Weather_Bean_Hours.class);

        tv_test.setText(city.getLocation().get(0).getName() + "\t" + now.getNow().getText() + "\n");

        SwipeRefreshLayout_01.setRefreshing(false);
    }



    @Override
    protected void initData() {
//        bing_pic_img
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

    /**
     * 显示到界面上的初始化操作
     */
    private void initDataOnStart() {
        if (db_bean_my_city != null) {
            //显示这和界面的时候自动刷新
            SwipeRefreshLayout_01.post(new Runnable() {
                @Override
                public void run() {
                    SwipeRefreshLayout_01.setRefreshing(true);
                    upDate(db_bean_my_city.getCityId());
                    stopS();
                    List<Db_Bean_Now> list = LitePal.findAll(Db_Bean_Now.class);
                    //Utils.log(list.size());
                    tv_test.setText("现有数据" + list.size() + "条");
                }
            });
            Utils.toast(db_bean_my_city.getCity());
        } else {
            List<Db_Bean_My_City_List> lists = LitePal.findAll(Db_Bean_My_City_List.class);
            SwipeRefreshLayout_01.post(new Runnable() {
                @Override
                public void run() {
                    SwipeRefreshLayout_01.setRefreshing(true);
                    upDate(lists.get(0).getCityId());
                    stopS();
                    List<Db_Bean_Now> list = LitePal.findAll(Db_Bean_Now.class);
                    //Utils.log(list.size());
                    tv_test.setText("现有数据" + list.size() + "条");
                }
            });
        }
    }

    /**
     * 绑定服务的方式 启动服务
     * 过时不用
     * public static HttpGetService.HttpGetBinder httpGetBinder;
     *     public static ServiceConnection connection = new ServiceConnection() {
     *         @Override
     *         public void onServiceConnected(ComponentName name, IBinder service) {
     *             httpGetBinder = (HttpGetService.HttpGetBinder) service;
     *             httpGetBinder.startGet();
     *         }
     *
     *         @Override
     *         public void onServiceDisconnected(ComponentName name) {
     *
     *         }
     *     };
     */


}
