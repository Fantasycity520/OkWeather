package com.kite.okweather.ui.fragment;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kite.okweather.R;
import com.kite.okweather.ui.adapter.CityAdapter;
import com.kite.okweather.utils.BaseFragment;
import com.kite.okweather.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Fg_City extends BaseFragment {

    RecyclerView rv_vity;
    ImageView title_city_city, im_title_location;
    EditText ed_title;

    @Override
    public void onResume() {
        super.onResume();
        getFocus();
    }

    //监听返回
    private void getFocus() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // 监听到返回按钮点击事件
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new Fg_Main()).commit();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    protected int initLayout() {
        return R.layout.fg_city;
    }

    @Override
    protected void initView(View view) {
        rv_vity = view.findViewById(R.id.rv_vity);
        title_city_city = view.findViewById(R.id.title_city_city);
        ed_title = view.findViewById(R.id.ed_title);
        im_title_location = view.findViewById(R.id.im_title_location);
        im_title_location.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        List<String> list = new ArrayList<>();
        CityAdapter cityAdapter = new CityAdapter(list);
        rv_vity.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_vity.setAdapter(cityAdapter);
    }

    @Override
    protected void initSp() {

    }

    @Override
    protected void title() {
        BottomNavigationView main_bottom = getActivity().findViewById(R.id.main_bottom);
        main_bottom.setVisibility(View.GONE);

        title_city_city.setOnClickListener(this);
        ed_title.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    // 监听到回车键，会执行2次该方法。按下与松开
                    String ed = ed_title.getText().toString();
                    if (!ed.equals("")) {
                        Utils.toast(ed);
                        ed_title.setText("");
                    }
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_city_city:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new Fg_Main()).commit();
                break;
            case R.id.im_title_location:
                Utils.toast("定位");
                break;
            default:
                break;
        }
    }
}
