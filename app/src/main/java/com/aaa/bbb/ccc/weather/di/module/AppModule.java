package com.aaa.bbb.ccc.weather.di.module;

import android.content.Context;


import com.aaa.bbb.ccc.weather.WeatherApp;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
private WeatherApp app;

    public AppModule(WeatherApp app) {
        this.app = app;
    }
    @Provides
    Context provideContext(){
       return app.getApplicationContext();
    }
}
