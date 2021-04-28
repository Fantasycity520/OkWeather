package com.kite.okweather.utils;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {

    private static String key = "&key=a55b555d8d224385b1ca2ed3985a88ea";
    public static String url_now = "https://devapi.qweather.com/v7/weather/now?location=";
    public static String url_3Day = "https://devapi.qweather.com/v7/weather/3d?location=";
    public static String url_7Day = "https://devapi.qweather.com/v7/weather/7d?location=";
    public static String url_hours = "https://devapi.qweather.com/v7/weather/24h?location=";
    public static String url_city = "https://geoapi.qweather.com/v2/city/lookup?location=";

    //异步网络请求
    public static void sendOkHttpRequest(String address, Callback callBack) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callBack);
    }

    //同步网络请求
    public static String OkHttpRequest(String address, String location) {
        String s = "";
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(address + location + key).build();
            client.newCall(request).execute().code();
            return client.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
