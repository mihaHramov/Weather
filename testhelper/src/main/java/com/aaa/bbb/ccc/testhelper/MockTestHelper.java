package com.aaa.bbb.ccc.testhelper;

import com.aaa.bbb.ccc.model.DailyForecast;
import com.aaa.bbb.ccc.model.Location;
import com.aaa.bbb.ccc.model.Place;
import com.aaa.bbb.ccc.model.ShortForecast;
import com.aaa.bbb.ccc.model.Temperature;
import com.aaa.bbb.ccc.model.WeatherType;
import com.aaa.bbb.ccc.model.Wind;

import java.util.Arrays;
import java.util.List;

public class MockTestHelper {
    private MockTestHelper(){
        throw new IllegalStateException("Utility class");
    }

     private static ShortForecast getShortForecast(Integer date,
                                                 Wind wind,
                                                 Temperature temperature,
                                                 WeatherType weatherType) {
        ShortForecast shortForecast = new ShortForecast();
        shortForecast.setDate(date);
        shortForecast.setSnow(20.0);
        shortForecast.setWind(wind);
        shortForecast.setClouds(3);
        shortForecast.setHumidity(1);
        shortForecast.setPressure(93);
        shortForecast.setRain(20.10);
        shortForecast.setTemperature(temperature);
        shortForecast.setWeatherType(weatherType);
        return shortForecast;
    }

    public static DailyForecast getDailyForecast(Integer date) {
        Wind wind = new Wind(30.0, 10);
        Temperature temperature = new Temperature(20.0, 10.0);
        WeatherType weatherType = new WeatherType("description", "icon.png");
        List<ShortForecast> shortForecasts = Arrays.asList(
                getShortForecast(date, wind, temperature, weatherType),
                getShortForecast(date + 1, wind, temperature, weatherType),
                getShortForecast(date + 2, wind, temperature, weatherType)
        );
        return new DailyForecast(shortForecasts);
    }

    public static Place getPlace(String name, String country, String lang, Integer id, String lat, String lon) {
        Place place = new Place();
        place.setCountry(country);
        place.setName(name);
        place.setLangName(lang);
        place.setId(id);
        Location location = new Location(lat, lon);
        place.setLocation(location);
        return place;
    }
}
