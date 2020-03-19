package com.aaa.bbb.ccc.data.repository.country;

import com.aaa.bbb.ccc.data.model.Country;

import rx.Observable;

public interface ICountryRepository {
    Observable<Country> getCountryByCode(String iso);
}
