package com.aaa.bbb.ccc.weather.data.repository.impl;

import com.aaa.bbb.ccc.weather.data.model.Location;
import com.aaa.bbb.ccc.weather.data.repository.intrf.ILocationRepository;

import rx.Observable;

public class LocationRepository implements ILocationRepository {
    @Override
    public Observable<Location> getCurrentLocation() {
        return Observable.just(new Location("48.05","37.93"));
    }
}
