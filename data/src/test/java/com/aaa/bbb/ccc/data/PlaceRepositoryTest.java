package com.aaa.bbb.ccc.data;

import com.aaa.bbb.ccc.model.Location;
import com.aaa.bbb.ccc.model.Place;
import com.aaa.bbb.ccc.data.model.api.translate.TranslateResponse;
import com.aaa.bbb.ccc.data.network.TranslateApi;
import com.aaa.bbb.ccc.data.repository.impl.CityRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ICashRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlaceRepositoryTest {
    private Place place;
    private ICashRepository cashRepository;
    private TranslateApi translateApi;
    private CityRepository cityRepository;
    private Integer id = 102030;
    private String lang = "ru";
    private String translateName = "Лондон";
    private String name = "London";
    private TestSubscriber<Place> testSubscriber;

    @Before
    public void setUp() {
        place = initCity();
        testSubscriber = new TestSubscriber<>();
        cashRepository = mock(ICashRepository.class);
        translateApi = mock(TranslateApi.class);
        Place placeFromDb = initCity();
        placeFromDb.setName(translateName);
        TranslateResponse translateResponse = new TranslateResponse();
        translateResponse.setCode(200);
        translateResponse.setLang(lang);
        translateResponse.setText(Collections.singletonList(translateName));
        when(cashRepository.getCity(id, lang)).thenReturn(Observable.just(placeFromDb));
        when(translateApi.getTranslate(name, lang)).thenReturn(Observable.just(translateResponse));
        cityRepository = new CityRepository(cashRepository, translateApi);
    }


    @Test
    public void getCityTranslateWhenAllFine() {
        cityRepository
                .getCityTranslate(place)
                .subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        Assert.assertEquals(translateName, testSubscriber.getOnNextEvents().get(0).getName());
    }

    @Test
    public void getCityTranslateWhenServerGetError() {
        when(translateApi.getTranslate(name, lang)).thenReturn(Observable.error(new Throwable("network error")));
        cityRepository
                .getCityTranslate(place)
                .subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        Assert.assertEquals(translateName, testSubscriber.getOnNextEvents().get(0).getName());
        verify(translateApi).getTranslate(anyString(), anyString());
        verify(cashRepository).getCity(id, lang);
    }


    @Test
    public void getCityTranslateWhenServerAndDbGetError() {
        when(translateApi.getTranslate(name, lang)).thenReturn(Observable.error(new Throwable("network error")));
        when(cashRepository.getCity(id, lang)).thenReturn(Observable.error(new Throwable("db error")));
        Place placeDefault = new Place();
        placeDefault.setCountry(place.getCountry());
        placeDefault.setId(place.getId());
        placeDefault.setLangName(place.getLangName());
        Location location = new Location("20.0","20.0");
        placeDefault.setLocation(location);
        placeDefault.setName(name);


        cityRepository
                .getCityTranslate(placeDefault)
                .subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        Assert.assertEquals(name, testSubscriber.getOnNextEvents().get(0).getName());
        verify(translateApi).getTranslate(anyString(), anyString());
        verify(cashRepository).getCity(id, lang);
    }

    private Place initCity() {
        place = new Place();
        place.setName(name);
        place.setLangName(lang);
        place.setCountry("uk");
        place.setId(id);
        Location location = new Location("20.0","20.0");
        place.setLocation(location);
        return place;
    }
}