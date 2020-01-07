package com.aaa.bbb.ccc.data.map;

import com.aaa.bbb.ccc.data.db.entity.City;

import rx.functions.Func1;

public class FromCityEntryToCity implements Func1<City, com.aaa.bbb.ccc.data.model.weatherApi.City> {
    @Override
    public com.aaa.bbb.ccc.data.model.weatherApi.City call(City city) {
        com.aaa.bbb.ccc.data.model.weatherApi.City cityApi = new com.aaa.bbb.ccc.data.model.weatherApi.City();
        cityApi.setCountry(city.getCountry());
        cityApi.setName(city.getName());
        cityApi.setId((int) city.getId());
        return cityApi;
    }
}
