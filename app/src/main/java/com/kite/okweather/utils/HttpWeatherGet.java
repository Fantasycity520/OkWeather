package com.kite.okweather.utils;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.kite.okweather.beans.Db_Bean_My_City_List;
import com.kite.okweather.beans.Weather_Bean_3Day;
import com.kite.okweather.beans.Weather_Bean_7Day;
import com.kite.okweather.beans.Weather_Bean_Aqi;
import com.kite.okweather.beans.Weather_Bean_City;
import com.kite.okweather.beans.Weather_Bean_Hours;
import com.kite.okweather.beans.Weather_Bean_Live;
import com.kite.okweather.beans.Weather_Bean_Now;
import com.kite.okweather.db.SaveWeather;
import com.kite.okweather.ui.activity.Main;

import org.litepal.LitePal;

import java.util.List;

public class HttpWeatherGet {


    public static String HttpGetNow(String location) {
        return HttpUtil.OkHttpRequest(HttpUtil.url_now, location);
    }

    public static String HttpGet3Day(String location) {
        return HttpUtil.OkHttpRequest(HttpUtil.url_3Day, location);
    }

    public static String HttpGet7Day(String location) {
        return HttpUtil.OkHttpRequest(HttpUtil.url_7Day, location);
    }

    public static String HttpGetCity(String location) {
        return HttpUtil.OkHttpRequest(HttpUtil.url_city, location);
    }

    public static String HttpGetHours(String location) {
        return HttpUtil.OkHttpRequest(HttpUtil.url_hours, location);
    }

    /**
     * 生活指数
     */
    public static String HttpGetLive(String location) {
        return HttpUtil.OkHttpRequest(HttpUtil.url_live, location);
    }

    /**
     * aqi
     */
    public static String HttpGetAqi(String location) {
        return HttpUtil.OkHttpRequest(HttpUtil.url_aqi, location);
    }

    /**
     * 把当前点击的item 置顶
     *
     * @param position
     */
    public static void itemToTop(int position) {
        List<Db_Bean_My_City_List> lists = LitePal.findAll(Db_Bean_My_City_List.class);
        Db_Bean_My_City_List city_1 = lists.get(0);
        Db_Bean_My_City_List city_item = lists.get(position);

        city_item.updateAll("id = 1");
        city_1.updateAll("id = " + (position + 1));
    }
}
