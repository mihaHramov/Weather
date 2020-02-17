package com.aaa.bbb.ccc.data.repository.impl;

import com.aaa.bbb.ccc.data.map.FromResponseToListOfDetailedWeatherForecast;
import com.aaa.bbb.ccc.data.map.FromWeatherResponseToWeatherForecast;
import com.aaa.bbb.ccc.model.SynopticForecast;
import com.aaa.bbb.ccc.data.network.OpenWeatherMapApi;
import com.aaa.bbb.ccc.data.repository.intrf.ICashRepository;
import com.aaa.bbb.ccc.data.repository.intrf.IWeatherForecastRepository;

import rx.Observable;

public class WeatherForecastRepository implements IWeatherForecastRepository {
    private OpenWeatherMapApi mWeatherApi;
    private ICashRepository mCashRepository;


    public WeatherForecastRepository(OpenWeatherMapApi mWeatherApi, ICashRepository cashRepository) {
        this.mWeatherApi = mWeatherApi;
        this.mCashRepository = cashRepository;
    }

    @Override
    public Observable<SynopticForecast> getWeatherForecast(String lat, String lon, String lang, String metric) {
        return mWeatherApi.getForecast(lat, lon, lang, metric)
                .flatMap(new FromResponseToListOfDetailedWeatherForecast(), new FromWeatherResponseToWeatherForecast())
                .doOnNext(weatherForecast -> mCashRepository.saveWeatherForecast(weatherForecast))
                .onErrorResumeNext(throwable -> mCashRepository.getWeatherForecast(lat, lon, lang, metric));
    }
}
