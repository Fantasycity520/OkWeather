package com.kite.okweather.ui_2.activity;

import android.content.Intent;
import android.view.View;

import com.kite.okweather.R;
import com.kite.okweather.ui_2.base.BaseActivity_Ui_02;

public class Activity_Ui_02_Frist extends BaseActivity_Ui_02 {

    @Override
    protected int layoutResID() {
        return R.layout.activity__ui_02__frist;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        startActivity(new Intent(this, Activit_Ui_02_Main.class));
    }


    @Override
    protected void initSp() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}