package com.aaa.bbb.ccc.data.map;

import com.aaa.bbb.ccc.model.Place;
import com.aaa.bbb.ccc.data.model.api.weather.City;

import rx.functions.Func1;

public class FromCityApiToPlace implements Func1<City, Place> {
    @Override
    public Place call(City apiCity) {
        Place place = new Place();
        place.setName(apiCity.getName());
        place.setCountry(apiCity.getCountry());
        place.setId(apiCity.getId());
        return place;
    }
}
