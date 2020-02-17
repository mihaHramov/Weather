package com.aaa.bbb.ccc.domain.interactor;

import com.aaa.bbb.ccc.model.SynopticForecast;

import rx.Observable;

public interface ICurrentWeatherForecastInteractor {
    Observable<SynopticForecast> getCurrentWeather();
}
