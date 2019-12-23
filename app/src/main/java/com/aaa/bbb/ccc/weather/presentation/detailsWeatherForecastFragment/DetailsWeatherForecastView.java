package com.aaa.bbb.ccc.weather.presentation.detailsWeatherForecastFragment;


import com.aaa.bbb.ccc.weather.domain.model.DailyForecast;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndStrategy.class)
public interface DetailsWeatherForecastView extends MvpView {

    void showWeatherForecast(DailyForecast dailyForecast);
}
