package com.aaa.bbb.ccc.data;

import com.aaa.bbb.ccc.data.model.api.translate.TranslateResponse;
import com.aaa.bbb.ccc.data.network.TranslateApi;
import com.aaa.bbb.ccc.data.repository.city.CityRepository;
import com.aaa.bbb.ccc.data.repository.cash.ICashRepository;
import com.aaa.bbb.ccc.model.Location;
import com.aaa.bbb.ccc.model.Place;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlaceRepositoryTest {
    private static final String DB_ERROR = "db error";
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
        when(cashRepository.getCity(id, lang)).thenReturn(Observable.error(new Throwable(DB_ERROR)));
        Place placeDefault = new Place();
        placeDefault.setCountry(place.getCountry());
        placeDefault.setId(place.getId());
        placeDefault.setLangName(place.getLangName());
        Location location = new Location("20.0", "20.0");
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

    @Test
    public void getCityTranslateWhenDbGetError() {
        TestScheduler scheduler = Schedulers.test();
        Observable<Place> placeDbObservable = Observable.interval(250, TimeUnit.MILLISECONDS, scheduler)
                .flatMap((Func1<Long, Observable<Place>>) aLong -> Observable.error(new Throwable(DB_ERROR)))
                .onErrorResumeNext((Func1<Throwable, Observable<Place>>) throwable -> Observable.empty());
        when(cashRepository.getCity(id, lang)).thenReturn(placeDbObservable);
        TranslateResponse translateResponse = new TranslateResponse();
        translateResponse.setCode(200);
        translateResponse.setLang(lang);
        translateResponse.setText(Collections.singletonList(translateName));
        Observable<TranslateResponse> translateResponseObservable = Observable.interval(350, TimeUnit.MILLISECONDS, scheduler)
                .flatMap((Func1<Long, Observable<TranslateResponse>>) aLong -> Observable.just(translateResponse));
        when(translateApi.getTranslate(anyString(), anyString())).thenReturn(translateResponseObservable);
        Subscription subscription = cityRepository
                .getCityTranslate(place)
                .subscribe(testSubscriber);

        scheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS);
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        Assert.assertEquals(translateName, testSubscriber.getOnNextEvents().get(0).getName());
        verify(translateApi).getTranslate(anyString(), anyString());
        subscription.unsubscribe();
    }

    private Place initCity() {
        place = new Place();
        place.setName(name);
        place.setLangName(lang);
        place.setCountry("uk");
        place.setId(id);
        Location location = new Location("20.0", "20.0");
        place.setLocation(location);
        return place;
    }
}