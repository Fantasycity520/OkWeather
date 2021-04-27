package com.kite.okweather.db;

/**
 * 县
 */
public class County {
    private int id;
    private String CountyName;
    private int WeatherId;
    private int CityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return CountyName;
    }

    public void setCountyName(String countyName) {
        CountyName = countyName;
    }

    public int getWeatherId() {
        return WeatherId;
    }

    public void setWeatherId(int weatherId) {
        WeatherId = weatherId;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }
}
