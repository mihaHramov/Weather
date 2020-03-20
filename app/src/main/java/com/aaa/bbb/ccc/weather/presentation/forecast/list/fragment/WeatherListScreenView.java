package com.aaa.bbb.ccc.weather.presentation.forecast.list.fragment;


import com.aaa.bbb.ccc.model.ShortForecast;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndStrategy.class)
public interface WeatherListScreenView extends MvpView {
    void showPlace(String place);

    void showWeather(List<ShortForecast> dailyForecast);

    void showError(String message);

    void showWeatherForecastForToday(ShortForecast forecast);
}
