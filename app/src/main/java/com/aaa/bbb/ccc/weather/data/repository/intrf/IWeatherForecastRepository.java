package com.aaa.bbb.ccc.weather.data.repository.intrf;

import com.aaa.bbb.ccc.weather.data.model.WeatherForecast;

import rx.Observable;

public interface IWeatherForecastRepository {
    Observable<WeatherForecast> getWeatherForecast(String lat, String lon, String lang, String metric);
}
