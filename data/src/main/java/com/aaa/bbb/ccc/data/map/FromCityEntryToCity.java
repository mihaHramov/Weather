package com.aaa.bbb.ccc.data.map;

import com.aaa.bbb.ccc.data.model.entity.City;
import com.aaa.bbb.ccc.model.Place;

import rx.functions.Func1;

public class FromCityEntryToCity implements Func1<City, Place> {
    @Override
    public Place call(City city) {
        Place placeModel = new Place();
        placeModel.setLangName(city.getLangName());
        placeModel.setName(city.getName());
        placeModel.setCountry(city.getCountry());
        placeModel.setId(city.getIdCity());
        return placeModel;
    }
}
