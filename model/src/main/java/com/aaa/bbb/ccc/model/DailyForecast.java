package com.aaa.bbb.ccc.model;

import java.io.Serializable;
import java.util.List;

public class DailyForecast implements Serializable {
    private List<ShortForecast> shortForecasts;

    public List<ShortForecast> getShortForecasts() {
        return shortForecasts;
    }

    public void setShortForecasts(List<ShortForecast> shortForecasts) {
        this.shortForecasts = shortForecasts;
    }

    public DailyForecast(List<ShortForecast> shortForecasts) {
        this.shortForecasts = shortForecasts;
    }

    public ShortForecast getPreview() {
        return shortForecasts.get(0);
    }

}
