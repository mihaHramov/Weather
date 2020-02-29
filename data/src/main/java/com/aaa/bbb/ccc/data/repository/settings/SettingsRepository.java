package com.aaa.bbb.ccc.data.repository.settings;

import com.aaa.bbb.ccc.model.Location;

import java.util.Date;
import java.util.Locale;

import rx.Observable;

public class SettingsRepository implements ISettingsRepository {
    @Override
    public Observable<String> getLanguage() {
        return Observable.just(Locale.getDefault().getLanguage());
    }

    @Override
    public Observable<String> getUnits() {
        return Observable.just("metric");
    }

    @Override
    public Observable<Location> getDefaultLocation() {
        return Observable.just(new Location("48.05", "37.93"));
    }

    @Override
    public Integer getTime() {
        return (int) (new Date().getTime() / 1000);
    }
}