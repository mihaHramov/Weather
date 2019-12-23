package com.aaa.bbb.ccc.weather.domain.interactor;

import android.Manifest;

import com.aaa.bbb.ccc.weather.data.model.Location;
import com.aaa.bbb.ccc.weather.data.model.WeatherForecast;
import com.aaa.bbb.ccc.weather.data.repository.intrf.ILocationRepository;
import com.aaa.bbb.ccc.weather.data.repository.intrf.IPermissionsRepository;
import com.aaa.bbb.ccc.weather.data.repository.intrf.ISettingsRepository;
import com.aaa.bbb.ccc.weather.data.repository.intrf.IWeatherForecastRepository;
import com.aaa.bbb.ccc.weather.domain.map.DetailWeatherForecastToDailyForecast;
import com.aaa.bbb.ccc.weather.domain.model.DailyForecast;
import com.aaa.bbb.ccc.weather.domain.model.Place;
import com.aaa.bbb.ccc.weather.domain.model.SynopticForecast;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class CurrentWeatherForecastInteractor implements ICurrentWeatherForecastInteractor {
    private IPermissionsRepository mPermissionsRepository;
    private IWeatherForecastRepository mRepositoryOfWeather;
    private ILocationRepository mLocationRepository;
    private ISettingsRepository mSettingsRepository;

    public CurrentWeatherForecastInteractor(IPermissionsRepository mPermissionsRepository, IWeatherForecastRepository mRepositoryOfWeather, ILocationRepository mLocationRepository, ISettingsRepository settingsRepository) {
        this.mPermissionsRepository = mPermissionsRepository;
        this.mRepositoryOfWeather = mRepositoryOfWeather;
        this.mLocationRepository = mLocationRepository;
        this.mSettingsRepository = settingsRepository;
    }


    @Override
    public Observable<SynopticForecast> getCurrentWeather() {
        Observable<Location> locationObservable = mPermissionsRepository
                .getPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .flatMap((Func1<Boolean, Observable<Location>>) isGranted -> isGranted ? mLocationRepository.getCurrentLocation() : mSettingsRepository.getDefaultLocation());

        Observable<String> languageObservable = mSettingsRepository.getLanguage();
        Observable<String> unitsObservable = mSettingsRepository.getUnits();
        return Observable.zip(
                locationObservable, languageObservable, unitsObservable,
                (location, lang, utils) -> mRepositoryOfWeather.getWeatherForecast(location.getLat(), location.getLot(), lang, utils))
                .flatMap((Func1<Observable<WeatherForecast>, Observable<WeatherForecast>>) weatherForecastObservable -> weatherForecastObservable)
                .flatMap(this::convertToSynoptic);
    }

    private Observable<SynopticForecast> convertToSynoptic(WeatherForecast weatherForecast) {
        Observable<Place> place = Observable.just(weatherForecast.getLocality())
                .map(city -> new Place(city.getName(), city.getCountry(), city.getSunrise().toString(), city.getSunset().toString()));
        Observable<List<DailyForecast>> listDailyForecast = Observable.from(weatherForecast.getForecasts())
                .flatMap(new DetailWeatherForecastToDailyForecast())
                .toList();
       return place.zipWith(listDailyForecast, SynopticForecast::new);
    }
}