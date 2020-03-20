package com.aaa.bbb.ccc.weather.presentation.forecast.list.activity;


import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndStrategy.class)
interface WeatherListView extends MvpView {
    void showWeatherForecast();
}
