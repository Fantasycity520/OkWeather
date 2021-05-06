package com.kite.okweather.ui_2.activity;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kite.okweather.R;
import com.kite.okweather.ui_2.base.BaseActivity_Ui_02;
import com.kite.okweather.utils.BaseActivity;
import com.kite.okweather.utils.Utils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.toast.XToast;

public class Activit_Ui_02_Main extends BaseActivity_Ui_02 {

    private static final String TAG = "";
    TitleBar titleBar;

    @Override
    protected int layoutResID() {
        return R.layout.activity_activit__ui_02__main;
    }

    @Override
    protected void initView() {


        titleBar = findViewById(R.id.titlebar);

        titleBar.setLeftClickListener(v -> XToast.success(BaseActivity_Ui_02.context, "点击返回").show())
                .addAction(new TitleBar.TextAction("更多") {
                    @SuppressLint("CheckResult")
                    @Override
                    public void performAction(View view) {
                        XToast.success(BaseActivity_Ui_02.context, "点击更多").show();
                    }
                });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initSp() {

    }

    @Override
    public void onClick(View v) {

    }
}