package com.aaa.bbb.ccc.weather.di.component;

import com.aaa.bbb.ccc.weather.di.module.DetailsWeatherForecastFragmentModule;
import com.aaa.bbb.ccc.weather.presentation.detailsWeatherForecastFragment.DetailsWeatherForecastFragment;
import com.aaa.bbb.ccc.weather.presentation.weatherListSceenFragment.presentation.presenter.WeatherListScreenPresenter;
import com.aaa.bbb.ccc.weather.presentation.weatherListSceenFragment.ui.WeatherListScreenFragment;

import dagger.Subcomponent;

@Subcomponent(modules = DetailsWeatherForecastFragmentModule.class)
public interface DetailsWeatherForecastFragmentComponent {
    void inject(DetailsWeatherForecastFragment fragment);
}
