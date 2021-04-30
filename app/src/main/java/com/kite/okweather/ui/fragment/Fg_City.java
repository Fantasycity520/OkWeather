package com.kite.okweather.ui.fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.kite.okweather.R;
import com.kite.okweather.beans.Db_Bean_City;
import com.kite.okweather.beans.Db_Bean_My_City_List;
import com.kite.okweather.beans.Db_City;
import com.kite.okweather.beans.Weather_Bean_City;
import com.kite.okweather.ui.activity.Main;
import com.kite.okweather.ui.adapter.CityAdapter;
import com.kite.okweather.utils.BaseFragment;
import com.kite.okweather.utils.HttpUtil;
import com.kite.okweather.utils.PermissionsChecker;
import com.kite.okweather.utils.Utils;
import com.xiasuhuei321.loadingdialog.manager.StyleManager;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fg_City extends BaseFragment {

    RecyclerView rv_vity;
    ImageView title_city_city, im_title_location, im_title_add;
    EditText ed_title;

    private PermissionsChecker mPermissionsChecker; // 权限检测器
    private final int RESULT_CODE_LOCATION = 0x001;

    //定位权限,获取app内常用权限
    String[] permsLocation = {"android.permission.READ_PHONE_STATE"
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
        rv_vity = view.findViewById(R.id.rv_vity);
        title_city_city = view.findViewById(R.id.title_city_city);
        ed_title = view.findViewById(R.id.ed_title);
        im_title_location = view.findViewById(R.id.im_title_location);
        im_title_location.setOnClickListener(this);
        im_title_add = view.findViewById(R.id.im_title_add);
        im_title_add.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        mPermissionsChecker = new PermissionsChecker(getActivity());
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(listener);

        List<Db_Bean_My_City_List> list = LitePal.findAll(Db_Bean_My_City_List.class);
        CityAdapter cityAdapter = new CityAdapter(list, (AppCompatActivity) getActivity());
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
                        HttpLocation(ed);
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
                Log.d(TAG, "onClick: " + "返回主界面");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new Fg_Main()).commit();
                break;
            case R.id.im_title_location:
                location();
                break;
            case R.id.im_title_add:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new Fg_City_Add()).commit();
                break;
            default:
                break;
        }
    }

    private void location() {
        StyleManager s = new StyleManager();
        //在这里调用方法设置s的属性
        s.Anim(false).repeatTime(0).contentSize(-1).intercept(true).speed(LoadingDialog.Speed.SPEED_TWO);
        ld = new LoadingDialog(getActivity());
        ld.setLoadingText("定位中")
                .setSuccessText("定位成功")//显示加载成功时的文字
                .setFailedText("定位失败")
                .show();
//        if (mPermissionsChecker.lacksPermissions(permsLocation)) {
//            ActivityCompat.requestPermissions(getActivity(), permsLocation, RESULT_CODE_LOCATION);
//        } else {
//
//
//        }
        //获取位置
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);//是否要地址
        option.setOpenGps(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        mLocClient.setLocOption(option);
        mLocClient.start();
        //Utils.toast("获取位置");
    }

    /***
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
                            if (d==0){
                                my_city_list.save();
                                List<Db_Bean_My_City_List> list = LitePal.findAll(Db_Bean_My_City_List.class);
                                CityAdapter cityAdapter = new CityAdapter(list, (AppCompatActivity) getActivity());
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

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
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

    /**
     * 搜索城市
     *
     * @param ed
     */
    LoadingDialog ld;

    private void HttpLocation(String ed) {
        //提示对话框
        StyleManager s = new StyleManager();
        //在这里调用方法设置s的属性
        s.Anim(false).repeatTime(0).contentSize(-1).intercept(true).speed(LoadingDialog.Speed.SPEED_TWO);
        ld = new LoadingDialog(getActivity());
        ld.setLoadingText("加载中")
                .setSuccessText("加载成功")//显示加载成功时的文字
                .setFailedText("加载失败")
                .show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s_location = HttpUtil.OkHttpRequest(HttpUtil.url_city, ed);
                Utils.log(s_location);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        Db_Bean_My_City_List list = new Db_Bean_My_City_List();
                        Db_City db_city = gson.fromJson(s_location, Db_City.class);
                        if (db_city.getCode().equals("200")) {
                            list.setCityId(db_city.getLocation().get(0).getId());
                            //省
                            list.setProvince(db_city.getLocation().get(0).getAdm1());
                            //市
                            list.setCity(db_city.getLocation().get(0).getAdm2());
                            //区
                            list.setDistrict(db_city.getLocation().get(0).getName());

                            list.save();
                            initData();
                            ld.loadSuccess();
                        } else {
                            ld.loadFailed();
                        }
                    }
                });
            }
        }).start();
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
}
