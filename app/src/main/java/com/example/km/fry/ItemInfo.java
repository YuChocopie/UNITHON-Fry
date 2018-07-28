package com.example.km.fry;

public class ItemInfo {
    private String degree;
    private String humidity;
    private String hour;
    private String unhappy;
    private String uv;
    private String poison;

    public ItemInfo(String degree, String humidity, String hour, String unhappy, String uv, String poison) {
        this.degree = degree;
        this.humidity = humidity;
        this.hour = hour;
        this.unhappy = unhappy;
        this.uv = uv;
        this.poison = poison;
    }

    public String getDegree() {
        return this.degree;
    }
    public String getHumidity() {
        return this.humidity;
    }

    public String getHour() {
        return this.hour;
    }

    public String getUnhappy() {
        return this.unhappy;
    }

    public String getUv() {
        return this.uv;
    }

    public String getPoison() {
        return this.poison;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setUnhappy(String unhappy) {
        this.unhappy = unhappy;
    }

    public void setUv(String uv) {
        this.uv = uv;
    }

    public void setPoison(String poison) {
        this.poison = poison;
    }

}
