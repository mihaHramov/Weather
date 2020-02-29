package com.aaa.bbb.ccc.model;

import java.io.Serializable;
import java.util.List;

public class SynopticForecast implements Serializable {
    private Place place;
    private List<DailyForecast> dailyForecast;

    public SynopticForecast(Place place, List<DailyForecast> dailyForecast) {
        this.place = place;
        this.dailyForecast = dailyForecast;
    }

    public Place getPlace() {
        return place;
    }


    public List<DailyForecast> getDailyForecast() {
        return dailyForecast;
    }

    public void setDailyForecast(List<DailyForecast> dailyForecast) {
        this.dailyForecast = dailyForecast;
    }

    public SynopticForecast setPlace(Place city) {
        this.place = city;
        return this;
    }
}
