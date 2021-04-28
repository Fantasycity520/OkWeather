package com.kite.okweather.beans;

import org.litepal.crud.LitePalSupport;

public class Db_Bean_3Day extends LitePalSupport {
    int id;
    String day3;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay3() {
        return day3;
    }

    public void setDay3(String day3) {
        this.day3 = day3;
    }
}
