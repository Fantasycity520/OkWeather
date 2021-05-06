package com.kite.okweather.services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kite.okweather.beans.Db_Bean_My_City_List;
import com.kite.okweather.beans.Db_Save_Data;
import com.kite.okweather.broadcast.HttpGetBroadcast;
import com.kite.okweather.db.SaveWeather;
import com.kite.okweather.ui.activity.Main;
import com.kite.okweather.utils.BaseActivity;
import com.kite.okweather.utils.HttpWeatherGet;
import com.kite.okweather.utils.Utils;

import org.litepal.LitePal;

import java.util.List;

import static android.content.ContentValues.TAG;


public class HttpGetService extends Service {


    private void getHttp(String location) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String httpGetNow = HttpWeatherGet.HttpGetNow(location);
                String httpGet3Day = HttpWeatherGet.HttpGet3Day(location);
                String httpGet7Day = HttpWeatherGet.HttpGet7Day(location);
                String httpGetCity = HttpWeatherGet.HttpGetCity(location);
                String httpGetHours = HttpWeatherGet.HttpGetHours(location);
                String httpGetAqi = HttpWeatherGet.HttpGetAqi(location);
                String httpGetLive = HttpWeatherGet.HttpGetLive(location);
                Db_Save_Data save_data = new Db_Save_Data();
                save_data.setNow(httpGetNow);
                save_data.setLive(httpGetLive);
                save_data.setAqi(httpGetAqi);
                save_data.setHours(httpGetHours);
                save_data.setCity(httpGetCity);
                save_data.setDay7(httpGet7Day);
                save_data.setDay3(httpGet3Day);
//                Log.d(TAG, "run: "+LitePal.findAll(Db_Bean_My_City_List.class).get(0).getCity());
//                Log.d(TAG, "run: "+location);
//                Log.d(TAG, "run: "+LitePal.findAll(Db_Bean_My_City_List.class).get(0).getCityId());
//                Log.d(TAG, "run: " + save_data.toString());
                List<Db_Save_Data> list = LitePal.findAll(Db_Save_Data.class);
                Log.d(TAG, "run: " + list.size());
                if (list.size() == 0) {
                    Log.d(TAG, "run: 数据不存在直接保存");
                    save_data.save();
                } else {
                    Log.d(TAG, "run: 数据存在即将替换");
                    save_data.updateAll("id = 1");
                }
                if (LitePal.findAll(Db_Save_Data.class).size() > 0) {
                    SaveWeather.Broadcast();
                }
                Log.d(TAG, "run: ------------");
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getHttp(intent.getStringExtra("city"));
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        Utils.log("Weather服务销毁");
        super.onDestroy();
    }


}