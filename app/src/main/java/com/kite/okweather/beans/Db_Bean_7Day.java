package com.kite.okweather.beans;

import org.litepal.crud.LitePalSupport;

public class Db_Bean_7Day extends LitePalSupport {
    int id;
    String day7;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay7() {
        return day7;
    }

    public void setDay7(String day7) {
        this.day7 = day7;
    }
}
