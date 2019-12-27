package com.aaa.bbb.ccc.data.repository.impl;

import com.aaa.bbb.ccc.data.repository.intrf.ISettingsRepository;
import com.aaa.bbb.ccc.data.model.Location;

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