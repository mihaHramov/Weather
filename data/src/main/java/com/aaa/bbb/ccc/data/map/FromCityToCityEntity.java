package com.aaa.bbb.ccc.data.map;

import com.aaa.bbb.ccc.data.model.City;

import rx.functions.Func1;

public class FromCityToCityEntity implements Func1<City, com.aaa.bbb.ccc.data.db.entity.City> {


    @Override
    public com.aaa.bbb.ccc.data.db.entity.City call(City city) {
        double lat = convertRadian(city.getLat());
        double lon = convertRadian(city.getLon());

        Double latSin = Math.sin(lat);
        Double latCos = Math.cos(lat);
        Double lonSin = Math.sin(lon);
        Double lonCos = Math.sin(lon);

        com.aaa.bbb.ccc.data.db.entity.City entityCity = new com.aaa.bbb.ccc.data.db.entity.City();
        entityCity.setId(city.getId());
        entityCity.setCountry(city.getCountry());
        entityCity.setName(city.getName());

        entityCity.setLatCos(latCos);
        entityCity.setLatSin(latSin);
        entityCity.setLonCos(lonCos);
        entityCity.setLonSin(lonSin);
        entityCity.setLon(city.getLon());
        entityCity.setLat(city.getLat());
        return entityCity;
    }

    private Double convertRadian(Double agle) {
        return agle * Math.PI / 180;
    }
}
