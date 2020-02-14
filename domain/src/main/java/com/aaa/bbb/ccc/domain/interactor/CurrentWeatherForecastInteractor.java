package com.aaa.bbb.ccc.domain.interactor;

import android.Manifest;

import com.aaa.bbb.ccc.data.model.Location;
import com.aaa.bbb.ccc.data.model.WeatherForecast;
import com.aaa.bbb.ccc.data.repository.intrf.ICityRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ILocationRepository;
import com.aaa.bbb.ccc.data.repository.intrf.IPermissionsRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ISchedulerRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ISettingsRepository;
import com.aaa.bbb.ccc.data.repository.intrf.IWeatherForecastRepository;
import com.aaa.bbb.ccc.domain.map.DetailWeatherForecastToDailyForecast;
import com.aaa.bbb.ccc.domain.map.FromCityToPlace;
import com.aaa.bbb.ccc.domain.model.DailyForecast;
import com.aaa.bbb.ccc.domain.model.Place;
import com.aaa.bbb.ccc.domain.model.SynopticForecast;

import java.util.List;

import rx.Observable;

public class CurrentWeatherForecastInteractor implements ICurrentWeatherForecastInteractor {
    private ISchedulerRepository mSchedulerRepository;
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
            ISchedulerRepository schedulerRepository,
            ICityRepository cityRepository) {
        this.mPermissionsRepository = mPermissionsRepository;
        this.mRepositoryOfWeather = mRepositoryOfWeather;
        this.mLocationRepository = mLocationRepository;
        this.mSettingsRepository = settingsRepository;
        this.mSchedulerRepository = schedulerRepository;
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
                (location, lang, utils) -> getWeather(location.getLat(), location.getLot(), lang, utils))
                .flatMap(weatherForecastObservable -> weatherForecastObservable)
                .flatMap(weatherForecast -> mCityRepository.getCityTranslate(weatherForecast.getLocality()),
                        (weatherForecast, city) -> {
                            weatherForecast.setLocality(city);
                            return weatherForecast;
                        })
                .flatMap(this::convertToSynoptic)
                .subscribeOn(mSchedulerRepository.getIO());
    }

    private Observable<WeatherForecast> getWeather(String lat, String lot, String lang, String utils) {
        return mRepositoryOfWeather.getWeatherForecast(lat, lot, lang, utils)
                .doOnNext(weatherForecast -> weatherForecast.getLocality().setLangName(lang));
    }

    private Observable<SynopticForecast> convertToSynoptic(WeatherForecast weatherForecast) {
        Observable<Place> place = Observable.just(weatherForecast.getLocality())
                .map(new FromCityToPlace());
        Observable<List<DailyForecast>> listDailyForecast = Observable.from(weatherForecast.getForecasts())
                .flatMap(new DetailWeatherForecastToDailyForecast())
                .toList();
        return place.zipWith(listDailyForecast, SynopticForecast::new);
    }
}