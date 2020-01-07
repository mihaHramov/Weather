package com.aaa.bbb.ccc.data.model;

import java.util.List;

public class WeatherForecast {
    private City locality;
    private List<DetailedWeatherForecast> forecasts;

    public WeatherForecast(City locality, List<DetailedWeatherForecast> forecasts) {
        this.locality = locality;
        this.forecasts = forecasts;
    }

    public City getLocality() {
        return locality;
    }

    public void setLocality(City locality) {
        this.locality = locality;
    }

    public List<DetailedWeatherForecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<DetailedWeatherForecast> forecasts) {
        this.forecasts = forecasts;
    }
}
