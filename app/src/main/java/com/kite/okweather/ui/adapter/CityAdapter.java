package com.kite.okweather.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.kite.okweather.R;
import com.kite.okweather.beans.Db_Bean_My_City_List;
import com.kite.okweather.beans.Db_City;
import com.kite.okweather.ui.fragment.Fg_Main;
import com.kite.okweather.utils.Utils;

import org.litepal.LitePal;

import java.util.List;
//CityAdapter

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    List<Db_Bean_My_City_List> list;
    AppCompatActivity activity;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView item_city_01, item_city_02;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            item_city_01 = itemView.findViewById(R.id.item_city_01);
            item_city_02 = itemView.findViewById(R.id.item_city_02);
        }
    }

    public CityAdapter(List<Db_Bean_My_City_List> list, AppCompatActivity activity) {
        this.list = list;
        this.activity = activity;
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
                Db_Bean_My_City_List bean_my_city_list = list.get(position);
                itemToTop(position);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new Fg_Main(bean_my_city_list)).commit();
            }
        });
        return viewHolder;
    }

    /**
     * 把当前点击的item 置顶
     *
     * @param position
     */
    private void itemToTop(int position) {
        List<Db_Bean_My_City_List> lists = LitePal.findAll(Db_Bean_My_City_List.class);
        Db_Bean_My_City_List city_1 = lists.get(0);
        Db_Bean_My_City_List city_item = lists.get(position);

        city_item.updateAll("id = 1");
        city_1.updateAll("id = " + (position + 1));
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
