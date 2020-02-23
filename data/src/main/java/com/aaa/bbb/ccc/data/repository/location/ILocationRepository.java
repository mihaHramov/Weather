package com.aaa.bbb.ccc.data.repository.location;

import com.aaa.bbb.ccc.model.Location;

import rx.Observable;

public interface ILocationRepository {
    Observable<Location> getCurrentLocation();
}
