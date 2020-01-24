package com.aaa.bbb.ccc.data.repository.impl;

import com.aaa.bbb.ccc.data.model.City;
import com.aaa.bbb.ccc.data.repository.intrf.ICashRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ICityRepository;

public class CityRepository implements ICityRepository {
    private ICashRepository mCashRepository;

    public CityRepository(ICashRepository mCashRepository) {
        this.mCashRepository = mCashRepository;
    }

    @Override
    public void saveCity(City city) {
        mCashRepository.saveCity(city);
    }
}
