package com.aaa.bbb.ccc.data.repository.impl;

import androidx.core.util.Pair;

import com.aaa.bbb.ccc.data.map.TranslateLanguageMap;
import com.aaa.bbb.ccc.data.map.ZipCityAndTranslateInfo;
import com.aaa.bbb.ccc.data.model.City;
import com.aaa.bbb.ccc.data.model.translateApi.TranslateResponse;
import com.aaa.bbb.ccc.data.network.OpenWeatherMapApi;
import com.aaa.bbb.ccc.data.network.TranslateApi;
import com.aaa.bbb.ccc.data.repository.intrf.ICashRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ICityRepository;

import rx.Observable;
import rx.functions.Func1;

public class CityRepository implements ICityRepository {
    private ICashRepository mCashRepository;
    private ZipCityAndTranslateInfo mapper;
    private TranslateApi mTranslateApi;

    public CityRepository(ICashRepository mCashRepository, TranslateApi translateApi) {
        this.mCashRepository = mCashRepository;
        this.mapper = new ZipCityAndTranslateInfo();
        this.mTranslateApi = translateApi;
    }

    @Override
    public Observable<City> getCityTranslate(City locality) {
        Observable<City> cityDbObs = mCashRepository.getCity(locality.getId(), locality.getLangName());
        Observable<City> cityObservable = Observable.just(locality);
        Observable<String> language = Observable.just(locality.getLangName());
        Observable<String> name = cityObservable.map(City::getName);
        Observable<String> oldLanguage = language.map(new TranslateLanguageMap(OpenWeatherMapApi.BASE_LANGUAGE));
        Observable<City> defaultResult = Observable.zip(name, Observable.just(OpenWeatherMapApi.BASE_LANGUAGE), Pair::new)
                .zipWith(cityObservable, mapper);

        Observable<City> resultOfTranslateObs = Observable.zip(name, oldLanguage, Pair::new)
                .flatMap((Func1<Pair<String, String>, Observable<String>>)//translate
                                stringStringPair ->
                                        mTranslateApi.getTranslate(stringStringPair.first, stringStringPair.second)
                                                .flatMap((Func1<TranslateResponse, Observable<String>>)
                                                        translateResponse -> Observable.from(translateResponse.getText()))
                                                .first(),
                        (stringStringPair, s) -> new Pair<>(s, stringStringPair.second)
                )
                .zipWith(cityObservable, mapper)
                .doOnNext(city -> mCashRepository.saveCity(city));
        return resultOfTranslateObs
                .mergeWith(cityDbObs)
                .first()
                .onErrorResumeNext(defaultResult);
    }
}