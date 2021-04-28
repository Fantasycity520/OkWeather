package com.kite.okweather.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kite.okweather.R;
import com.kite.okweather.utils.Utils;

import java.util.ArrayList;
import java.util.List;
//CityAdapter

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    List<String> list;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_city_01, item_city_02;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_city_01 = itemView.findViewById(R.id.item_city_01);
            item_city_02 = itemView.findViewById(R.id.item_city_02);
        }
    }

    public CityAdapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.item_city_01.setText("City:\t" + position);
        holder.item_city_02.setText("中国 安徽 City:\t" + position);
    }

    @Override
    public int getItemCount() {
        return 30;
    }
}
