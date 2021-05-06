package com.kite.okweather.ui.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kite.okweather.R;
import com.kite.okweather.beans.Db_Bean_My_City_List;
import com.kite.okweather.beans.Db_City;
import com.kite.okweather.ui.activity.Main;
import com.kite.okweather.ui.fragment.Fg_Main;
import com.kite.okweather.utils.BaseActivity;
import com.kite.okweather.utils.Utils;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//CityAdapter

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private static final String TAG = "";
    static RecyclerView recyclerView;
    List<Db_Bean_My_City_List> list;
    AppCompatActivity activity;
    static int ii = 0;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView item_city_01, item_city_02;
        Button item_btu_de;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            item_city_01 = itemView.findViewById(R.id.item_city_01);
            item_city_02 = itemView.findViewById(R.id.item_city_02);
            item_btu_de = itemView.findViewById(R.id.item_btu_de);
        }
    }

    public CityAdapter(List<Db_Bean_My_City_List> list, AppCompatActivity activity, RecyclerView recyclerView) {
        this.list = list;
        this.activity = activity;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                itemToTop2(position);
                Main.ii++;
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new Fg_Main()).commit();
            }
        });

        viewHolder.item_btu_de.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                List<Db_Bean_My_City_List> lists = LitePal.findAll(Db_Bean_My_City_List.class);
                LitePal.deleteAll(Db_Bean_My_City_List.class, "district=?", lists.get(position).getDistrict());
                List<Db_Bean_My_City_List> list = LitePal.findAll(Db_Bean_My_City_List.class);
                CityAdapter cityAdapter = new CityAdapter(list, activity, recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                recyclerView.setAdapter(cityAdapter);
            }
        });
        return viewHolder;
    }

    /**
     * 把当前点击的item 置顶
     *
     * @param position
     */
    void itemToTop(int position) {
        if (position != 0) {
            Utils.log("执行置顶操作" + position + "-->" + "0");
            List<Db_Bean_My_City_List> list = LitePal.findAll(Db_Bean_My_City_List.class);
            for (int i = 0; i < list.size(); i++) {
                Utils.log(list.get(i).getCity());
            }
            Collections.swap(list, 0, position);
            Utils.log("置顶算法完成---------");

            LitePal.deleteAll(Db_Bean_My_City_List.class);

            for (int i = 0; i < list.size(); i++) {
                Utils.log(list.get(i).getCity());
            }
            Utils.log("结果---------");
            for (int i = 0; i < LitePal.findAll(Db_Bean_My_City_List.class).size(); i++) {
                Utils.log(LitePal.findAll(Db_Bean_My_City_List.class).get(i).getCity());
            }
            LitePal.findAll(Db_Bean_My_City_List.class);


        } else {
            BaseActivity.activity.getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new Fg_Main()).commit();
        }
    }

    /**
     * 把当前点击的item 置顶
     *
     * @param position
     */
    public static void itemToTop2(int position) {
        if (position != 0) {

            List<Db_Bean_My_City_List> lists = LitePal.findAll(Db_Bean_My_City_List.class);
            Db_Bean_My_City_List city_1 = lists.get(0);
            Db_Bean_My_City_List city_item = lists.get(position);

            city_item.updateAll("city = ?", city_1.getCity());
            city_1.updateAll("id=?", lists.get(position).getId() + "");

            CityAdapter cityAdapter = new CityAdapter(LitePal.findAll(Db_Bean_My_City_List.class), (AppCompatActivity) BaseActivity.activity, recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(BaseActivity.context));
            recyclerView.setAdapter(cityAdapter);
        } else {
            BaseActivity.activity.getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new Fg_Main()).commit();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.item_city_01.setText(list.get(position).getDistrict());
        holder.item_city_02.setText(list.get(position).getProvince() + ":\t" + list.get(position).getCity() + ":\t" + list.get(position).getDistrict());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



}
