package com.aaa.bbb.ccc.data.repository.SettingsRepository;

import com.aaa.bbb.ccc.model.Location;

import rx.Observable;

public interface ISettingsRepository {
    Observable<String> getLanguage();
    Observable<String> getUnits();
    Observable<Location> getDefaultLocation();
}
