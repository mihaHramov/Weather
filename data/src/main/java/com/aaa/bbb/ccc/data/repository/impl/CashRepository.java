package com.aaa.bbb.ccc.data.repository.impl;

import com.aaa.bbb.ccc.data.db.WeatherDatabase;
import com.aaa.bbb.ccc.data.db.entity.Forecast;
import com.aaa.bbb.ccc.data.map.FromCityEntryToCity;
import com.aaa.bbb.ccc.data.map.FromCityToCityEntity;
import com.aaa.bbb.ccc.data.map.FromWeatherForecastToForecastEntity;
import com.aaa.bbb.ccc.data.model.City;
import com.aaa.bbb.ccc.data.model.WeatherForecast;
import com.aaa.bbb.ccc.data.repository.intrf.ICashRepository;

import rx.Observable;

public class CashRepository implements ICashRepository {
    private WeatherDatabase mWeatherDataBase;

    public CashRepository(WeatherDatabase weatherDataBase) {
        this.mWeatherDataBase = weatherDataBase;
    }

    @Override
    public Observable<City> getCity(Integer id, String lang) {
        Observable<String> langObs = Observable.just(lang);
        Observable<Integer> idObs = Observable.just(id);
        return Observable.zip(idObs, langObs,
                ((integer, s) -> mWeatherDataBase.getCityDao().getByIdAndLanguage(integer, s)))
                .map(new FromCityEntryToCity());
    }

    @Override
    public void saveCity(City city) {
        FromCityToCityEntity map = new FromCityToCityEntity();
        mWeatherDataBase.getCityDao().insert(map.call(city));
    }

    @Override
    public void saveWeatherForecast(WeatherForecast weatherForecast) {
        FromWeatherForecastToForecastEntity map = new FromWeatherForecastToForecastEntity();
        for (Forecast forecast : map.call(weatherForecast)) {
            mWeatherDataBase.getForecastDao().insert(forecast);
        }
    }

    @Override
    public Observable<WeatherForecast> getWeatherForecast(String lat, String lon, String lang, String metric) {
        //1 get city by coord
        //2 get forecast to city
        //sort by date
        return null;
    }
}
