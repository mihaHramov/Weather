package com.aaa.bbb.ccc.weather.di.component;

import com.aaa.bbb.ccc.weather.di.module.WeatherListFragmentModule;
import com.aaa.bbb.ccc.weather.presentation.weatherListSceenFragment.presentation.presenter.WeatherListScreenPresenter;
import com.aaa.bbb.ccc.weather.presentation.weatherListSceenFragment.ui.WeatherListScreenFragment;

import dagger.Subcomponent;

@Subcomponent(modules = WeatherListFragmentModule.class)
public interface WeatherListFragmentComponent {
    WeatherListScreenPresenter getPresenter();
    void inject(WeatherListScreenFragment weatherListScreenFragment);
}
