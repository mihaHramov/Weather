package com.aaa.bbb.ccc.weather.di.module;

import android.content.Context;

import com.aaa.bbb.ccc.weather.data.network.OpenWeatherMapApi;
import com.aaa.bbb.ccc.weather.data.network.TranslateApi;
import com.aaa.bbb.ccc.weather.data.repository.intrf.ILocationRepository;
import com.aaa.bbb.ccc.weather.data.repository.intrf.IPermissionsRepository;
import com.aaa.bbb.ccc.weather.data.repository.intrf.ISettingsRepository;
import com.aaa.bbb.ccc.weather.data.repository.intrf.IWeatherForecastRepository;
import com.aaa.bbb.ccc.weather.data.repository.impl.LocationRepository;
import com.aaa.bbb.ccc.weather.data.repository.impl.PermissionsRepository;
import com.aaa.bbb.ccc.weather.data.repository.impl.SettingsRepository;
import com.aaa.bbb.ccc.weather.data.repository.impl.WeatherForecastRepository;
import com.tbruyelle.rxpermissions.RxPermissions;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Provides
    ISettingsRepository provideISettingsRepository(){
        return new SettingsRepository();
    }
    @Provides
    ILocationRepository provideLocationRepository() {
        return new LocationRepository();
    }

    @Provides
    IWeatherForecastRepository provideWeatherForecastRepository(OpenWeatherMapApi api, TranslateApi translateApi) {
        return new WeatherForecastRepository(api,translateApi);
    }

    @Provides
    RxPermissions provideRxPermissions(Context context) {
        return RxPermissions.getInstance(context);
    }

    @Provides
    IPermissionsRepository providePermissionsRepository(RxPermissions rxPermissions) {
        return new PermissionsRepository(rxPermissions);
    }
}
