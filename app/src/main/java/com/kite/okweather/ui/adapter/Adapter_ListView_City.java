package com.kite.okweather.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kite.okweather.R;

import java.util.List;

public class Adapter_ListView_City extends ArrayAdapter<String> {

    private int id;

    List<String> list;

    public Adapter_ListView_City(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.list = objects;
        this.id = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String s = list.get(position);
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(getContext()).inflate(id, parent, false);
        TextView textView = view.findViewById(R.id.lv_item_city);
        textView.setText(s);
        return view;
    }

}
