package com.aaa.bbb.ccc.data.repository.CityRepository;

import com.aaa.bbb.ccc.model.Place;

import rx.Observable;

public interface ICityRepository {
    Observable<Place> getCityTranslate(Place locality);
}
