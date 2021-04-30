package com.kite.okweather.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.kite.okweather.ui.fragment.Fg_01;
import com.kite.okweather.utils.Utils;

public class HttpGetBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Utils.toast("数据请求完成");
        new Fg_01().showData();
    }
}
