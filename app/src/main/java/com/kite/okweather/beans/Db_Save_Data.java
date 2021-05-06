package com.kite.okweather.beans;

import org.litepal.crud.LitePalSupport;

public class Db_Save_Data extends LitePalSupport {

    /**
     * 用于缓存 网络请求的数据 只会保存一条
     */

    int id;
    String now;
    String day3;
    String day7;
    String city;
    String hours;
    String aqi;
    String live;

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

    public String getDay3() {
        return day3;
    }

    public void setDay3(String day3) {
        this.day3 = day3;
    }

    public String getDay7() {
        return day7;
    }

    public void setDay7(String day7) {
        this.day7 = day7;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getLive() {
        return live;
    }

    public void setLive(String live) {
        this.live = live;
    }

    @Override
    public String toString() {
        return "Db_Save_Data{" +
                "id=" + id +
                ", now='" + now + '\'' +
                ", day3='" + day3 + '\'' +
                ", day7='" + day7 + '\'' +
                ", city='" + city + '\'' +
                ", hours='" + hours + '\'' +
                ", aqi='" + aqi + '\'' +
                ", live='" + live + '\'' +
                '}';
    }
}
