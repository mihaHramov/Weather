package com.aaa.bbb.ccc.weather.presentation.detailsWeatherForecastFragment;

import android.os.Bundle;

import com.aaa.bbb.ccc.weather.domain.model.DailyForecast;

import moxy.InjectViewState;
import moxy.MvpPresenter;


@InjectViewState
class DetailsWeatherForecastPresenter extends MvpPresenter<DetailsWeatherForecastView> {

     void init(DailyForecast dailyForecast, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getViewState().showWeatherForecast(dailyForecast);
        }
    }
}
