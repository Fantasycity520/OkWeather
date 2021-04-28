package com.kite.okweather.ui.fragment;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kite.okweather.R;
import com.kite.okweather.ui.adapter.Adapter_ViewPager2;
import com.kite.okweather.utils.BaseFragment;
import com.kite.okweather.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Fg_Main extends BaseFragment {

    BottomNavigationView main_bottom;
    ViewPager2 viewpager_main;
    ImageView title_city;

    @Override
    protected int initLayout() {
        return R.layout.fg_main;
    }

    @Override
    protected void initView(View view) {
        title_city = view.findViewById(R.id.title_city);
        title_city.setOnClickListener(this);
        view_pager(view);
        bottom();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initSp() {

    }

    @Override
    protected void title() {
        BottomNavigationView main_bottom = getActivity().findViewById(R.id.main_bottom);
        main_bottom.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_city:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new Fg_City()).commit();
                break;
            default:
                break;
        }
    }

    private void view_pager(View view) {
        viewpager_main = view.findViewById(R.id.viewpager_main);
        List<Fragment> list = new ArrayList<>();
        list.add(new Fg_01());
        list.add(new Fg_02());
        list.add(new Fg_03());
        list.add(new Fg_04());
        Adapter_ViewPager2 adapter_viewPager2 = new Adapter_ViewPager2(getActivity().getSupportFragmentManager(), getLifecycle(), list);
        viewpager_main.setAdapter(adapter_viewPager2);
        // ViewPager2 滑动事件监听
        viewpager_main.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                main_bottom.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    void bottom() {
        main_bottom = getActivity().findViewById(R.id.main_bottom);
        // 重新点击监听(即点击目前选中的Tab时触发)
        main_bottom.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                Utils.log("Reselected Item:" + item.getTitle());
            }
        });
        main_bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.tab_one:
                        viewpager_main.setCurrentItem(0);
                        main_bottom.getMenu().getItem(0).setChecked(true);
                        break;
                    case R.id.tab_two:
                        viewpager_main.setCurrentItem(1);
                        main_bottom.getMenu().getItem(1).setChecked(true);
                        break;
                    case R.id.tab_three:
                        viewpager_main.setCurrentItem(2);
                        main_bottom.getMenu().getItem(2).setChecked(true);
                        break;
                    case R.id.tab_four:
                        viewpager_main.setCurrentItem(3);
                        main_bottom.getMenu().getItem(3).setChecked(true);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
}
