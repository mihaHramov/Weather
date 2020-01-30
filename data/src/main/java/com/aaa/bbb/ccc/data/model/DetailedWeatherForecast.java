package com.aaa.bbb.ccc.data.model;

import java.util.List;

public class DetailedWeatherForecast {
    private List<BriefWeatherForecast> briefWeatherForecasts;
    private Integer date;

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public List<BriefWeatherForecast> getBriefWeatherForecasts() {
        return briefWeatherForecasts;
    }

    public void setBriefWeatherForecasts(List<BriefWeatherForecast> briefWeatherForecasts) {
        this.briefWeatherForecasts = briefWeatherForecasts;
    }
}
