package com.aaa.bbb.ccc.weather.model;


import java.io.Serializable;
import java.util.List;

public class DailyForecast implements Serializable {
    private String date;
    private List<ShortForecast> shortForecasts;

    public List<ShortForecast> getShortForecasts() {
        return shortForecasts;
    }

    public void setShortForecasts(List<ShortForecast> shortForecasts) {
        this.shortForecasts = shortForecasts;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ShortForecast getPreview() {
        return shortForecasts.get(0);
    }
}
