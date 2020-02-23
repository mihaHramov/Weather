package com.aaa.bbb.ccc.data.repository.location;

import com.aaa.bbb.ccc.model.Location;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;

public class LocationRepository implements ILocationRepository {
    private ReactiveLocationProvider locationProvider;

    public LocationRepository(ReactiveLocationProvider reactiveLocationProvider) {
        this.locationProvider = reactiveLocationProvider;
    }

    @Override
    public Observable<Location> getCurrentLocation() {
        return locationProvider.getLastKnownLocation().map(location -> {
            String lat = Double.toString(location.getLatitude());
            String lot = Double.toString(location.getLongitude());
            return new Location(lat, lot);
        });
    }
}
