package com.kite.okweather.services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.kite.okweather.broadcast.HttpGetBroadcast;
import com.kite.okweather.ui.activity.Main;
import com.kite.okweather.utils.HttpWeatherGet;
import com.kite.okweather.utils.Utils;


public class HttpGetService extends Service {

    private HttpGetBinder mBinder = new HttpGetBinder();

    public class HttpGetBinder extends Binder {
        public void startGet() {
            getHttp();
            Utils.log("MyService" + "star");
        }

        public int getProgress() {
            Utils.log("MyService" + "getProgress");
            return 0;
        }
        public void d(){
            onDestroy();
        }
    }

    private void getHttp() {
        HttpWeatherGet.HttpGetNow(new Main(), "101010100");
        HttpWeatherGet.HttpGet3Day(new Main(), "101010100");
        HttpWeatherGet.HttpGet7Day(new Main(), "101010100");
        HttpWeatherGet.HttpGetCity(new Main(), "101010100");
        HttpWeatherGet.HttpGetHours(new Main(), "101010100");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
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