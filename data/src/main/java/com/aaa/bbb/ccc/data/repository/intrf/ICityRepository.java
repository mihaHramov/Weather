package com.aaa.bbb.ccc.data.repository.intrf;

import com.aaa.bbb.ccc.model.Place;

import rx.Observable;

public interface ICityRepository {
    Observable<Place> getCityTranslate(Place locality);
}
