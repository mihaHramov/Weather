package com.aaa.bbb.ccc.data.repository.impl;

import com.aaa.bbb.ccc.data.map.FromCityApiToCity;
import com.aaa.bbb.ccc.data.map.FromResponseToListOfDetailedWeatherForecast;
import com.aaa.bbb.ccc.data.model.WeatherForecast;
import com.aaa.bbb.ccc.data.network.OpenWeatherMapApi;
import com.aaa.bbb.ccc.data.repository.intrf.ICashRepository;
import com.aaa.bbb.ccc.data.repository.intrf.IWeatherForecastRepository;

import rx.Observable;
import rx.functions.Func1;

public class WeatherForecastRepository implements IWeatherForecastRepository {
    private OpenWeatherMapApi mWeatherApi;
    private ICashRepository mCashRepository;


    public WeatherForecastRepository(OpenWeatherMapApi mWeatherApi, ICashRepository cashRepository) {
        this.mWeatherApi = mWeatherApi;
        this.mCashRepository = cashRepository;
    }

    @Override
    public Observable<WeatherForecast> getWeatherForecast(String lat, String lon, String lang, String metric) {
        return mWeatherApi.getForecast(lat, lon, lang, metric)
                .flatMap(new FromResponseToListOfDetailedWeatherForecast(),
                        (weatherResponse, detailedWeatherForecasts) ->
                                Observable.just(weatherResponse.getCity())
                                        .map(new FromCityApiToCity())
                                        .zipWith(Observable.just(detailedWeatherForecasts), WeatherForecast::new))
                .flatMap((Func1<Observable<WeatherForecast>, Observable<WeatherForecast>>)
                        weatherForecastObservable -> weatherForecastObservable)
                //save forecast
                .doOnNext(weatherForecast -> mCashRepository.saveWeatherForecast(weatherForecast))
                .onErrorResumeNext(mCashRepository.getWeatherForecast(lat, lon, lang, metric));
    }
}
