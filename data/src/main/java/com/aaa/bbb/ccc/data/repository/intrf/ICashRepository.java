package com.aaa.bbb.ccc.data.repository.intrf;

import com.aaa.bbb.ccc.data.model.City;
import com.aaa.bbb.ccc.data.model.WeatherForecast;

import rx.Observable;

public interface ICashRepository {
    Observable<City> getCity(Integer id,String lang);
    void saveCity(City city);
    void saveWeatherForecast(WeatherForecast weatherForecast);

    Observable<WeatherForecast> getWeatherForecast(String lat, String lon, String lang, String metric);
}
