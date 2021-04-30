package com.kite.okweather.ui.fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kite.okweather.R;
import com.kite.okweather.beans.Db_Bean_City_List;
import com.kite.okweather.beans.Db_Bean_My_City_List;
import com.kite.okweather.beans.Weather_Bean_City;
import com.kite.okweather.ui.activity.Main;
import com.kite.okweather.ui.adapter.Adapter_ListView_City;
import com.kite.okweather.utils.BaseFragment;
import com.kite.okweather.utils.HttpUtil;
import com.kite.okweather.utils.Utils;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Fg_City_Add extends BaseFragment {

    ImageView title_city_list_return;
    ListView lv_add_city;
    Adapter_ListView_City adapter_listView_city;
    static int i_city = 0;

    //省以下
    List<Db_Bean_City_List> list_01;
    //市一下
    List<Db_Bean_City_List> list_02;

    @Override
    public void onStart() {
        super.onStart();
        i_city = 0;
    }

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
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new Fg_City()).commit();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected int initLayout() {
        return R.layout.fg_city_list;
    }

    @Override
    protected void initView(View view) {
        title_city_list_return = view.findViewById(R.id.title_city_list_return);
        title_city_list_return.setOnClickListener(this);
        lv_add_city = view.findViewById(R.id.lv_add_city);
    }

    List<String> l_s = new ArrayList<>();

    @Override
    protected void initData() {

        //省市县
        List<Db_Bean_City_List> list = Main.list;
        int s = 0;
        String s1 = "河北、山西、辽宁、吉林、黑龙江、江苏、浙江、安徽、福建、江西、山东、河南、湖北、湖南、广东、海南、四川、贵州、云南、陕西、甘肃、青海、台湾";
        String[] strings = s1.split("、");
        Utils.log(strings.length);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getProvince().equals(strings[0])) {
                //Utils.log(list.get(i).getCity() + "市:\t" + list.get(i).getDistrict() + "区/县");
            }
        }

        //省级名字
        List<String> l_01 = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            l_01.add(strings[i]);
        }

        adapter_listView_city = new Adapter_ListView_City(getActivity(), R.layout.item_listview_city, l_01);
        lv_add_city.setAdapter(adapter_listView_city);

        lv_add_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (i_city) {
                    case 0:
                        //处理省级业务
                        l_s = v_01(list, l_01, position);
                        Utils.log(l_s.size());
                        i_city++;
                        break;
                    case 1:
                        //市级业务
                        v_02(l_s.get(position), l_s, position);
                        i_city++;
                        break;
                    case 2:
                        //区级业务
                        TextView textView = view.findViewById(R.id.lv_item_city);
                        Utils.toast(String.valueOf(textView.getText()));


                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Db_Bean_My_City_List dbBeanMyCityList = new Db_Bean_My_City_List();
                                dbBeanMyCityList.setCity(String.valueOf(textView.getText()));

                                Gson gson = new Gson();
                                Weather_Bean_City city = gson.fromJson(HttpUtil.OkHttpRequest(HttpUtil.url_city, String.valueOf(textView.getText())), Weather_Bean_City.class);
                                Db_Bean_My_City_List list = new Db_Bean_My_City_List();
                                list.setCity(city.getLocation().get(0).getAdm2());
                                list.setCityId(city.getLocation().get(0).getId());

                                List<Db_Bean_My_City_List> db_bean_my_city_lists = LitePal.findAll(Db_Bean_My_City_List.class);
                                int d = 0;
                                for (int i = 0; i < db_bean_my_city_lists.size(); i++) {
                                    if (list.getCity().equals(db_bean_my_city_lists.get(i).getCity())) {
                                        d++;
                                    }
                                }
                                if (d==0){
                                    dbBeanMyCityList.setCityId(city.getLocation().get(0).getId());
                                    dbBeanMyCityList.setCity(city.getLocation().get(0).getAdm2());
                                    dbBeanMyCityList.setDistrict(city.getLocation().get(0).getName());
                                    dbBeanMyCityList.setProvince(city.getLocation().get(0).getAdm1());
                                    dbBeanMyCityList.save();
                                }
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new Fg_Main(dbBeanMyCityList)).commit();
                                    }
                                });
                            }
                        }).start();

                        i_city++;
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 省级业务
     *
     * @param list
     * @param l_01
     * @param position
     * @return
     */
    private List<String> v_01(List<Db_Bean_City_List> list, List<String> l_01, int position) {
        list_01 = new ArrayList<>();
        //市县
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getProvince().equals(l_01.get(position))) {
                list_01.add(list.get(i));
            }
        }
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i < list_01.size(); i++) {
            list2.add(list_01.get(i).getCity());
        }
        removeStringListDupli(list2);
        UpData(list_01);
        return list2;
    }

    private List<String> v_02(String s, List<String> lists, int position) {
        List<String> list_r = new ArrayList<>();
        list_02 = new ArrayList<>();
        for (int i = 0; i < list_01.size(); i++) {
            if (list_01.get(i).getCity().equals(s)) {
//                Utils.log(list_01.get(i).getDistrict());
                list_r.add(list_01.get(i).getDistrict());
                list_02.add(list_01.get(i));
            }
        }
        UpData2(list_02);
        return list_r;
    }

    @Override
    protected void initSp() {

    }

    @Override
    protected void title() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_city_list_return:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new Fg_City()).commit();
                break;
        }
    }

    void UpData(List<Db_Bean_City_List> lists) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            list.add(lists.get(i).getCity());
        }
        adapter_listView_city = new Adapter_ListView_City(getActivity(), R.layout.item_listview_city, removeStringListDupli(list));
        lv_add_city.setAdapter(adapter_listView_city);
    }

    void UpData2(List<Db_Bean_City_List> lists) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            list.add(lists.get(i).getDistrict());
        }
        adapter_listView_city = new Adapter_ListView_City(getActivity(), R.layout.item_listview_city, removeStringListDupli(list));
        lv_add_city.setAdapter(adapter_listView_city);
    }

    /**
     * 去重
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
}
