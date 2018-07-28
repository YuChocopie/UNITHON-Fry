package com.example.km.fry;

public class ItemInfo {
    private String degree;
    private String humidity;
    private String hour;

    public ItemInfo(String degree, String humidity, String hour) {
        this.degree = degree;
        this.humidity = humidity;
        this.hour = hour;
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

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
