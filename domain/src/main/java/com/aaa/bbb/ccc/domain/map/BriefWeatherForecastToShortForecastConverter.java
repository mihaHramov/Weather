package com.aaa.bbb.ccc.domain.map;

import com.aaa.bbb.ccc.data.model.BriefWeatherForecast;
import com.aaa.bbb.ccc.data.model.Temperature;
import com.aaa.bbb.ccc.domain.model.ShortForecast;
import com.aaa.bbb.ccc.domain.model.WeatherType;
import com.aaa.bbb.ccc.domain.model.Wind;
import com.aaa.bbb.ccc.domain.utils.DateConverter;
import com.aaa.bbb.ccc.domain.utils.WindTypeConverter;

import java.util.Calendar;

import rx.functions.Func1;

public class BriefWeatherForecastToShortForecastConverter implements Func1<BriefWeatherForecast, ShortForecast> {
    @Override
    public ShortForecast call(BriefWeatherForecast briefWeatherForecast) {
        ShortForecast shortForecast = new ShortForecast();
        Calendar date = DateConverter.getDateByInteger(briefWeatherForecast.getDate());

        shortForecast.setDate(date);
        shortForecast.setClouds(briefWeatherForecast.getClouds());
        shortForecast.setHumidity(briefWeatherForecast.getHumidity());
        shortForecast.setPressure(briefWeatherForecast.getPressure());
        shortForecast.setSnow(briefWeatherForecast.getSnow());

        WeatherType weatherType = new WeatherType(briefWeatherForecast.getWeatherType().getDescription(),briefWeatherForecast.getWeatherType().getIcon());
        shortForecast.setWeatherType(weatherType);

        //temperature
        Temperature temperatureOld = briefWeatherForecast.getTemperature();
        com.aaa.bbb.ccc.domain.model.Temperature temperature = new com.aaa.bbb.ccc.domain.model.Temperature(temperatureOld.getMax(), temperatureOld.getMin());
        shortForecast.setTemperature(temperature);

        Wind wind = new Wind(WindTypeConverter.convert(briefWeatherForecast.getWind()), briefWeatherForecast.getWind().getSpeed());
        shortForecast.setWind(wind);
        return shortForecast;
    }
}