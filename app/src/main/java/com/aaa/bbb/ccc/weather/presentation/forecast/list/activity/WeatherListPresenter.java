package com.aaa.bbb.ccc.weather.presentation.forecast.list.activity;

import moxy.InjectViewState;
import moxy.MvpPresenter;


@InjectViewState
class WeatherListPresenter extends MvpPresenter<WeatherListView> {
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showWeatherForecast();
    }
}
