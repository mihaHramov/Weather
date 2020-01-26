package com.aaa.bbb.ccc.data;

import com.aaa.bbb.ccc.data.model.City;
import com.aaa.bbb.ccc.data.model.translateApi.TranslateResponse;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CityRepositoryTest {
    private City city;
    private ICashRepository cashRepository;
    private TranslateApi translateApi;
    private CityRepository cityRepository;
    private Integer id = 102030;
    private String lang = "ru";
    private String translateName = "Лондон";
    private String name = "London";
    private TestSubscriber<City> testSubscriber;

    @Before
    public void setUp() {
        city = initCity();
        testSubscriber = new TestSubscriber<>();
        cashRepository = mock(ICashRepository.class);
        translateApi = mock(TranslateApi.class);
        City cityFromDb = initCity();
        cityFromDb.setName(translateName);
        TranslateResponse translateResponse = new TranslateResponse();
        translateResponse.setCode(200);
        translateResponse.setLang(lang);
        translateResponse.setText(Collections.singletonList(translateName));
        when(cashRepository.getCity(id, lang)).thenReturn(Observable.just(cityFromDb));
        when(translateApi.getTranslate(name, lang)).thenReturn(Observable.just(translateResponse));
        cityRepository = new CityRepository(cashRepository, translateApi);
    }


    @Test
    public void getCityTranslateWhenAllFine() {
        cityRepository
                .getCityTranslate(city)
                .subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        Assert.assertEquals(translateName,testSubscriber.getOnNextEvents().get(0).getName());
    }

    @Test
    public void getCityTranslateWhenServerGetError() {
        when(translateApi.getTranslate(name, lang)).thenReturn(Observable.error(new Throwable("network error")));
        cityRepository
                .getCityTranslate(city)
                .subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        Assert.assertEquals(translateName,testSubscriber.getOnNextEvents().get(0).getName());
        verify(translateApi).getTranslate(anyString(), anyString());
        verify(cashRepository).getCity(id,lang);
    }


    @Test
    public void getCityTranslateWhenServerAndDbGetError() {
        when(translateApi.getTranslate(name, lang)).thenReturn(Observable.error(new Throwable("network error")));
        when(cashRepository.getCity(id,lang)).thenReturn(Observable.error(new Throwable("db error")));
        City cityDefault = new City();
        cityDefault.setCountry(city.getCountry());
        cityDefault.setId(city.getId());
        cityDefault.setLangName(city.getLangName());
        cityDefault.setLat(city.getLat());
        cityDefault.setLon(city.getLon());
        cityDefault.setSunrise(city.getSunrise());
        cityDefault.setSunset(city.getSunset());
        cityDefault.setName(name);


        cityRepository
                .getCityTranslate(cityDefault)
                .subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        Assert.assertEquals(name,testSubscriber.getOnNextEvents().get(0).getName());
        verify(translateApi).getTranslate(anyString(),anyString());
        verify(cashRepository).getCity(id,lang);
    }

    private City initCity() {
        city = new City();
        city.setName(name);
        city.setLangName(lang);
        city.setCountry("uk");
        city.setId(id);
        city.setLon(20.0);
        city.setLat(20.0);
        city.setSunset(1030);
        city.setSunrise(203040);
        return city;
    }
}