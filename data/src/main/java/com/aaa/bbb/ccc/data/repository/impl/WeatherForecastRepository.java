package com.aaa.bbb.ccc.data.repository.impl;

import android.util.Log;

import com.aaa.bbb.ccc.data.map.FromResponseToListOfDetailedWeatherForecast;
import com.aaa.bbb.ccc.data.model.DetailedWeatherForecast;
import com.aaa.bbb.ccc.data.model.WeatherForecast;
import com.aaa.bbb.ccc.data.model.weatherApi.City;
import com.aaa.bbb.ccc.data.model.weatherApi.WeatherResponce;
import com.aaa.bbb.ccc.data.network.OpenWeatherMapApi;
import com.aaa.bbb.ccc.data.network.TranslateApi;
import com.aaa.bbb.ccc.data.repository.intrf.IWeatherForecastRepository;
import com.google.gson.Gson;

import java.util.List;

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
        //weather forecast
        String wat = "{\"cod\":\"200\",\"message\":0,\"cnt\":2,\"list\":[{\"dt\":1576648800,\"main\":{\"temp\":4.05,\"feels_like\":0.65,\"temp_min\":4.05,\"temp_max\":4.05,\"pressure\":1023,\"sea_level\":1023,\"grnd_level\":1000,\"humidity\":94,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"переменная облачность\",\"icon\":\"03d\"}],\"clouds\":{\"all\":28},\"wind\":{\"speed\":2.75,\"deg\":228},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-12-18 06:00:00\"},{\"dt\":1576659600,\"main\":{\"temp\":7.53,\"feels_like\":4.02,\"temp_min\":7.53,\"temp_max\":7.53,\"pressure\":1022,\"sea_level\":1022,\"grnd_level\":1000,\"humidity\":80,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"облачно с прояснениями\",\"icon\":\"04d\"}],\"clouds\":{\"all\":74},\"wind\":{\"speed\":3.21,\"deg\":228},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-12-18 09:00:00\"}],\"city\":{\"id\":702320,\"name\":\"Makiyivka\",\"coord\":{\"lat\":48.0478,\"lon\":37.9258},\"country\":\"UA\",\"population\":15000,\"timezone\":7200,\"sunrise\":1576646011,\"sunset\":1576676151}}";
        Observable<WeatherForecast> weatherForecastObservable =
                Observable.just(new Gson().fromJson(wat, WeatherResponce.class))
//                mWeatherApi.getForecast(lat, lon, lang, metric)
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
