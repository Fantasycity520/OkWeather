package com.kite.okweather.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kite.okweather.R;

import com.kite.okweather.beans.Db_Bean_3Day;
import com.kite.okweather.beans.Db_Bean_7Day;
import com.kite.okweather.beans.Db_Bean_Aqi;
import com.kite.okweather.beans.Db_Bean_City;
import com.kite.okweather.beans.Db_Bean_Hours;
import com.kite.okweather.beans.Db_Bean_Live;
import com.kite.okweather.beans.Db_Bean_My_City_List;
import com.kite.okweather.beans.Db_Bean_Now;
import com.kite.okweather.beans.Weather_Bean_3Day;
import com.kite.okweather.beans.Weather_Bean_7Day;
import com.kite.okweather.beans.Weather_Bean_Aqi;
import com.kite.okweather.beans.Weather_Bean_City;
import com.kite.okweather.beans.Weather_Bean_Hours;
import com.kite.okweather.beans.Weather_Bean_Live;
import com.kite.okweather.beans.Weather_Bean_Now;
import com.kite.okweather.services.HttpGetService;

import com.kite.okweather.utils.BaseActivity;
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
    List<Db_Bean_Now> db_bean_nows;
    List<Db_Bean_3Day> db_bean_3Days;
    List<Db_Bean_7Day> db_bean_7Days;
    List<Db_Bean_Hours> db_bean_hours;
    List<Db_Bean_City> db_bean_cities;
    List<Db_Bean_Aqi> db_Bean_Aqi;
    List<Db_Bean_Live> db_Bean_Live;

    static SwipeRefreshLayout SwipeRefreshLayout_01;
    Intent intent;
    Db_Bean_My_City_List db_bean_my_city;
    static TextView degree_text;
    static TextView weather_info_text;
    static LinearLayout forecast_layout;
    static TextView aqi_text;
    static TextView pm25_text;
    static TextView comfort_text;
    static TextView car_wash_text;
    static TextView sport_text;
    static NestedScrollView scro;

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
        scro = view.findViewById(R.id.scro);
        degree_text = view.findViewById(R.id.degree_text);
        weather_info_text = view.findViewById(R.id.weather_info_text);
        forecast_layout = view.findViewById(R.id.forecast_layout);
        aqi_text = view.findViewById(R.id.aqi_text);
        pm25_text = view.findViewById(R.id.pm25_text);
        comfort_text = view.findViewById(R.id.comfort_text);
        car_wash_text = view.findViewById(R.id.car_wash_text);
        sport_text = view.findViewById(R.id.sport_text);
        SwipeRefreshLayout_01 = view.findViewById(R.id.SwipeRefreshLayout_01);

        SwipeRefreshLayout_01.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                List<Db_Bean_My_City_List> lists = LitePal.findAll(Db_Bean_My_City_List.class);
                upDate(lists.get(0).getCityId());
                stopS();
            }
        });

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
    static View view_item;
    static TextView date_text, info_text, max_text, min_text;

    @SuppressLint("SetTextI18n")
    public void showData() {
        db_bean_nows = LitePal.findAll(Db_Bean_Now.class);
        db_bean_3Days = LitePal.findAll(Db_Bean_3Day.class);
        db_bean_7Days = LitePal.findAll(Db_Bean_7Day.class);
        db_bean_hours = LitePal.findAll(Db_Bean_Hours.class);
        db_bean_cities = LitePal.findAll(Db_Bean_City.class);
        db_Bean_Aqi = LitePal.findAll(Db_Bean_Aqi.class);
        db_Bean_Live = LitePal.findAll(Db_Bean_Live.class);

        Gson gson = new Gson();
        Weather_Bean_Now now = gson.fromJson(db_bean_nows.get(0).getNow(), Weather_Bean_Now.class);
        Weather_Bean_City city = gson.fromJson(db_bean_cities.get(0).getCity(), Weather_Bean_City.class);
        Weather_Bean_3Day day3 = gson.fromJson(db_bean_3Days.get(0).getDay3(), Weather_Bean_3Day.class);
        Weather_Bean_7Day day7 = gson.fromJson(db_bean_7Days.get(0).getDay7(), Weather_Bean_7Day.class);
        Weather_Bean_Hours hours = gson.fromJson(db_bean_hours.get(0).getHours(), Weather_Bean_Hours.class);
        Weather_Bean_Aqi aqi = gson.fromJson(db_Bean_Aqi.get(0).getAqi(), Weather_Bean_Aqi.class);
        Weather_Bean_Live live = gson.fromJson(db_Bean_Live.get(0).getLive(), Weather_Bean_Live.class);
        //第一块
        degree_text.setText(now.getNow().getTemp() + "℃");
        weather_info_text.setText(now.getNow().getText());
        //七天天气
        forecast_layout.removeAllViews();
        for (int i = 0; i < day7.getDaily().size(); i++) {
            view_item = LayoutInflater.from(BaseActivity.context).inflate(R.layout.fg_forecast_item, forecast_layout, false);
            date_text = view_item.findViewById(R.id.date_text);
            info_text = view_item.findViewById(R.id.info_text);
            max_text = view_item.findViewById(R.id.max_text);
            min_text = view_item.findViewById(R.id.min_text);
            date_text.setText(day7.getDaily().get(i).getFxDate());
            info_text.setText(day7.getDaily().get(i).getTextDay());
            max_text.setText(day7.getDaily().get(i).getTempMax());
            min_text.setText(day7.getDaily().get(i).getTempMin());
            forecast_layout.addView(view_item);
        }
        //空气质量
        aqi_text.setText(aqi.getNow().getAqi());
        pm25_text.setText(aqi.getNow().getPm2p5());

        //生活指数
        comfort_text.setText(live.getDaily().get(8).getCategory() + "\n" + live.getDaily().get(8).getText());
        car_wash_text.setText(live.getDaily().get(0).getName() + "\n" + live.getDaily().get(0).getText());
        sport_text.setText(live.getDaily().get(1).getName() + "\n" + live.getDaily().get(1).getText());
        Log.d(TAG, "showData: " + live.getDaily().get(0).getType());
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
        List<Db_Bean_My_City_List> city_list = LitePal.findAll(Db_Bean_My_City_List.class);
        if (city_list.size() != 0) {
            SwipeRefreshLayout_01.post(new Runnable() {
                @Override
                public void run() {
                    SwipeRefreshLayout_01.setRefreshing(true);
                    upDate(city_list.get(0).getCityId());
                    stopS();
                }
            });
        } else {
            SwipeRefreshLayout_01.post(new Runnable() {
                @Override
                public void run() {
                    SwipeRefreshLayout_01.setRefreshing(true);
                    upDate("北京");
                    stopS();
                }
            });
        }
    }

}
