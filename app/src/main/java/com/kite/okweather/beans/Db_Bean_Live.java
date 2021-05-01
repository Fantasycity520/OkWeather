package com.kite.okweather.beans;

import org.litepal.crud.LitePalSupport;

public class Db_Bean_Live extends LitePalSupport {

    int id;
    String live;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLive() {
        return live;
    }

    public void setLive(String live) {
        this.live = live;
    }

    @Override
    public String toString() {
        return "Db_Bean_Live{" +
                "id=" + id +
                ", live='" + live + '\'' +
                '}';
    }
}
