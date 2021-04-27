package com.kite.okweather.utils;

import android.app.usage.UsageStats;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Utils {

    static Context context = BaseActivity.context;
    private static final String TAG = "Utils---->";

    public static void log(String str) {
        Log.d(TAG + "\t" + context.getPackageName() + "\t", "log_d:\n: " + str);
    }

    public static void log(int i) {
        Log.d(TAG + "\t" + context.getPackageName() + "\t", "log_d:\n: " + i);
    }

    public static void log(long l) {
        Log.d(TAG + "\t" + context.getPackageName() + "\t", "log_d:\n: " + l);
    }

    public static void toast(String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public static void toast(int str) {
        Toast.makeText(context, str + "", Toast.LENGTH_SHORT).show();
    }

    public static void toast(long str) {
        Toast.makeText(context, str + "", Toast.LENGTH_SHORT).show();
    }

}
