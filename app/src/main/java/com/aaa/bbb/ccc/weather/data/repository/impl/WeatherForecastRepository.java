package com.aaa.bbb.ccc.weather.data.repository.impl;

import android.util.Log;

import com.aaa.bbb.ccc.weather.data.map.FromResponseToListOfDetailedWeatherForecast;
import com.aaa.bbb.ccc.weather.data.model.DetailedWeatherForecast;
import com.aaa.bbb.ccc.weather.data.model.WeatherForecast;
import com.aaa.bbb.ccc.weather.data.model.weatherApi.City;
import com.aaa.bbb.ccc.weather.data.model.weatherApi.WeatherResponce;
import com.aaa.bbb.ccc.weather.data.network.OpenWeatherMapApi;
import com.aaa.bbb.ccc.weather.data.network.TranslateApi;
import com.aaa.bbb.ccc.weather.data.repository.intrf.IWeatherForecastRepository;
import com.google.gson.Gson;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
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
        //weather forecast
        Observable<WeatherForecast> weatherForecastObservable =
//                Observable.just(new Gson().fromJson(wat,Response.class))
                mWeatherApi.getForecast(lat, lon, lang, metric)
                .doOnNext(response -> Log.d("mihaHramov1", new Gson().toJson(response)))
                .flatMap(this::getWeatherForecast)
                .doOnNext(response -> Log.d("mihaHramov2", new Gson().toJson(response)));

        //get translate city name on user language
        observableLanguage
                .zipWith(weatherForecastObservable, (s, weatherForecast) ->
                        mTranslateApi
                                .getTranslate(weatherForecast.getLocality().getName(), s)
                                .map(translateResponse -> translateResponse.getText().get(0))
                )
                .doOnNext(response -> Log.d("mihaHramov3", new Gson().toJson(response)))
                .flatMap((Func1<Observable<String>, Observable<String>>) stringObservable -> stringObservable)
                .doOnNext(response -> Log.d("mihaHramov4", new Gson().toJson(response)))
                .onErrorResumeNext(weatherForecastObservable.map(weatherForecast -> weatherForecast.getLocality().getName()))
                .doOnNext(response -> Log.d("mihaHramov5", new Gson().toJson(response)));


        return weatherForecastObservable.zipWith(observableLanguage, (weatherForecast, s) -> {
            weatherForecast.getLocality().setName(s);
            return weatherForecast;
        });
    }

    private Observable<WeatherForecast> getWeatherForecast(WeatherResponce response) {
        Observable<City> localityObservable = Observable.just(response.getCity());
        Observable<List<DetailedWeatherForecast>> listDailyObservable = Observable.just(response)
                .flatMap(new FromResponseToListOfDetailedWeatherForecast());
        return localityObservable.zipWith(listDailyObservable, WeatherForecast::new);
    }
}
