package com.aaa.bbb.ccc.weather.di.component;

import com.aaa.bbb.ccc.weather.di.module.WeatherListActivityModule;
import com.aaa.bbb.ccc.weather.presentation.forecast.list.activity.WeatherListPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = WeatherListActivityModule.class)
public interface WeatherListActivityComponent {
    WeatherListPresenter getPresenter();
}
