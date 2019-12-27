package com.aaa.bbb.ccc.data.repository.intrf;

import com.aaa.bbb.ccc.data.model.Location;

import rx.Observable;

public interface ISettingsRepository {
    Observable<String> getLanguage();
    Observable<String> getUnits();
    Observable<Location> getDefaultLocation();
}
