package com.aaa.bbb.ccc.weather.di.module;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.aaa.bbb.ccc.data.repository.city.ICityRepository;
import com.aaa.bbb.ccc.data.repository.country.ICountryRepository;
import com.aaa.bbb.ccc.data.repository.date.IDateRepository;
import com.aaa.bbb.ccc.data.repository.forecast.IWeatherForecastRepository;
import com.aaa.bbb.ccc.data.repository.location.ILocationRepository;
import com.aaa.bbb.ccc.data.repository.permissions.IPermissionsRepository;
import com.aaa.bbb.ccc.data.repository.settings.ISettingsRepository;
import com.aaa.bbb.ccc.domain.interactor.CurrentWeatherForecastInteractor;
import com.aaa.bbb.ccc.domain.interactor.ICurrentWeatherForecastInteractor;
import com.aaa.bbb.ccc.weather.presentation.forecast.list.fragment.WeatherListScreenPresenter;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Router;

@Module
public class WeatherListFragmentModule {
    @Provides
    LinearLayoutManager provideLinear(Context context) {
        return new LinearLayoutManager(context);
    }

    @Provides
    WeatherListScreenPresenter presenter(Router router) {
        return new WeatherListScreenPresenter(router);
    }
}
