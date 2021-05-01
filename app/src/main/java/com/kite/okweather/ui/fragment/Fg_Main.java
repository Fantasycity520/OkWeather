package com.kite.okweather.ui.fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kite.okweather.R;
import com.kite.okweather.beans.Db_Bean_My_City_List;
import com.kite.okweather.ui.adapter.Adapter_ViewPager2;
import com.kite.okweather.utils.ActivityCollector;
import com.kite.okweather.utils.BaseFragment;
import com.kite.okweather.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Fg_Main extends BaseFragment {

    BottomNavigationView main_bottom;
    ViewPager2 viewpager_main;
    ImageView title_city;
    Db_Bean_My_City_List db_bean_my_city;
    ImageView bing_pic_img;

    public Fg_Main(Db_Bean_My_City_List db_bean_my_city) {
        this.db_bean_my_city = db_bean_my_city;
    }

    public Fg_Main() {

    }

    @Override
    public void onResume() {
        super.onResume();
        getFocus();
    }


    static int i = 0;

    /*
     *  监听返回
     */
    private void getFocus() {

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // 监听到返回按钮点击事件
                    //ActivityCollector.finishAll();
                    if (i == 0) {
                        //Utils.toast("双击返回");
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                i = 0;
                            }
                        }, 1000);
                    }
                    if (i >= 1) {
                        Log.d(TAG, "onKey: " + i);
                        ActivityCollector.finishAll();
                    }
                    i++;
                    return true;
                }
                return false;
            }
        });
    }

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

        bing_pic_img = view.findViewById(R.id.bing_pic_img);
        bing_pic_img.setImageAlpha(240);
        Glide.with(getActivity()).load("http://195.133.53.243:8080/05_Stu/web/bi_main_01.png").into(bing_pic_img);

        //loadBingPic();
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


    //网络加载图片
    private void loadBingPic() {
        String reques_Bing = "https://www.talklee.com/api/bing/";
        String reques_Bing2 = "http://fly.atlinker.cn/api/bing/1920-cn.php";

        Glide.with(getActivity()).load(reques_Bing2).into(bing_pic_img);

    }

    /**
     * 初始化VIewPager
     * @param view
     */
    private void view_pager(View view) {
        viewpager_main = view.findViewById(R.id.viewpager_main);
        List<Fragment> list = new ArrayList<>();
        list.add(new Fg_01(db_bean_my_city));
        list.add(new Fg_02());
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
                    default:
                        break;
                }
                return false;
            }
        });
    }
}
