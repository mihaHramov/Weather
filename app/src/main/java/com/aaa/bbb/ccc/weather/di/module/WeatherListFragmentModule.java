package com.aaa.bbb.ccc.weather.di.module;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.aaa.bbb.ccc.data.repository.intrf.ICityRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ILocationRepository;
import com.aaa.bbb.ccc.data.repository.intrf.IPermissionsRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ISchedulerRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ISettingsRepository;
import com.aaa.bbb.ccc.data.repository.intrf.IWeatherForecastRepository;
import com.aaa.bbb.ccc.domain.interactor.CurrentWeatherForecastInteractor;
import com.aaa.bbb.ccc.domain.interactor.ICurrentWeatherForecastInteractor;
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
    LinearLayoutManager provideLinear(Context context) {
        return new LinearLayoutManager(context);
    }

    @Provides
    public WeatherListScreenPresenter presenter(ICurrentWeatherForecastInteractor interactor, Router router,ISchedulerRepository mSchedulerRepository) {
        return new WeatherListScreenPresenter(mSchedulerRepository, interactor, router);
    }

    @Provides
    ICurrentWeatherForecastInteractor interactor(IPermissionsRepository permissionsRepository,
                                                 IWeatherForecastRepository weatherForecastRepository,
                                                 ILocationRepository locationRepository,
                                                 ISettingsRepository settingsRepository,
                                                 ISchedulerRepository schedulerRepository,
                                                 ICityRepository cityRepository){
        return new CurrentWeatherForecastInteractor(permissionsRepository,weatherForecastRepository,locationRepository,settingsRepository,schedulerRepository,cityRepository);
    }
}
