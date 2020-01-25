package com.aaa.bbb.ccc.data.repository.intrf;

import com.aaa.bbb.ccc.data.model.City;

import rx.Observable;

public interface ICityRepository {
    Observable<City> getCityTranslate(City locality);
}
