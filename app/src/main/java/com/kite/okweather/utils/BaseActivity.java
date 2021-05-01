package com.kite.okweather.utils;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    /*
            switch (v.getId()) {
            case :
                break;
            default:
                break;
            }
    */

    public static Context context;

    public void onBackPressed() {
        ActivityCollector.finishAll();
        onDestroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    //用受保护的（protected）onCreate方法
    //当一个类（子类）继承BeasActivity时需要重写onCreate里面的方法

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在这里面可以定义自己需要的方法了，定义的方法可以在子类里面实现，这就是基类，自定义Activity
        context = getApplicationContext();
        //填充布局，填充主方法的布局，可以创建方法在子类里获取布局，也可以直接在本类获取布局
        setContentView(layoutResID());  //创建方法在子类里获取布局
//        setContentView(R.layout.activity_main);  //直接在本类获取布局
        ActivityCollector.addActivity(this);
        getSupportActionBar().hide();

        //以下是自己定义的方法，自己可以随便定义方法

        //获取并初始化视图或控件
        initView();

        //请求数据或获取数据
        initData();

        //onclick();

        initSp();
    }

    /**
     * 初始化布局
     *
     * @return 布局id
     */
    protected abstract int layoutResID();


    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化、绑定数据
     */
    protected abstract void initData();

    /**
     * 初始化、SharedPreferences
     */
    protected abstract void initSp();

}
