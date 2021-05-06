package com.kite.okweather.ui.activity;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kite.okweather.R;
import com.kite.okweather.beans.Db_Bean_City_List;
import com.kite.okweather.beans.Db_Bean_My_City_List;
import com.kite.okweather.broadcast.HttpGetBroadcast;
import com.kite.okweather.ui.fragment.Fg_City;
import com.kite.okweather.ui.fragment.Fg_Main;
import com.kite.okweather.ui.fragment.Fg_Ui_login;
import com.kite.okweather.utils.BaseActivity;
import com.kite.okweather.utils.Utils;
import com.rainy.weahter_bg_plug.WeatherBg;
import com.rainy.weahter_bg_plug.utils.WeatherUtil;
import com.xuexiang.xui.utils.ViewUtils;

import org.litepal.LitePal;

import java.util.List;

@SuppressLint("NonConstantResourceId")
public class Main extends BaseActivity {
    //背景
    WeatherBg bg_main;

    //用于判断是否刷新 天气逻辑
    public static int ii = 0;
    BottomNavigationView main_bottom;

    public static List<Db_Bean_City_List> list;
    public static List<Db_Bean_My_City_List> my_city_lists;
    public static String s1 = "河北、山西、辽宁、吉林、黑龙江、江苏、浙江、安徽、福建、江西、山东、河南、湖北、湖南、广东、海南、四川、贵州、云南、陕西、甘肃、青海、台湾";

    public static HttpGetBroadcast receiver = new HttpGetBroadcast();
    public static IntentFilter filter = new IntentFilter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected int layoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        BaseActivity.activity = this;
        BaseActivity.context = getApplicationContext();
        bg_main = findViewById(R.id.bg_main);

        filter.addAction("android.intent.action.MY_BROADCAST");
        list = LitePal.findAll(Db_Bean_City_List.class);
        my_city_lists = LitePal.findAll(Db_Bean_My_City_List.class);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new Fg_Ui_login()).commit();

        main_bottom = findViewById(R.id.main_bottom);
        main_bottom.setBackgroundColor(Color.rgb(112, 128, 144));
    }

    @Override
    protected void initData() {
        Utils.initDb();
        Utils.db_MyCityList();
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