package com.kite.okweather.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.kite.okweather.R;

import com.kite.okweather.beans.Db_Bean_My_City_List;
import com.kite.okweather.beans.Db_Save_Data;
import com.kite.okweather.beans.Weather_Bean_3Day;
import com.kite.okweather.beans.Weather_Bean_7Day;
import com.kite.okweather.beans.Weather_Bean_Aqi;
import com.kite.okweather.beans.Weather_Bean_City;
import com.kite.okweather.beans.Weather_Bean_Hours;
import com.kite.okweather.beans.Weather_Bean_Live;
import com.kite.okweather.beans.Weather_Bean_Now;
import com.kite.okweather.services.HttpGetService;

import com.kite.okweather.ui.activity.Main;
import com.kite.okweather.utils.BaseActivity;
import com.kite.okweather.utils.BaseFragment;
import com.kite.okweather.utils.Utils;
import com.rainy.weahter_bg_plug.WeatherBg;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.BaseDialog;
import com.xuexiang.xui.widget.dialog.MiniLoadingDialog;

import org.litepal.LitePal;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Fg_01 extends BaseFragment {

    //背景
    static WeatherBg bg_main;

    TextView tv_fg_main_title;

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

    static BaseDialog dialog;
    View ViewRoot;

    public Fg_01() {

    }

    public Fg_01(Db_Bean_My_City_List db_bean_my_city, View view) {
        this.db_bean_my_city = db_bean_my_city;
        this.ViewRoot = view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (Main.ii != 0) {
            SwipeRefreshLayout_01.post(new Runnable() {
                @Override
                public void run() {
                    SwipeRefreshLayout_01.setRefreshing(true);
                    upDate(LitePal.findAll(Db_Bean_My_City_List.class).get(0).getCityId());
                    stopS();
                }
            });
            dialog.show();
        }

        Utils.log(Main.ii);
    }

    @Override
    protected int initLayout() {
        return R.layout.fg_01;
    }

    @Override
    protected void initView(View view) {

        bg_main = getActivity().findViewById(R.id.bg_main);

        dialog = WidgetUtils.getMiniLoadingDialog(getActivity());

        tv_fg_main_title = ViewRoot.findViewById(R.id.tv_fg_main_title);
        tv_fg_main_title.setText(LitePal.findAll(Db_Bean_My_City_List.class).get(0).getDistrict());

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
                dialog.show();
                upDate(lists.get(0).getCityId());
                stopS();
            }
        });
        upDateVoid();

    }

    static String s = "sunny";

    static void initBackground(String type) {
        if (type.equals("晴")) {
            s = "sunny";
        } else if (type.equals("暴雨")) {
            s = "heavyRainy";
        } else if (type.equals("暴雪")) {
            s = "heavySnow";
        } else if (type.equals("中雪")) {
            s = "middleSnow";
        } else if (type.equals("雷阵雨")) {
            s = "thunder";
        } else if (type.equals("小雨")) {
            s = "lightRainy";
        } else if (type.equals("小雪")) {
            s = "lightSnow";
        } else if (type.equals("多云")) {
            s = "cloudy";
        } else if (type.equals("晴")) {
            s = "sunnyNight";
        } else if (type.equals("多云")) {
            s = "cloudyNight";
        } else if (type.equals("中雨")) {
            s = "middleRainy";
        } else if (type.equals("薄雾")) {
            s = "hazy";
        } else if (type.equals("雾")) {
            s = "foggy";
        } else if (type.equals("霾")) {
            s = "overcast";
        } else if (type.equals("沙尘暴")) {
            s = "dusty";
        }
        bg_main.changeWeather(s);
        Utils.log(s);
    }

    /**
     * 停止网络请求服务
     */
    private void stopS() {
        BaseActivity.activity.stopService(intent);
    }

    /**
     * 启动服务 网络请求
     */
    private void upDate(String location) {
        intent = new Intent(getActivity(), HttpGetService.class);
        intent.putExtra("city", location);
        getActivity().startService(intent);
        Main.ii = 0;
    }

    /**
     * 网络请求成功 广播发回 处理ui逻辑
     */
    static View view_item;
    static TextView date_text, info_text, max_text, min_text;

    @SuppressLint("SetTextI18n")
    public void showData() {
        if (LitePal.findAll(Db_Save_Data.class).size() == 0) {
            SwipeRefreshLayout_01.setRefreshing(false);
            Utils.toast("出错");
        } else {
            showData_02();
            Utils.toast("成功");
        }
    }

    static Weather_Bean_Now now;
    static Weather_Bean_City city;
    static Weather_Bean_3Day day3;
    static Weather_Bean_7Day day7;
    static Weather_Bean_Hours hours;
    static Weather_Bean_Aqi aqi;
    static Weather_Bean_Live live;

    private void showData_02() {
        Gson gson = new Gson();
        now = gson.fromJson(LitePal.findAll(Db_Save_Data.class).get(0).getNow(), Weather_Bean_Now.class);
        city = gson.fromJson(LitePal.findAll(Db_Save_Data.class).get(0).getCity(), Weather_Bean_City.class);
        day3 = gson.fromJson(LitePal.findAll(Db_Save_Data.class).get(0).getDay3(), Weather_Bean_3Day.class);
        day7 = gson.fromJson(LitePal.findAll(Db_Save_Data.class).get(0).getDay7(), Weather_Bean_7Day.class);
        hours = gson.fromJson(LitePal.findAll(Db_Save_Data.class).get(0).getHours(), Weather_Bean_Hours.class);
        aqi = gson.fromJson(LitePal.findAll(Db_Save_Data.class).get(0).getAqi(), Weather_Bean_Aqi.class);
        live = gson.fromJson(LitePal.findAll(Db_Save_Data.class).get(0).getLive(), Weather_Bean_Live.class);

        initBackground(now.getNow().getText());

        Log.d(TAG, "showData_02: " + now.getNow().toString());
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
        SwipeRefreshLayout_01.setRefreshing(false);
        dialog.cancel();
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

    void upDateVoid() {
        if ((LitePal.findAll(Db_Save_Data.class).size() > 0)) {
            showData_02();
        } else {
            //initDataOnStart();
            SwipeRefreshLayout_01.post(new Runnable() {
                @Override
                public void run() {
                    SwipeRefreshLayout_01.setRefreshing(true);
                    upDate(LitePal.findAll(Db_Bean_My_City_List.class).get(0).getCityId());
                    stopS();
                }
            });
            dialog.show();
        }

    }
}
