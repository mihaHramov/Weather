package com.aaa.bbb.ccc.data.map;

import com.aaa.bbb.ccc.data.model.entity.Forecast;
import com.aaa.bbb.ccc.model.ShortForecast;

import rx.functions.Func1;

public class FromEntryForecastToForecast implements Func1<Forecast, ShortForecast> {
    @Override
    public ShortForecast call(Forecast forecast) {
        ShortForecast shortForecast = new ShortForecast();
        shortForecast.setRain(forecast.getRain());
        shortForecast.setClouds(forecast.getClouds());
        shortForecast.setPressure(forecast.getPressure());
        shortForecast.setHumidity(forecast.getHumidity());
        shortForecast.setSnow(forecast.getSnow());

        shortForecast.setDate(forecast.getDate());
        shortForecast.setWeatherType(forecast.getWeatherType());
        shortForecast.setTemperature(forecast.getTemperature());
        shortForecast.setWind(forecast.getWind());
        return shortForecast;
    }
}
