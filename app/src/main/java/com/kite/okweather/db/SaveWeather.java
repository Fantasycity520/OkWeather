package com.kite.okweather.db;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.kite.okweather.ui.activity.Main;
import com.kite.okweather.utils.BaseActivity;

import org.litepal.LitePal;

import java.util.List;

public class SaveWeather {

    private static final String TAG = "SaveWeather";
    String string = "";
    AppCompatActivity activity;

    int res = 0;


    public SaveWeather(String string) {
        this.string = string;
    }

    public SaveWeather(AppCompatActivity activity, String string) {
        this.string = string;
        this.activity = activity;
    }

    //    public synchronized void saveWeather_Now_Data() {
////        activity.unbindService(Fg_01.connection);
//        Log.d(TAG, "saveWeather_Now_Data: ");
//        List<Db_Bean_Now> db_bean_nowList = LitePal.findAll(Db_Bean_Now.class);
//        Db_Bean_Now now = new Db_Bean_Now();
//        if (db_bean_nowList.size() == 0) {
//            now.setId(0);
//            now.setNow(string);
//            now.save();
//        } else {
//            now.setId(0);
//            now.setNow(string);
//            now.updateAll("id = 1");
//        }
//    }
//
//    public synchronized void saveWeather_3Day_Data() {
//        Log.d(TAG, "saveWeather_3Day_Data: ");
//
//        List<Db_Bean_3Day> list = LitePal.findAll(Db_Bean_3Day.class);
//        Db_Bean_3Day bean_3Day = new Db_Bean_3Day();
//
//        if (list.size() == 0) {
//            bean_3Day.setDay3(string);
//            bean_3Day.setId(0);
//            bean_3Day.save();
//        } else {
//            bean_3Day.setDay3(string);
//            bean_3Day.setId(0);
//            bean_3Day.updateAll("id = 1");
//        }
//
//    }
//
//    public synchronized void saveWeather_7Day_Data() {
//        Log.d(TAG, "saveWeather_7Day_Data: ");
//
//        List<Db_Bean_7Day> list = LitePal.findAll(Db_Bean_7Day.class);
//        Db_Bean_7Day bean_7Day = new Db_Bean_7Day();
//
//        if (list.size() == 0) {
//            bean_7Day.setDay7(string);
//            bean_7Day.setId(0);
//            bean_7Day.save();
//        } else {
//            bean_7Day.setDay7(string);
//            bean_7Day.setId(0);
//            bean_7Day.updateAll("id = 1");
//        }
//
//    }
//
//    public synchronized void saveWeather_Hours_Data() {
//        Log.d(TAG, "saveWeather_Hours_Data: ");
//
//        List<Db_Bean_Hours> list = LitePal.findAll(Db_Bean_Hours.class);
//        Db_Bean_Hours dbBeanHours = new Db_Bean_Hours();
//
//        if (list.size() == 0) {
//            dbBeanHours.setHours(string);
//            dbBeanHours.setId(0);
//            dbBeanHours.save();
//        } else {
//            dbBeanHours.setHours(string);
//            dbBeanHours.setId(0);
//            dbBeanHours.updateAll("id = 1");
//        }
//    }
//
//    public synchronized void saveWeather_City_Data() {
//        Log.d(TAG, "saveWeather_City_Data: ");
//
//        List<Db_Bean_City> list = LitePal.findAll(Db_Bean_City.class);
//        Db_Bean_City dbBeanCity = new Db_Bean_City();
//
//        if (list.size() == 0) {
//            dbBeanCity.setCity(string);
//            dbBeanCity.setId(0);
//            dbBeanCity.save();
//        } else {
//            dbBeanCity.setCity(string);
//            dbBeanCity.setId(0);
//            dbBeanCity.updateAll("id = 1");
//        }
//    }
//    /**
//     * 保存生活指数
//     */
//    public synchronized void saveWeather_Live_Data() {
//        Log.d(TAG, "saveWeather_City_Data: ");
//
//        List<Db_Bean_Live> list = LitePal.findAll(Db_Bean_Live.class);
//        Db_Bean_Live dbBeanCity = new Db_Bean_Live();
//
//        if (list.size() == 0) {
//            dbBeanCity.setLive(string);
//            dbBeanCity.setId(0);
//            dbBeanCity.save();
//        } else {
//            dbBeanCity.setLive(string);
//            dbBeanCity.setId(0);
//            dbBeanCity.updateAll("id = 1");
//        }
//    }
//
//    /**
//     * 保存Aqi
//     */
//    public synchronized void saveWeather_Aqi_Data() {
//        Log.d(TAG, "saveWeather_City_Data: ");
//
//        List<Db_Bean_Aqi> list = LitePal.findAll(Db_Bean_Aqi.class);
//        Db_Bean_Aqi dbBeanCity = new Db_Bean_Aqi();
//
//        if (list.size() == 0) {
//            dbBeanCity.setAqi(string);
//            dbBeanCity.setId(0);
//            dbBeanCity.save();
//        } else {
//            dbBeanCity.setAqi(string);
//            dbBeanCity.setId(0);
//            dbBeanCity.updateAll("id = 1");
//        }
//
//        Broadcast();
//    }
//
    public static void Broadcast() {
        //发送广播
        BaseActivity.context.registerReceiver(Main.receiver, Main.filter);
        BaseActivity.context.sendBroadcast(new Intent("android.intent.action.MY_BROADCAST"));
    }

}
