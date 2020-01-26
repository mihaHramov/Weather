package com.aaa.bbb.ccc.domain.map;

import com.aaa.bbb.ccc.data.model.City;
import com.aaa.bbb.ccc.domain.model.Place;

import rx.functions.Func1;

public class FromCityToPlace implements Func1<City, Place> {
    @Override
    public Place call(City city) {
        return new Place(city.getName(), city.getCountry(), city.getSunrise().toString(), city.getSunset().toString());
    }
}
