package com.aaa.bbb.ccc.domain.interactor;

import android.Manifest;

import com.aaa.bbb.ccc.data.repository.city.ICityRepository;
import com.aaa.bbb.ccc.data.repository.location.ILocationRepository;
import com.aaa.bbb.ccc.data.repository.permissions.IPermissionsRepository;
import com.aaa.bbb.ccc.data.repository.settings.ISettingsRepository;
import com.aaa.bbb.ccc.data.repository.forecast.IWeatherForecastRepository;
import com.aaa.bbb.ccc.model.Location;
import com.aaa.bbb.ccc.model.SynopticForecast;

import rx.Observable;
import rx.schedulers.Schedulers;

public class CurrentWeatherForecastInteractor implements ICurrentWeatherForecastInteractor {
    private IPermissionsRepository mPermissionsRepository;
    private IWeatherForecastRepository mRepositoryOfWeather;
    private ILocationRepository mLocationRepository;
    private ISettingsRepository mSettingsRepository;
    private ICityRepository mCityRepository;

    public CurrentWeatherForecastInteractor(
            IPermissionsRepository mPermissionsRepository,
            IWeatherForecastRepository mRepositoryOfWeather,
            ILocationRepository mLocationRepository,
            ISettingsRepository settingsRepository,
            ICityRepository cityRepository) {
        this.mPermissionsRepository = mPermissionsRepository;
        this.mRepositoryOfWeather = mRepositoryOfWeather;
        this.mLocationRepository = mLocationRepository;
        this.mSettingsRepository = settingsRepository;
        this.mCityRepository = cityRepository;
    }


    @Override
    public Observable<SynopticForecast> getCurrentWeather() {
        Observable<Location> locationObservable = mPermissionsRepository
                .getPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .flatMap(isGranted -> Boolean.TRUE.equals(isGranted)
                        ? mLocationRepository.getCurrentLocation()
                        : mSettingsRepository.getDefaultLocation());

        Observable<String> languageObservable = mSettingsRepository.getLanguage();
        Observable<String> unitsObservable = mSettingsRepository.getUnits();
        return Observable.zip(locationObservable, languageObservable, unitsObservable,
                (location, lang, utils) -> mRepositoryOfWeather.getWeatherForecast(location.getLat(), location.getLot(), lang, utils))
                .flatMap(weatherForecastObservable -> weatherForecastObservable)
                .flatMap(weatherForecast -> mCityRepository.getCityTranslate(weatherForecast.getPlace()), SynopticForecast::setPlace)
                .subscribeOn(Schedulers.io());
    }
}