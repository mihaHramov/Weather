package com.aaa.bbb.ccc.data.repository.forecast;

import com.aaa.bbb.ccc.data.map.FromResponseToListOfDetailedWeatherForecast;
import com.aaa.bbb.ccc.data.map.FromWeatherResponseToWeatherForecast;
import com.aaa.bbb.ccc.data.network.OpenWeatherMapApi;
import com.aaa.bbb.ccc.data.repository.cash.ICashRepository;
import com.aaa.bbb.ccc.model.SynopticForecast;

import rx.Observable;

public class WeatherForecastRepository implements IWeatherForecastRepository {
    private OpenWeatherMapApi mWeatherApi;
    private ICashRepository mCashRepository;


    public WeatherForecastRepository(OpenWeatherMapApi mWeatherApi, ICashRepository cashRepository) {
        this.mWeatherApi = mWeatherApi;
        this.mCashRepository = cashRepository;
    }

    @Override
    public Observable<SynopticForecast> getWeatherForecast(String lat, String lon, String lang, Integer date, String metric) {
        Observable<SynopticForecast> dbResult = Observable.combineLatest(Observable.just(lat), Observable.just(lon),
                Observable.just(date), (s, s2, integer) -> mCashRepository.getWeatherForecast(s, s2, integer))
                .flatMap(synopticForecastObservable -> synopticForecastObservable);

        return mWeatherApi.getForecast(lat, lon, lang, metric)
                .flatMap(new FromResponseToListOfDetailedWeatherForecast(), new FromWeatherResponseToWeatherForecast())
                .doOnNext(weatherForecast -> mCashRepository.saveWeatherForecast(weatherForecast))
                .onErrorResumeNext(throwable -> dbResult);
    }
}
