package com.kite.okweather.utils;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.kite.okweather.beans.Db_Bean_My_City_List;
import com.kite.okweather.beans.Weather_Bean_3Day;
import com.kite.okweather.beans.Weather_Bean_7Day;
import com.kite.okweather.beans.Weather_Bean_City;
import com.kite.okweather.beans.Weather_Bean_Hours;
import com.kite.okweather.beans.Weather_Bean_Now;
import com.kite.okweather.db.SaveWeather;
import com.kite.okweather.ui.activity.Main;

import org.litepal.LitePal;

import java.util.List;

public class HttpWeatherGet {


    public static void HttpGetNow(AppCompatActivity activity, String location) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String s = HttpUtil.OkHttpRequest(HttpUtil.url_now, location);
                Gson gson = new Gson();
                Weather_Bean_Now now = gson.fromJson(s, Weather_Bean_Now.class);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (now.getCode().equals("200")) {
                            //Utils.log("实时天气:\t" + now.getNow().toString());
                            //Utils.toast("成功");
                            new SaveWeather(new Main(), s).saveWeather_Now_Data();
                        } else {
                            Utils.log(now.getCode());
                            Utils.toast("失败");
                        }
                    }
                });
            }
        });
        thread.start();
    }

    public static void HttpGet3Day(AppCompatActivity activity, String location) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String s = HttpUtil.OkHttpRequest(HttpUtil.url_3Day, location);
                Gson gson = new Gson();
                Weather_Bean_3Day day = gson.fromJson(s, Weather_Bean_3Day.class);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (day.getCode().equals("200")) {
                            for (int i = 0; i < day.getDaily().size(); i++) {
                                //Utils.log("三天 第:" + (i + 1) + "天:\t" + day.getDaily().get(i).toString());
                            }
                            //Utils.toast("成功");
                            new SaveWeather(s).saveWeather_3Day_Data();
                        } else {
                            Utils.log(day.getCode());
                            Utils.toast("失败");
                        }
                    }
                });
            }
        });
        thread.start();
    }

    public static void HttpGet7Day(AppCompatActivity activity, String location) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String s = HttpUtil.OkHttpRequest(HttpUtil.url_7Day, location);
                Gson gson = new Gson();
                Weather_Bean_7Day day = gson.fromJson(s, Weather_Bean_7Day.class);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (day.getCode().equals("200")) {
                            for (int i = 0; i < day.getDaily().size(); i++) {
                                //Utils.log("七天 第:" + (i + 1) + "天:\t" + day.getDaily().get(i).toString());
                            }
                            // Utils.toast("成功");
                            new SaveWeather(s).saveWeather_7Day_Data();
                        } else {
                            Utils.log(day.getCode());
                            Utils.toast("失败");
                        }
                    }
                });
            }
        });
        thread.start();
    }

    public static void HttpGetCity(AppCompatActivity activity, String location) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String s = HttpUtil.OkHttpRequest(HttpUtil.url_city, location);
                Gson gson = new Gson();
                Weather_Bean_City day = gson.fromJson(s, Weather_Bean_City.class);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (day.getCode().equals("200")) {
                            //Utils.log("获取城市:" + "\t" + day.getLocation().toString());
                            //Utils.toast("成功");
                            new SaveWeather(s).saveWeather_City_Data();
                        } else {
                            Utils.log(day.getCode());
                            Utils.toast("失败");
                        }
                    }
                });
            }
        });
        thread.start();
    }

    public static void HttpGetHours(AppCompatActivity activity, String location) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String s = HttpUtil.OkHttpRequest(HttpUtil.url_hours, location);
                Gson gson = new Gson();
                Weather_Bean_Hours day = gson.fromJson(s, Weather_Bean_Hours.class);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (day.getCode().equals("200")) {
                            for (int i = 0; i < day.getHourly().size(); i++) {
                                //Utils.log("逐小时天气 第:" + (i + 1) + "个" + "\t" + day.getHourly().toString());
                            }
                            //Utils.toast("成功");
                            new SaveWeather(s).saveWeather_Hours_Data();
                        } else {
                            Utils.log(day.getCode());
                            Utils.toast("失败");
                        }
                    }
                });
            }
        });
        thread.start();
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
