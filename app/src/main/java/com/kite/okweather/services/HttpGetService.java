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
    static String data = "";

    public class HttpGetBinder extends Binder {
        public void startGet(String data) {
            getHttp(data);
            Utils.log("MyService" + "star" + data);
        }

        public int getProgress() {
            Utils.log("MyService" + "getProgress");
            return 0;
        }

    }

    private void getHttp(String data) {
        HttpWeatherGet.HttpGetNow(new Main(), data);
        HttpWeatherGet.HttpGet3Day(new Main(), data);
        HttpWeatherGet.HttpGet7Day(new Main(), data);
        HttpWeatherGet.HttpGetCity(new Main(), data);
        HttpWeatherGet.HttpGetHours(new Main(), data);
        HttpWeatherGet.HttpGetAqi(new Main(), data);
        HttpWeatherGet.HttpGetLive(new Main(), data);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        data = intent.getStringExtra("city");
        getHttp(data);
        return super.onStartCommand(intent, flags, startId);
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