package com.kite.okweather.ui.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kite.okweather.R;
import com.kite.okweather.utils.BaseFragment;
import com.kite.okweather.utils.Utils;

public class Fg_City extends BaseFragment {

    @Override
    protected int initLayout() {
        return R.layout.fg_city;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initSp() {

    }

    @Override
    protected void title() {
        LinearLayout ll_ed_title, ll_title_location;
        ll_ed_title = getActivity().findViewById(R.id.ll_ed_title);
        ll_ed_title.setVisibility(View.VISIBLE);
        ll_title_location = getActivity().findViewById(R.id.ll_title_location);
        ll_title_location.setVisibility(View.VISIBLE);

        ImageView title_city = getActivity().findViewById(R.id.title_city);
        title_city.setImageResource(R.drawable.title_return);
        title_city.setOnClickListener(this);

        ImageView title_location = getActivity().findViewById(R.id.title_location);
        title_location.setOnClickListener(this);

        EditText ed_title = getActivity().findViewById(R.id.ed_title);
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
            case R.id.title_city:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fg_main, new Fg_Main()).commit();
                break;
            case R.id.title_location:
                Utils.toast("定位");
                break;
            default:
                break;
        }
    }
}
