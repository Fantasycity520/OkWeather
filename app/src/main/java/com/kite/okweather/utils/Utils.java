package com.kite.okweather.utils;

import android.app.usage.UsageStats;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.kite.okweather.beans.Db_Bean_City_List;
import com.kite.okweather.beans.Db_Bean_My_City_List;
import com.kite.okweather.ui.activity.Main;
import com.kite.okweather.ui.adapter.CityAdapter;

import org.json.JSONArray;
import org.litepal.LitePal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    static Context context = BaseActivity.activity.getApplicationContext();
    private static final String TAG = "Utils---->";

    public static void log(String str) {
        Log.d(TAG + "\t" + context.getPackageName() + "\t", "log_d:\n: " + str);
    }

    public static void log(int i) {
        Log.d(TAG + "\t" + context.getPackageName() + "\t", "log_d:\n: " + i);
    }

    public static void log(long l) {
        Log.d(TAG + "\t" + context.getPackageName() + "\t", "log_d:\n: " + l);
    }

    public static void toast(String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public static void toast(int str) {
        Toast.makeText(context, str + "", Toast.LENGTH_SHORT).show();
    }

    public static void toast(long str) {
        Toast.makeText(context, str + "", Toast.LENGTH_SHORT).show();
    }

    public static String readTextFromSDcard(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    /*
        初始化 所有城市列表
     */
    public static void initDb() {
        if (Main.list.size() == 0) {
            try {
                //LitePal.getDatabase();
                Db_Bean_City_List dbBeanCityList;
                InputStream is = BaseActivity.context.getAssets().open("city_list.txt");
                String text = Utils.readTextFromSDcard(is);
                Gson gson = new Gson();
                List<Db_Bean_City_List> lists = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(text);
                for (int i = 0; i < jsonArray.length(); i++) {
                    lists.add(gson.fromJson(jsonArray.get(i).toString(), Db_Bean_City_List.class));
                    dbBeanCityList = new Db_Bean_City_List();
                    dbBeanCityList.setCity(lists.get(i).getCity());
                    dbBeanCityList.setCityId(lists.get(i).getCityId());
                    dbBeanCityList.setProvince(lists.get(i).getProvince());
                    dbBeanCityList.setLongitude(lists.get(i).getLongitude());
                    dbBeanCityList.setLatitude(lists.get(i).getLatitude());
                    dbBeanCityList.setDistrict(lists.get(i).getDistrict());
                    dbBeanCityList.save();
                }
                Main.list = lists;
                initDb();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "initDb: " + "城市列表缓存过 不再缓存");
        }
    }

    /*
     * 初始化我的城市列表
     */

    public static void db_MyCityList() {
        if (Main.my_city_lists.size() == 0) {
            //初始化
            try {
                int res = LitePal.findAll(Db_Bean_My_City_List.class).size();
                if (res == 0) {
                    Db_Bean_My_City_List list = new Db_Bean_My_City_List();
                    list.setCityId("101010200");
                    list.setProvince("北京");
                    list.setCity("北京市");
                    list.setDistrict("海淀区");
                    list.setLatitude("39.90498");
                    list.setLongitude("116.40528");
                    list.save();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "initDb: " + "我的城市列表已存在 不再缓存");
        }
    }
}
