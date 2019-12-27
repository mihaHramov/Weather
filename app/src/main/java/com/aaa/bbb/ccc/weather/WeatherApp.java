package com.aaa.bbb.ccc.weather;

import android.app.Application;

import com.aaa.bbb.ccc.weather.di.component.AppComponent;
import com.aaa.bbb.ccc.weather.di.component.DaggerAppComponent;
import com.aaa.bbb.ccc.weather.di.component.DetailsWeatherForecastFragmentComponent;
import com.aaa.bbb.ccc.weather.di.component.WeatherListFragmentComponent;
import com.aaa.bbb.ccc.weather.di.module.AppModule;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;


public class WeatherApp extends Application {
    private AppComponent component;
    private static WeatherApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        instance = this;
    }

    public static WeatherApp getInstance() {
        return instance;
    }

    public WeatherListFragmentComponent getWeatherListFragmentModule() {
        return component.getWeatherListFragmentComponent();
    }

    public DetailsWeatherForecastFragmentComponent getDetailsWeatherForecastFragmentComponent() {
        return component.getDetailsWeatherForecastFragmentComponent();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }
}