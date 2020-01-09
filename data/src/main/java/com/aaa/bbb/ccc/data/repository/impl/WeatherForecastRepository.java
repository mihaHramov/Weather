package com.aaa.bbb.ccc.data.repository.impl;

import androidx.core.util.Pair;

import com.aaa.bbb.ccc.data.map.FromCityApiToCity;
import com.aaa.bbb.ccc.data.map.FromResponseToListOfDetailedWeatherForecast;
import com.aaa.bbb.ccc.data.map.TranslateLanguageMap;
import com.aaa.bbb.ccc.data.model.City;
import com.aaa.bbb.ccc.data.model.WeatherForecast;
import com.aaa.bbb.ccc.data.model.translateApi.TranslateResponse;
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
                        (weatherResponse, detailedWeatherForecasts) -> Observable.just(weatherResponse.getCity())
                                .map(new FromCityApiToCity())
                                .zipWith(Observable.just(detailedWeatherForecasts), WeatherForecast::new))
                .flatMap((Func1<Observable<WeatherForecast>, Observable<WeatherForecast>>)
                        weatherForecastObservable -> weatherForecastObservable)
                .zipWith(obsLanguage, (weatherForecast, s) ->
                        Observable.just(weatherForecast)
                        .zipWith(translateCityName(weatherForecast.getLocality(), s), (weatherForecast1, city) -> {
                            weatherForecast1.setLocality(city);
                            return weatherForecast1;
                        }))
                .flatMap((Func1<Observable<WeatherForecast>, Observable<WeatherForecast>>) weatherForecastObservable -> weatherForecastObservable);
    }

    private Observable<City> translateCityName(City locality, String lang) {
        Observable<City> cityObservable = Observable.just(locality);
        Observable<String> language = Observable.just(lang);
        Observable<String> name = cityObservable.map(City::getName);
        Observable<String> oldLanguage = language.map(new TranslateLanguageMap(OpenWeatherMapApi.BASE_LANGUAGE));
        Observable<Pair<String,String>> defaultResult =  Observable.zip(name, Observable.just(OpenWeatherMapApi.BASE_LANGUAGE), Pair::new);

       return Observable.zip(name, oldLanguage, Pair::new)
                .flatMap((Func1<Pair<String, String>, Observable<String>>)//translate
                        stringStringPair ->
                                mTranslateApi.getTranslate(stringStringPair.first, stringStringPair.second)
                                        .flatMap((Func1<TranslateResponse, Observable<String>>)
                                                translateResponse -> Observable.from(translateResponse.getText()))
                                        .first(),
                        (stringStringPair, s) -> new Pair<>(s, stringStringPair.second)
                )
                .onErrorResumeNext(defaultResult).zipWith(cityObservable, (stringStringPair, city) -> {
                    city.setName(stringStringPair.first);
                    city.setLangName(stringStringPair.second);
                    return city;
                });

    }
}
