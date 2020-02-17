package com.aaa.bbb.ccc.data.map;

import com.aaa.bbb.ccc.data.model.entity.Forecast;
import com.aaa.bbb.ccc.data.model.Wind;
import com.aaa.bbb.ccc.data.utils.DateConverter;
import com.aaa.bbb.ccc.model.ShortForecast;
import com.aaa.bbb.ccc.model.Place;

import rx.functions.Func2;


public class FromBriefForecastToForecastEntity implements Func2<ShortForecast, Place,Forecast>{
    @Override
    public Forecast call(ShortForecast shortForecast, Place place) {
        Forecast forecast = new Forecast();
        forecast.setClouds(shortForecast.getClouds());
        forecast.setDate(DateConverter.convertToInteger(shortForecast.getDate()));
        forecast.setHumidity(shortForecast.getHumidity());
        forecast.setPressure(shortForecast.getPressure());
        forecast.setSnow(shortForecast.getSnow());
        forecast.setTemperature(shortForecast.getTemperature());
        Wind wind = new Wind(shortForecast.getWind().getWindSpeed(),shortForecast.getWind().getDeg());
        forecast.setWind(wind);
        forecast.setWeatherType(shortForecast.getWeatherType());
        forecast.setCityId(place.getId());
        return forecast;
    }
}
