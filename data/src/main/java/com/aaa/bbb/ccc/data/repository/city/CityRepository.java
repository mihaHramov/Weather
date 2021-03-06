package com.aaa.bbb.ccc.data.repository.city;

import androidx.core.util.Pair;

import com.aaa.bbb.ccc.data.map.TranslateLanguageMap;
import com.aaa.bbb.ccc.data.map.ZipCityAndTranslateInfo;
import com.aaa.bbb.ccc.data.network.TranslateApi;
import com.aaa.bbb.ccc.data.repository.cash.ICashRepository;
import com.aaa.bbb.ccc.model.Place;

import rx.Observable;

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
    public Observable<Place> getCityTranslate(Place locality) {
        Observable<Place> cityDbObs = mCashRepository.getCity(locality.getId(), locality.getLangName());
        Observable<Place> cityObservable = Observable.just(locality);
        Observable<String> language = Observable.just(locality.getLangName());
        Observable<String> name = cityObservable.map(Place::getName);
        Observable<String> oldLanguage = language.map(new TranslateLanguageMap(TranslateApi.Const.OPEN_WEATHER_MAP_API_BASE_LANGUAGE));
        Observable<Place> defaultResult = Observable.zip(name, Observable.just(TranslateApi.Const.OPEN_WEATHER_MAP_API_BASE_LANGUAGE), Pair::new)
                .zipWith(cityObservable, mapper);
        Observable<Place> resultOfTranslateObs = Observable.zip(name, oldLanguage, Pair::new)
                .flatMap(stringStringPair -> getTranslate(stringStringPair.first, stringStringPair.second),
                        (stringStringPair, s) -> new Pair<>(s, stringStringPair.second))
                .zipWith(cityObservable, mapper)
                .doOnNext(city -> mCashRepository.saveCity(city));
        return Observable.mergeDelayError(resultOfTranslateObs, cityDbObs)
                .first()
                .onErrorResumeNext(throwable -> defaultResult);
    }

    private Observable<String> getTranslate(String text, String lang) {
        return mTranslateApi.getTranslate(text, lang)
                .flatMap(translateResponse -> Observable.from(translateResponse.getText()))
                .first();
    }
}
