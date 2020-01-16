package com.aaa.bbb.ccc.data.map;

import com.aaa.bbb.ccc.data.db.entity.City;

import rx.functions.Func1;

public class FromCityEntryToCity implements Func1<City, com.aaa.bbb.ccc.data.model.City> {
    @Override
    public com.aaa.bbb.ccc.data.model.City call(City city) {
        com.aaa.bbb.ccc.data.model.City cityModel = new com.aaa.bbb.ccc.data.model.City();
        cityModel.setLangName(city.getLangName());
        cityModel.setName(city.getName());
        cityModel.setCountry(city.getCountry());
        cityModel.setId((int) city.getId());
        cityModel.setLat(city.getLat());
        cityModel.setLon(city.getLon());
        return cityModel;
    }
}
