package com.aaa.bbb.ccc.weather.data.repository.impl;

import com.aaa.bbb.ccc.weather.data.model.Location;
import com.aaa.bbb.ccc.weather.data.repository.intrf.ISettingsRepository;

import rx.Observable;

public class SettingsRepository implements ISettingsRepository {
    @Override
    public Observable<String> getLanguage() {
        return Observable.just("ru");
    }

    @Override
    public Observable<String> getUnits() {
        return Observable.just("metric");
    }

    @Override
    public Observable<Location> getDefaultLocation() {
        return Observable.just(new Location("48.05","37.93"));
    }
}