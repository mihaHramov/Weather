package com.aaa.bbb.ccc.weather.di.component;

import com.aaa.bbb.ccc.data.RepositoryModule;
import com.aaa.bbb.ccc.weather.di.module.AppModule;
import com.aaa.bbb.ccc.weather.di.module.NavigationModule;

import dagger.Component;

@Component(modules = {
        RepositoryModule.class,
        AppModule.class,
        NavigationModule.class})
public interface AppComponent {
    WeatherListFragmentComponent getWeatherListFragmentComponent();
    DetailsWeatherForecastFragmentComponent getDetailsWeatherForecastFragmentComponent();
    WeatherListActivityComponent getWeatherListActivityComponent();
}
