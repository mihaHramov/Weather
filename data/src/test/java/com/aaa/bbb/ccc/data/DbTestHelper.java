package com.aaa.bbb.ccc.data;

import com.aaa.bbb.ccc.data.model.entity.City;
import com.aaa.bbb.ccc.data.model.entity.Forecast;
import com.aaa.bbb.ccc.model.Location;
import com.aaa.bbb.ccc.model.Place;

class DbTestHelper {
    private DbTestHelper() {
        throw new IllegalStateException("Utility class");
    }

    static Place getPlace(String name, String country, String lang, Integer id, String lat, String lon) {
        Place place = new Place();
        place.setCountry(country);
        place.setName(name);
        place.setLangName(lang);
        place.setId(id);
        Location location = new Location(lat, lon);
        place.setLocation(location);
        return place;
    }

    static City getCity(String name,
                        String country,
                        String langName,
                        Double lon, Double lat,
                        Integer id) {
        City city = new City();
        city.setName(name);
        city.setCountry(country);
        city.setLat(lat);
        city.setLangName(langName);
        city.setLon(lon);
        city.setIdCity(id);
        city.setLatCos(Math.cos(lat));
        city.setLonCos(Math.cos(lon));
        city.setLatSin(Math.sin(lat));
        city.setLatCos(Math.sin(lon));
        return city;
    }

    static Forecast getForecast(Integer cityId, Integer date) {
        Forecast forecast = new Forecast();
        forecast.setCityId(cityId);
        forecast.setClouds(20);
        forecast.setPressure(100);
        forecast.setDate(date);
        return forecast;
    }
}
