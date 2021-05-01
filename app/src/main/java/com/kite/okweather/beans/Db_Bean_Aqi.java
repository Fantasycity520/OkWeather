package com.kite.okweather.beans;

import org.litepal.crud.LitePalSupport;

public class Db_Bean_Aqi extends LitePalSupport {

    int id;
    String aqi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    @Override
    public String toString() {
        return "Db_Bean_Aqi{" +
                "id=" + id +
                ", aqi='" + aqi + '\'' +
                '}';
    }
}
