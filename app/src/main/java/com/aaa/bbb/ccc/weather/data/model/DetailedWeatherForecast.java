package com.aaa.bbb.ccc.weather.data.model;

import java.util.List;

public class DetailedWeatherForecast {
    private List<BriefWeatherForecast> briefWeatherForecasts;
    private Integer Date;

    public Integer getDate() {
        return Date;
    }

    public void setDate(Integer date) {
        Date = date;
    }

    public List<BriefWeatherForecast> getBriefWeatherForecasts() {
        return briefWeatherForecasts;
    }

    public void setBriefWeatherForecasts(List<BriefWeatherForecast> briefWeatherForecasts) {
        this.briefWeatherForecasts = briefWeatherForecasts;
    }
}
