package com.aaa.bbb.ccc.data.repository.intrf;

import com.aaa.bbb.ccc.model.Place;
import com.aaa.bbb.ccc.model.SynopticForecast;

import rx.Observable;

public interface ICashRepository {
    Observable<Place> getCity(Integer id, String lang);
    void saveCity(Place place);
    void saveWeatherForecast(SynopticForecast synopticForecast);

    Observable<SynopticForecast> getWeatherForecast(String lat, String lon, String lang, String metric);
}
