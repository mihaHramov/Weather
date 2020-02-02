package com.aaa.bbb.ccc.weather.di.component;

import com.aaa.bbb.ccc.weather.di.module.DetailsWeatherForecastFragmentModule;
import com.aaa.bbb.ccc.weather.presentation.forecast.details.fragment.DetailsWeatherForecastFragment;

import dagger.Subcomponent;

@Subcomponent(modules = DetailsWeatherForecastFragmentModule.class)
public interface DetailsWeatherForecastFragmentComponent {
    void inject(DetailsWeatherForecastFragment fragment);
}
