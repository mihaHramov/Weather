package com.aaa.bbb.ccc.data.repository.impl;

import com.aaa.bbb.ccc.data.map.FromResponseToListOfDetailedWeatherForecast;
import com.aaa.bbb.ccc.data.map.TranslateLanguageMap;
import com.aaa.bbb.ccc.data.model.WeatherForecast;
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
        Observable<String> obsLanguage = Observable.just(lang);
        return mWeatherApi.getForecast(lat, lon, lang, metric)
                .flatMap(new FromResponseToListOfDetailedWeatherForecast(),
                        (weatherResponse, detailedWeatherForecasts) -> new WeatherForecast(weatherResponse.getCity(), detailedWeatherForecasts))
                .flatMap((Func1<WeatherForecast, Observable<String>>) weatherForecast ->
                        Observable.just(weatherForecast.getLocality().getName())
                                .zipWith(obsLanguage, (name, language) -> getTranslateName(language, name))
                                .flatMap((Func1<Observable<String>, Observable<String>>) stringObservable -> stringObservable), (weatherForecast, s) -> {
                    weatherForecast.getLocality().setName(s);
                    return weatherForecast;
                });
    }

    private Observable<String> getTranslateName(String language, String name) {
        Observable<String> observableName = Observable.just(name);
        return Observable.just(language)
                .map(new TranslateLanguageMap(OpenWeatherMapApi.BASE_LANGUAGE))
                .zipWith(observableName, (lang, name1) ->
                        mTranslateApi.getTranslate(name1, lang)
                                .map(translateResponse -> translateResponse.getText().get(0)))
                .flatMap((Func1<Observable<String>, Observable<String>>) stringObservable -> stringObservable)
                .onErrorResumeNext(observableName);
    }
}
