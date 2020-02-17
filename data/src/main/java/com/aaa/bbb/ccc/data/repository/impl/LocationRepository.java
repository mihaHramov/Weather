package com.aaa.bbb.ccc.data.repository.impl;

import android.content.Context;

import com.aaa.bbb.ccc.model.Location;
import com.aaa.bbb.ccc.data.repository.intrf.ILocationRepository;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;

public class LocationRepository implements ILocationRepository {
    private Context context;

    public LocationRepository(Context context) {
        this.context = context;
    }

    @Override
    public Observable<Location> getCurrentLocation() {
        ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(context);
        return locationProvider.getLastKnownLocation().map(location -> {
            String lat = Double.toString(location.getLatitude());
            String lot = Double.toString(location.getLongitude());
            return new Location(lat, lot);
        });
    }
}
