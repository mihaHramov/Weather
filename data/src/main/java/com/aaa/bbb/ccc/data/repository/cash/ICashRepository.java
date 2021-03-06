package com.aaa.bbb.ccc.data.repository.cash;

import com.aaa.bbb.ccc.model.Place;
import com.aaa.bbb.ccc.model.SynopticForecast;

import rx.Observable;

public interface ICashRepository {
    Observable<Place> getCity(Integer id, String lang);
    void saveCity(Place place);
    void saveWeatherForecast(SynopticForecast synopticForecast);
    Observable<SynopticForecast> getWeatherForecast(String lat, String lon, Integer date);
}
