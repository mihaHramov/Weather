package com.aaa.bbb.ccc.data.map;

import com.aaa.bbb.ccc.data.db.entity.Forecast;
import com.aaa.bbb.ccc.data.model.BriefWeatherForecast;
import com.aaa.bbb.ccc.data.model.City;

import rx.functions.Func2;


public class FromBriefForecastToForecastEntity implements Func2<BriefWeatherForecast,City,Forecast>{
    @Override
    public Forecast call(BriefWeatherForecast briefWeatherForecast, City city) {
        Forecast forecast = new Forecast();
        forecast.setClouds(briefWeatherForecast.getClouds());
        forecast.setDate(briefWeatherForecast.getDate());
        forecast.setHumidity(briefWeatherForecast.getHumidity());
        forecast.setPressure(briefWeatherForecast.getPressure());
        forecast.setSnow(briefWeatherForecast.getSnow());
        forecast.setTemperature(briefWeatherForecast.getTemperature());
        forecast.setWind(briefWeatherForecast.getWind());
        forecast.setWeatherType(briefWeatherForecast.getWeatherType());
        forecast.setCityId(city.getId());
        return forecast;
    }
}
