package com.aaa.bbb.ccc.weather.presentation.weatherListSceenFragment.presentation.view;


import com.aaa.bbb.ccc.weather.domain.model.Place;
import com.aaa.bbb.ccc.weather.domain.model.ShortForecast;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndStrategy.class)
public interface WeatherListScreenView extends MvpView {
    void showPlace(Place place);

    void showWeather(List<ShortForecast> dailyForecast);
}
