package com.kite.okweather.beans;

import org.litepal.crud.LitePalSupport;

public class Db_Bean_City extends LitePalSupport {
    int id;
    String city;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Db_Bean_City{" +
                "id=" + id +
                ", city='" + city + '\'' +
                '}';
    }
}
