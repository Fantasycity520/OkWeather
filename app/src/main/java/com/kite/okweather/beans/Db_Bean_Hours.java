package com.kite.okweather.beans;

import org.litepal.crud.LitePalSupport;

public class Db_Bean_Hours extends LitePalSupport {
    int id;
    String hours;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }
}
