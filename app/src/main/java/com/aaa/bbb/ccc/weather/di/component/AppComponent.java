package com.aaa.bbb.ccc.weather.di.component;

import com.aaa.bbb.ccc.weather.di.module.AppModule;
import com.aaa.bbb.ccc.weather.di.module.NavigationModule;
import com.aaa.bbb.ccc.weather.di.module.NetworkModule;
import com.aaa.bbb.ccc.weather.di.module.RepositoryModule;

import dagger.Component;

@Component(modules = {NetworkModule.class,
        RepositoryModule.class,
        AppModule.class,
        NavigationModule.class})
public interface AppComponent {
    WeatherListFragmentComponent getWeatherListFragmentComponent();
    DetailsWeatherForecastFragmentComponent getDetailsWeatherForecastFragmentComponent();
}
