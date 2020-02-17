package com.aaa.bbb.ccc.weather.presentation.forecast.details.fragment;


import com.aaa.bbb.ccc.model.DailyForecast;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndStrategy.class)
public interface DetailsWeatherForecastView extends MvpView {

    void showWeatherForecast(DailyForecast dailyForecast);
}
