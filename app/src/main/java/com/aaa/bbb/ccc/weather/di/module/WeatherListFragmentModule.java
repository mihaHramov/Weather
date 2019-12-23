package com.aaa.bbb.ccc.weather.di.module;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.aaa.bbb.ccc.weather.data.repository.intrf.ILocationRepository;
import com.aaa.bbb.ccc.weather.data.repository.intrf.IPermissionsRepository;
import com.aaa.bbb.ccc.weather.data.repository.intrf.ISettingsRepository;
import com.aaa.bbb.ccc.weather.data.repository.intrf.IWeatherForecastRepository;
import com.aaa.bbb.ccc.weather.domain.interactor.CurrentWeatherForecastInteractor;
import com.aaa.bbb.ccc.weather.presentation.adapter.ShortForecastAdapter;
import com.aaa.bbb.ccc.weather.presentation.weatherListSceenFragment.presentation.presenter.WeatherListScreenPresenter;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Router;

@Module
public class WeatherListFragmentModule {
    @Provides
    public ShortForecastAdapter adapter() {
        return new ShortForecastAdapter();
    }

    @Provides
    LinearLayoutManager provideLinaer(Context context) {
        return new LinearLayoutManager(context);
    }

    @Provides
    public WeatherListScreenPresenter presenter(CurrentWeatherForecastInteractor interactor, Router router) {
        return new WeatherListScreenPresenter(interactor, router);
    }

    @Provides
    CurrentWeatherForecastInteractor provideInteractor(IPermissionsRepository permissionsRepository, IWeatherForecastRepository weatherForecastRepository, ILocationRepository locationRepository, ISettingsRepository settingsRepository) {
        return new CurrentWeatherForecastInteractor(permissionsRepository, weatherForecastRepository, locationRepository, settingsRepository);
    }
}
