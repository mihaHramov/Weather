package com.aaa.bbb.ccc.data.map;

import com.aaa.bbb.ccc.data.model.weatherApi.City;

import rx.functions.Func1;

public class FromCityApiToCity implements Func1<City, com.aaa.bbb.ccc.data.model.City> {
    @Override
    public com.aaa.bbb.ccc.data.model.City call(City apiCity) {
        com.aaa.bbb.ccc.data.model.City city = new com.aaa.bbb.ccc.data.model.City();
        city.setName(apiCity.getName());
        city.setCountry(apiCity.getCountry());
        city.setId(apiCity.getId());
        city.setSunrise(apiCity.getSunrise());
        city.setSunset(apiCity.getSunset());
        return city;
    }
}
