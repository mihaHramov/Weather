package com.aaa.bbb.ccc.data.repository.impl;

import com.aaa.bbb.ccc.data.db.WeatherDatabase;
import com.aaa.bbb.ccc.data.map.FromCityEntryToCity;
import com.aaa.bbb.ccc.data.map.FromCityToCityEntity;
import com.aaa.bbb.ccc.data.model.City;
import com.aaa.bbb.ccc.data.repository.intrf.ICashRepository;

import rx.Observable;

public class CashRepository implements ICashRepository {
    private WeatherDatabase mWeatherDataBase;

    public CashRepository(WeatherDatabase weatherDataBase) {
        this.mWeatherDataBase = weatherDataBase;
    }

    @Override
    public Observable<City> getCity(Integer id, String lang) {
        Observable<String> langObs = Observable.just(lang);
        Observable<Integer> idObs = Observable.just(id);
        return Observable.zip(idObs, langObs,
                ((integer, s) -> mWeatherDataBase.getCityDao().getByIdAndLanguage(integer, s)))
                .map(new FromCityEntryToCity());
    }

    @Override
    public void saveCity(City city) {
        FromCityToCityEntity map = new FromCityToCityEntity();
        mWeatherDataBase.getCityDao().insert(map.call(city));
    }
}
