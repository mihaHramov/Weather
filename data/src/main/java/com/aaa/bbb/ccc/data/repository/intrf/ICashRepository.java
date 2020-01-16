package com.aaa.bbb.ccc.data.repository.intrf;

import com.aaa.bbb.ccc.data.model.City;

import rx.Observable;

public interface ICashRepository {
    Observable<City> getCity(Integer id,String lang);
    void saveCity(City city);
}
