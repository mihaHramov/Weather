package com.aaa.bbb.ccc.weather.domain.interactor;

import com.aaa.bbb.ccc.weather.domain.model.SynopticForecast;

import rx.Observable;

public interface ICurrentWeatherForecastInteractor {
    Observable<SynopticForecast> getCurrentWeather();
}
