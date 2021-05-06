package com.kite.okweather.ui.fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.kite.okweather.R;
import com.kite.okweather.beans.Db_Bean_City_List;
import com.kite.okweather.beans.Db_Bean_My_City_List;
import com.kite.okweather.beans.Db_City;
import com.kite.okweather.beans.Weather_Bean_City;
import com.kite.okweather.ui.activity.Main;
import com.kite.okweather.ui.adapter.CityAdapter;
import com.kite.okweather.utils.BaseActivity;
import com.kite.okweather.utils.BaseFragment;
import com.kite.okweather.utils.HttpUtil;
import com.kite.okweather.utils.PermissionsChecker;
import com.kite.okweather.utils.Utils;
import com.xiasuhuei321.loadingdialog.manager.StyleManager;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.BaseDialog;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Fg_City extends BaseFragment {

    LoadingDialog ld;

    static BaseDialog dialog;

    static RecyclerView rv_vity;
    ImageView title_city_city, im_title_location, im_title_add;
    TextView ed_title;

    static String s1 = "河北、山西、辽宁、吉林、黑龙江、江苏、浙江、安徽、福建、江西、山东、河南、湖北、湖南、广东、海南、四川、贵州、云南、陕西、甘肃、青海、台湾";
    static String[] strings;
    static List<Db_Bean_City_List> my_city_lists;

    private PermissionsChecker mPermissionsChecker; // 权限检测器
    private final int RESULT_CODE_LOCATION = 0x001;
    private Button btn_getPlace;
    //定位权限,获取app内常用权限
    static final String[] permsLocation = {"android.permission.READ_PHONE_STATE"
            , "android.permission.ACCESS_COARSE_LOCATION"
            , "android.permission.ACCESS_FINE_LOCATION"
            , "android.permission.READ_EXTERNAL_STORAGE"
            , "android.permission.WRITE_EXTERNAL_STORAGE"};
    LocationClient mLocClient;

    @Override
    public void onResume() {
        super.onResume();
        getFocus();
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
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void initView(View view) {

        animation(view);

        rv_vity = view.findViewById(R.id.rv_vity);
        title_city_city = view.findViewById(R.id.title_city_city);
        ed_title = view.findViewById(R.id.ed_title);
        im_title_location = view.findViewById(R.id.im_title_location);
        im_title_location.setOnClickListener(this);
        im_title_add = view.findViewById(R.id.im_title_add);
        im_title_add.setOnClickListener(this);

        ed_title.setText("城市管理");
    }

    //入场动画
    private void animation(View view) {
        ViewUtils.slideIn(view, 500, new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        }, ViewUtils.Direction.BOTTOM_TO_TOP);
    }

    @Override
    protected void initData() {
        dialog = WidgetUtils.getMiniLoadingDialog(getActivity(), "查询中");

        mPermissionsChecker = new PermissionsChecker(BaseActivity.context);
        mLocClient = new LocationClient(BaseActivity.context);
        mLocClient.registerLocationListener(listener);

        List<Db_Bean_My_City_List> list = LitePal.findAll(Db_Bean_My_City_List.class);
        CityAdapter cityAdapter = new CityAdapter(list, (AppCompatActivity) getActivity(), rv_vity);
        rv_vity.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_vity.setAdapter(cityAdapter);

        strings = s1.split("、");
        my_city_lists = Main.list;
    }


    @Override
    protected void initSp() {

    }

    @Override
    protected void title() {
        BottomNavigationView main_bottom = getActivity().findViewById(R.id.main_bottom);
        main_bottom.setVisibility(View.GONE);

        title_city_city.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_city_city:
                Log.d(TAG, "onClick: " + "返回主界面");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new Fg_Main()).commit();
                break;
            case R.id.im_title_location:
                location();
                break;
            case R.id.im_title_add:
                new MaterialDialog.Builder(getContext())
                        .title("城市列表")
                        .items(strings)
                        .itemsCallback((dialog, view, which, text) -> text_01(text + ""))
                        .show();
                //                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new Fg_City_Add()).commit();
                break;
            default:
                break;
        }
    }

    private void text_01(String s) {
        List<String> list = new ArrayList<>();
        List<Db_Bean_City_List> my_city_lists2 = new ArrayList<>();

        for (int i = 0; i < my_city_lists.size(); i++) {
            if (s.equals(my_city_lists.get(i).getProvince())) {
                Log.d(TAG, "text_01: " + my_city_lists.get(i).toString());
                my_city_lists2.add(my_city_lists.get(i));
                list.add(my_city_lists.get(i).getCity());
            }
        }
        new MaterialDialog.Builder(getContext())
                .title("城市列表")
                .items(removeStringListDupli(list))
                .itemsCallback((dialog, view, which, text) -> text_02(text + "", my_city_lists2))
                .show();
    }

    private void text_02(String s, List<Db_Bean_City_List> list) {
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (s.equals(list.get(i).getCity())) {
                Log.d(TAG, "text_02: " + list.get(i).getDistrict());
                list1.add(list.get(i).getDistrict());
            }
        }
        new MaterialDialog.Builder(getContext())
                .title("城市列表")
                .items(removeStringListDupli(list1))
                .itemsCallback((dialog, view, which, text) -> text_03(text))
                .show();
    }

    private void text_03(CharSequence text) {
        dialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Utils.log(text+"");
                saveCity(String.valueOf(text));
            }
        }).start();

    }

    /**
     * 去重
     *
     * @param stringList
     * @return
     */
    public List<String> removeStringListDupli(List<String> stringList) {
        Set<String> set = new LinkedHashSet<>();
        set.addAll(stringList);

        stringList.clear();

        stringList.addAll(set);
        return stringList;
    }

    /**
     * 定位
     */
    private void location() {
        StyleManager s = new StyleManager();
        //在这里调用方法设置s的属性
        s.Anim(false).repeatTime(0).contentSize(-1).intercept(true).speed(LoadingDialog.Speed.SPEED_TWO);
        ld = new LoadingDialog(getActivity());
        ld.setLoadingText("定位中")
                .setSuccessText("定位成功")//显示加载成功时的文字
                .setFailedText("定位失败")
                .show();

        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);//是否要地址
        option.setOpenGps(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        mLocClient.setLocOption(option);
        mLocClient.start();

    }

    /*
     * 定位结果回调，在此方法中处理定位结果
     */
    BDAbstractLocationListener listener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            Log.i("bdmap", "定位类型:" + bdLocation.getLocTypeDescription() + "\n"
                    + "纬度:" + bdLocation.getLatitude() + "\n"
                    + "经度:" + bdLocation.getLongitude() + "\n"
                    + "详细地址:" + bdLocation.getAddrStr() + "\n"
                    + "卫星数目" + bdLocation.getSatelliteNumber());

            Map<String, String> map = new HashMap<>();
            if (bdLocation.getAddrStr() == null) {
                ld.loadFailed();
            }
            map.put("定位类型", bdLocation.getLocTypeDescription());
            map.put("纬度", bdLocation.getLatitude() + "");
            map.put("经度", bdLocation.getLongitude() + "");
            map.put("详细地址", bdLocation.getAddrStr());
            map.put("卫星数目", bdLocation.getSatelliteNumber() + "");
            Boolean b;
            if (bdLocation.getAddrStr() == null) {
                b = false;
            } else {
                b = true;
            }
            SuccessLocation(map, b);
            mLocClient.stop();
            Utils.log(bdLocation.getAddrStr());
        }
    };

    /**
     * 定位成功
     *
     * @param map
     */
    String str_location;
    String xian;

    private void SuccessLocation(Map<String, String> map, Boolean b) {
        str_location = map.get("详细地址");
        String guo = str_location.split("省")[0].substring(0, 2);
        String sheng = str_location.split("省")[0].substring(2);
        String shi = str_location.split("省")[1].split("市")[0];
        xian = "";
        String qu = "";

        String strings_qu[] = str_location.split("省")[1].split("市")[1].split("区");
        String strings_xian[] = str_location.split("省")[1].split("市")[1].split("县");

        Utils.log(strings_qu.length);
        Utils.log(strings_xian.length);

        if (strings_qu.length > 1) {
            qu = strings_qu[0];
        }
        if (strings_xian.length > 1) {
            xian = strings_xian[0];
        }
        Utils.log(guo);
        Utils.log(sheng);
        Utils.log(shi);

        if (!xian.equals("")) {
            Utils.log(xian + "县");
        }
        if (!qu.equals("")) {
            Utils.log(qu + "区");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                String s_city = HttpUtil.OkHttpRequest(HttpUtil.url_city, xian);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (b) {
                            Weather_Bean_City city = new Gson().fromJson(s_city, Weather_Bean_City.class);
                            Db_Bean_My_City_List my_city_list = new Db_Bean_My_City_List();
                            my_city_list.setCityId(city.getLocation().get(0).getId());
                            my_city_list.setCity(city.getLocation().get(0).getAdm2());
                            my_city_list.setDistrict(city.getLocation().get(0).getName());
                            my_city_list.setProvince(city.getLocation().get(0).getAdm1());
                            my_city_list.setLongitude(city.getLocation().get(0).getLon());
                            my_city_list.setLatitude(city.getLocation().get(0).getLat());
                            List<Db_Bean_My_City_List> db_bean_my_city_lists = LitePal.findAll(Db_Bean_My_City_List.class);
                            int d = 0;
                            for (int i = 0; i < db_bean_my_city_lists.size(); i++) {
                                if (city.getLocation().get(0).getId().equals(db_bean_my_city_lists.get(i).getCityId())) {
                                    d++;
                                }
                            }
                            if (d == 0) {
                                my_city_list.save();
                                List<Db_Bean_My_City_List> list = LitePal.findAll(Db_Bean_My_City_List.class);
                                itemToTop2(list.size());
                                CityAdapter cityAdapter = new CityAdapter(list, (AppCompatActivity) getActivity(), rv_vity);
                                rv_vity.setLayoutManager(new LinearLayoutManager(getActivity()));
                                rv_vity.setAdapter(cityAdapter);

                            }
                            ld.loadSuccess();
                        } else {
                            ld.loadFailed();
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 保存城市
     *
     * @param s
     */
    void saveCity(String s) {
        String s_city = HttpUtil.OkHttpRequest(HttpUtil.url_city, s);
        List<Db_Bean_My_City_List> lists = LitePal.findAll(Db_Bean_My_City_List.class);
        Utils.log("增加前:" + lists.size());
        Db_Bean_My_City_List city = new Db_Bean_My_City_List();
        Db_City db_city = new Gson().fromJson(s_city, Db_City.class);
        city.setCityId(db_city.getLocation().get(0).getId());
        city.setProvince(db_city.getLocation().get(0).getAdm1());
        city.setCity(db_city.getLocation().get(0).getAdm2());
        city.setDistrict(db_city.getLocation().get(0).getName());
        city.setLatitude(db_city.getLocation().get(0).getLat());
        city.setLongitude(db_city.getLocation().get(0).getLon());
        int ii = 0;
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).getCity().equals(city.getCity())) {
                break;
            } else {
                city.save();
                itemToTop2(LitePal.findAll(Db_Bean_My_City_List.class).size());
                ii++;
                break;
            }
        }
        if (ii != 0) {
            List<Db_Bean_My_City_List> list = LitePal.findAll(Db_Bean_My_City_List.class);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    CityAdapter cityAdapter = new CityAdapter(list, (AppCompatActivity) getActivity(), rv_vity);
                    rv_vity.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv_vity.setAdapter(cityAdapter);
                }
            });
        }
        Log.d(TAG, "run: " + "" + city.getCityId());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.cancel();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permsLocation, int[] grantResults) {
        switch (permsRequestCode) {
            case RESULT_CODE_LOCATION:
                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (cameraAccepted) {
                    //权限通过后继续获取位置
                    location();
                } else {
                    //用户授权拒绝之后，友情提示一下
                    Toast.makeText(getActivity(), "请开启应用定位权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
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

    /**
     * 把当前点击的item 置顶
     *
     * @param position
     */
    public static void itemToTop2(int position) {

        Utils.log("增加后:" + position);

        List<Db_Bean_My_City_List> lists = LitePal.findAll(Db_Bean_My_City_List.class);
        Db_Bean_My_City_List city_1 = lists.get(0);
        Db_Bean_My_City_List city_item = lists.get(position - 1);

        city_item.updateAll("district = ?", city_1.getDistrict());
        city_1.updateAll("id=?", lists.get(position - 1).getId() + "");

        BaseActivity.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CityAdapter cityAdapter = new CityAdapter(LitePal.findAll(Db_Bean_My_City_List.class), (AppCompatActivity) BaseActivity.activity, rv_vity);
                rv_vity.setLayoutManager(new LinearLayoutManager(BaseActivity.context));
                rv_vity.setAdapter(cityAdapter);
                Main.ii = 1;
                BaseActivity.activity.getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new Fg_Main()).commit();
            }
        });
    }
}
