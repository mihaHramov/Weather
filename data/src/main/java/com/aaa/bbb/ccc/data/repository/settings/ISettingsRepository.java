package com.aaa.bbb.ccc.data.repository.settings;

import rx.Observable;

public interface ISettingsRepository {
    Observable<String> getLanguage();
    Observable<String> getUnits();
    Observable<String> getCountry();
}
