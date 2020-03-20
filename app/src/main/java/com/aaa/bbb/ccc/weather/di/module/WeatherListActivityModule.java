package com.aaa.bbb.ccc.weather.di.module;

import com.aaa.bbb.ccc.data.repository.city.ICityRepository;
import com.aaa.bbb.ccc.data.repository.country.ICountryRepository;
import com.aaa.bbb.ccc.data.repository.date.IDateRepository;
import com.aaa.bbb.ccc.data.repository.forecast.IWeatherForecastRepository;
import com.aaa.bbb.ccc.data.repository.location.ILocationRepository;
import com.aaa.bbb.ccc.data.repository.permissions.IPermissionsRepository;
import com.aaa.bbb.ccc.data.repository.settings.ISettingsRepository;
import com.aaa.bbb.ccc.domain.interactor.CurrentWeatherForecastInteractor;
import com.aaa.bbb.ccc.domain.interactor.ICurrentWeatherForecastInteractor;
import com.aaa.bbb.ccc.weather.presentation.forecast.list.activity.WeatherListPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class WeatherListActivityModule {
    @Provides
    WeatherListPresenter presenter(ICurrentWeatherForecastInteractor interactor) {
        return new WeatherListPresenter(interactor);
    }

    @Provides
    ICurrentWeatherForecastInteractor interactor(IPermissionsRepository permissionsRepository,
                                                 IWeatherForecastRepository weatherForecastRepository,
                                                 ILocationRepository locationRepository,
                                                 ISettingsRepository settingsRepository,
                                                 ICityRepository cityRepository,
                                                 IDateRepository dateRepository,
                                                 ICountryRepository countryRepository) {
        return new CurrentWeatherForecastInteractor(permissionsRepository, weatherForecastRepository, locationRepository, settingsRepository, cityRepository, dateRepository, countryRepository);
    }
}
