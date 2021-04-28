package com.kite.okweather.db;

import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.kite.okweather.beans.Db_Bean_3Day;
import com.kite.okweather.beans.Db_Bean_7Day;
import com.kite.okweather.beans.Db_Bean_City;
import com.kite.okweather.beans.Db_Bean_Hours;
import com.kite.okweather.beans.Db_Bean_Now;
import com.kite.okweather.broadcast.HttpGetBroadcast;
import com.kite.okweather.ui.fragment.Fg_01;
import com.kite.okweather.utils.BaseActivity;
import com.kite.okweather.utils.Utils;

import org.litepal.LitePal;

public class SaveWeather {

    private static final String TAG = "SaveWeather";
    String string = "";
    AppCompatActivity activity;

    public SaveWeather(String string) {
        this.string = string;
    }

    public SaveWeather(AppCompatActivity activity, String string) {
        this.string = string;
        this.activity = activity;
    }

    public void saveWeather_Now_Data() {
//        activity.unbindService(Fg_01.connection);
        Log.d(TAG, "saveWeather_Now_Data: ");
        LitePal.getDatabase();
        Db_Bean_Now now = new Db_Bean_Now();
        now.setId(0);
        now.setNow(string);
        now.save();
    }

    public void saveWeather_3Day_Data() {
        Log.d(TAG, "saveWeather_3Day_Data: ");
        LitePal.getDatabase();

        Db_Bean_3Day bean_3Day = new Db_Bean_3Day();
        bean_3Day.setDay3(string);
        bean_3Day.setId(0);
        bean_3Day.save();

    }

    public void saveWeather_7Day_Data() {
        Log.d(TAG, "saveWeather_7Day_Data: ");
        LitePal.getDatabase();
        Db_Bean_7Day bean_7Day = new Db_Bean_7Day();
        bean_7Day.setDay7(string);
        bean_7Day.setId(0);
        bean_7Day.save();
    }

    public void saveWeather_Hours_Data() {
        Log.d(TAG, "saveWeather_Hours_Data: ");
        LitePal.getDatabase();
        Db_Bean_Hours dbBeanHours = new Db_Bean_Hours();
        dbBeanHours.setHours(string);
        dbBeanHours.setId(0);
        dbBeanHours.save();
    }

    public void saveWeather_City_Data() {
        Log.d(TAG, "saveWeather_City_Data: ");
        LitePal.getDatabase();
        Db_Bean_City dbBeanCity = new Db_Bean_City();
        dbBeanCity.setCity(string);
        dbBeanCity.setId(0);
        dbBeanCity.save();
        Broadcast();
    }

    void Broadcast() {
        //发送广播
        HttpGetBroadcast receiver = new HttpGetBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.MY_BROADCAST");
        BaseActivity.context.registerReceiver(receiver, filter);
        BaseActivity.context.sendBroadcast(new Intent("android.intent.action.MY_BROADCAST"));
    }
}
