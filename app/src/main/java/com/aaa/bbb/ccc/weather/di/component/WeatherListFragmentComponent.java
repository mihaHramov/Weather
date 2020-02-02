package com.aaa.bbb.ccc.weather.di.component;

import com.aaa.bbb.ccc.weather.di.module.WeatherListFragmentModule;
import com.aaa.bbb.ccc.weather.presentation.forecast.list.fragment.WeatherListScreenPresenter;
import com.aaa.bbb.ccc.weather.presentation.forecast.list.fragment.WeatherListScreenFragment;

import dagger.Subcomponent;

@Subcomponent(modules = WeatherListFragmentModule.class)
public interface WeatherListFragmentComponent {
    WeatherListScreenPresenter getPresenter();
    void inject(WeatherListScreenFragment weatherListScreenFragment);
}
