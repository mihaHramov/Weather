package com.aaa.bbb.ccc.data.repository.impl;

import com.aaa.bbb.ccc.data.map.FromResponseToListOfDetailedWeatherForecast;
import com.aaa.bbb.ccc.data.model.WeatherForecast;
import com.aaa.bbb.ccc.data.model.weatherApi.WeatherResponse;
import com.aaa.bbb.ccc.data.network.OpenWeatherMapApi;
import com.aaa.bbb.ccc.data.network.TranslateApi;
import com.aaa.bbb.ccc.data.repository.intrf.IWeatherForecastRepository;

import rx.Observable;
import rx.functions.Func1;

public class WeatherForecastRepository implements IWeatherForecastRepository {
    private OpenWeatherMapApi mWeatherApi;
    private TranslateApi mTranslateApi;


    public WeatherForecastRepository(OpenWeatherMapApi mWeatherApi, TranslateApi translateApi) {
        this.mWeatherApi = mWeatherApi;
        this.mTranslateApi = translateApi;
    }

    @Override
    public Observable<WeatherForecast> getWeatherForecast(String lat, String lon, String lang, String metric) {
        //user language
        Observable<String> observableLanguage = Observable.just(lang);
        Observable<WeatherForecast> weatherForecastObservable = mWeatherApi.getForecast(lat, lon, lang, metric)
                .flatMap((Func1<WeatherResponse, Observable<WeatherForecast>>) weatherResponse ->
                        Observable.just(weatherResponse.getCity())
                                .zipWith(Observable.just(weatherResponse)
                                                .flatMap(new FromResponseToListOfDetailedWeatherForecast()),
                                        WeatherForecast::new)
                );

        observableLanguage
                .zipWith(weatherForecastObservable, (s, weatherForecast) ->
                        mTranslateApi
                                .getTranslate(weatherForecast.getLocality().getName(), s)
                                .map(translateResponse -> translateResponse.getText().get(0))
                )
                .flatMap((Func1<Observable<String>, Observable<String>>) stringObservable -> stringObservable)
                .onErrorResumeNext(weatherForecastObservable.map(weatherForecast -> weatherForecast.getLocality().getName()));

        return weatherForecastObservable.zipWith(observableLanguage, (weatherForecast, s) -> {
            weatherForecast.getLocality().setName(s);
            return weatherForecast;
        });
    }
}
