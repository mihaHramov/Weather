package com.aaa.bbb.ccc.data.repository.intrf;

import com.aaa.bbb.ccc.model.SynopticForecast;

import rx.Observable;

public interface IWeatherForecastRepository {
    Observable<SynopticForecast> getWeatherForecast(String lat, String lon, String lang, String metric);
}
