package com.aaa.bbb.ccc.weather.model;

import java.io.Serializable;
import java.util.List;

public class SynopticForecast implements Serializable {
    private String place;
    private List<DailyForecast> dailyForecast;

    public SynopticForecast(){}
    public SynopticForecast(String place, List<DailyForecast> dailyForecast) {
        this.place = place;
        this.dailyForecast = dailyForecast;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<DailyForecast> getDailyForecast() {
        return dailyForecast;
    }

    public void setDailyForecast(List<DailyForecast> dailyForecast) {
        this.dailyForecast = dailyForecast;
    }

}
