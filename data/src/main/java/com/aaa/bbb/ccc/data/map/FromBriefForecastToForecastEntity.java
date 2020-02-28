package com.aaa.bbb.ccc.data.map;

import com.aaa.bbb.ccc.data.model.entity.Forecast;
import com.aaa.bbb.ccc.model.Place;
import com.aaa.bbb.ccc.model.ShortForecast;

import rx.functions.Func2;


public class FromBriefForecastToForecastEntity implements Func2<ShortForecast, Place, Forecast> {
    @Override
    public Forecast call(ShortForecast shortForecast, Place place) {
        Forecast forecast = new Forecast();
        forecast.setClouds(shortForecast.getClouds());
        forecast.setDate(shortForecast.getDate());
        forecast.setHumidity(shortForecast.getHumidity());
        forecast.setPressure(shortForecast.getPressure());
        forecast.setSnow(shortForecast.getSnow());
        forecast.setTemperature(shortForecast.getTemperature());
        forecast.setWind(shortForecast.getWind());
        forecast.setWeatherType(shortForecast.getWeatherType());
        forecast.setCityId(place.getId());
        return forecast;
    }
}
