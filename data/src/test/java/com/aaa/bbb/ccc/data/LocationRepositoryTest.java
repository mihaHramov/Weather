package com.aaa.bbb.ccc.data;

import android.location.Location;

import com.aaa.bbb.ccc.data.repository.LocationRepository.LocationRepository;
import com.aaa.bbb.ccc.data.repository.LocationRepository.ILocationRepository;

import org.junit.Assert;
import org.junit.Test;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LocationRepositoryTest {

    @Test
    public void testRepositoryAndMap() {
        Double lat = 23.5678;
        Double lon = 19.4788;
        ReactiveLocationProvider mockReactiveLocationProvider = mock(ReactiveLocationProvider.class);
        Location location = mock(Location.class);
        when(location.getLatitude()).thenReturn(lat);
        when(location.getLongitude()).thenReturn(lon);
        when(mockReactiveLocationProvider.getLastKnownLocation()).thenReturn(Observable.just(location));
        ILocationRepository locationRepository = new LocationRepository(mockReactiveLocationProvider);
        TestSubscriber<com.aaa.bbb.ccc.model.Location> locationTestSubscriber = new TestSubscriber<>();
        locationRepository.getCurrentLocation().subscribe(locationTestSubscriber);
        locationTestSubscriber.assertCompleted();
        locationTestSubscriber.assertNoErrors();
        com.aaa.bbb.ccc.model.Location locationResult = locationTestSubscriber.getOnNextEvents().get(0);
        Assert.assertEquals(lat.toString(), locationResult.getLat());
        Assert.assertEquals(lon.toString(), locationResult.getLot());
    }
}
