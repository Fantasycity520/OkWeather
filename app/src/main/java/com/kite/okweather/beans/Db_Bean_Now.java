package com.kite.okweather.beans;

import org.litepal.crud.LitePalSupport;

public class Db_Bean_Now extends LitePalSupport {
    int id;
    String now;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }
}
