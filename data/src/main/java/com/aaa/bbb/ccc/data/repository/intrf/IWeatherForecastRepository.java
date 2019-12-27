package com.aaa.bbb.ccc.data.repository.intrf;

import com.aaa.bbb.ccc.data.model.WeatherForecast;

import rx.Observable;

public interface IWeatherForecastRepository {
    Observable<WeatherForecast> getWeatherForecast(String lat, String lon, String lang, String metric);
}
