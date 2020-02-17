package com.aaa.bbb.ccc.data.map;

import com.aaa.bbb.ccc.model.Place;

import rx.functions.Func1;

public class FromCityToCityEntity implements Func1<Place, com.aaa.bbb.ccc.data.model.entity.City> {


    @Override
    public com.aaa.bbb.ccc.data.model.entity.City call(Place place) {
        double lat = convertRadian(Double.valueOf(place.getLocation().getLat()));
        double lon = convertRadian(Double.valueOf(place.getLocation().getLot()));

        Double latSin = Math.sin(lat);
        Double latCos = Math.cos(lat);
        Double lonSin = Math.sin(lon);
        Double lonCos = Math.sin(lon);

        com.aaa.bbb.ccc.data.model.entity.City entityCity = new com.aaa.bbb.ccc.data.model.entity.City();
        entityCity.setId(place.getId());
        entityCity.setCountry(place.getCountry());
        entityCity.setName(place.getName());

        entityCity.setLatCos(latCos);
        entityCity.setLatSin(latSin);
        entityCity.setLonCos(lonCos);
        entityCity.setLonSin(lonSin);
        entityCity.setLon(Double.valueOf(place.getLocation().getLot()));
        entityCity.setLat(Double.valueOf(place.getLocation().getLat()));
        return entityCity;
    }

    private Double convertRadian(Double agle) {
        return agle * Math.PI / 180;
    }
}
