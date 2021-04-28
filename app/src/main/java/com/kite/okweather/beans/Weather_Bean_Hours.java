package com.kite.okweather.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather_Bean_Hours {

    @SerializedName("code")
    private String code;
    @SerializedName("updateTime")
    private String updateTime;
    @SerializedName("fxLink")
    private String fxLink;
    @SerializedName("refer")
    private ReferDTO refer;
    @SerializedName("hourly")
    private List<HourlyDTO> hourly;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getFxLink() {
        return fxLink;
    }

    public void setFxLink(String fxLink) {
        this.fxLink = fxLink;
    }

    public ReferDTO getRefer() {
        return refer;
    }

    public void setRefer(ReferDTO refer) {
        this.refer = refer;
    }

    public List<HourlyDTO> getHourly() {
        return hourly;
    }

    public void setHourly(List<HourlyDTO> hourly) {
        this.hourly = hourly;
    }

    public static class ReferDTO {
        @SerializedName("sources")
        private List<String> sources;
        @SerializedName("license")
        private List<String> license;

        public List<String> getSources() {
            return sources;
        }

        public void setSources(List<String> sources) {
            this.sources = sources;
        }

        public List<String> getLicense() {
            return license;
        }

        public void setLicense(List<String> license) {
            this.license = license;
        }
    }

    public static class HourlyDTO {
        @SerializedName("fxTime")
        private String fxTime;
        @SerializedName("temp")
        private String temp;
        @SerializedName("icon")
        private String icon;
        @SerializedName("text")
        private String text;
        @SerializedName("wind360")
        private String wind360;
        @SerializedName("windDir")
        private String windDir;
        @SerializedName("windScale")
        private String windScale;
        @SerializedName("windSpeed")
        private String windSpeed;
        @SerializedName("humidity")
        private String humidity;
        @SerializedName("pop")
        private String pop;
        @SerializedName("precip")
        private String precip;
        @SerializedName("pressure")
        private String pressure;
        @SerializedName("cloud")
        private String cloud;
        @SerializedName("dew")
        private String dew;

        public String getFxTime() {
            return fxTime;
        }

        public void setFxTime(String fxTime) {
            this.fxTime = fxTime;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getWind360() {
            return wind360;
        }

        public void setWind360(String wind360) {
            this.wind360 = wind360;
        }

        public String getWindDir() {
            return windDir;
        }

        public void setWindDir(String windDir) {
            this.windDir = windDir;
        }

        public String getWindScale() {
            return windScale;
        }

        public void setWindScale(String windScale) {
            this.windScale = windScale;
        }

        public String getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(String windSpeed) {
            this.windSpeed = windSpeed;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getPop() {
            return pop;
        }

        public void setPop(String pop) {
            this.pop = pop;
        }

        public String getPrecip() {
            return precip;
        }

        public void setPrecip(String precip) {
            this.precip = precip;
        }

        public String getPressure() {
            return pressure;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }

        public String getCloud() {
            return cloud;
        }

        public void setCloud(String cloud) {
            this.cloud = cloud;
        }

        public String getDew() {
            return dew;
        }

        public void setDew(String dew) {
            this.dew = dew;
        }

        @Override
        public String toString() {
            return "HourlyDTO{" +
                    "fxTime='" + fxTime + '\'' +
                    ", temp='" + temp + '\'' +
                    ", icon='" + icon + '\'' +
                    ", text='" + text + '\'' +
                    ", wind360='" + wind360 + '\'' +
                    ", windDir='" + windDir + '\'' +
                    ", windScale='" + windScale + '\'' +
                    ", windSpeed='" + windSpeed + '\'' +
                    ", humidity='" + humidity + '\'' +
                    ", pop='" + pop + '\'' +
                    ", precip='" + precip + '\'' +
                    ", pressure='" + pressure + '\'' +
                    ", cloud='" + cloud + '\'' +
                    ", dew='" + dew + '\'' +
                    '}';
        }
    }
}
