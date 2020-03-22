package com.aaa.bbb.ccc.weather.presentation.forecast.list.activity;


import com.aaa.bbb.ccc.model.ShortForecast;
import com.aaa.bbb.ccc.model.SynopticForecast;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndStrategy.class)
interface WeatherListView extends MvpView {
    void showPlace(String place);

    void showWeather(SynopticForecast synopticForecast);

    void showError(String message);

    void showWeatherForecastForToday(ShortForecast forecast);
}
