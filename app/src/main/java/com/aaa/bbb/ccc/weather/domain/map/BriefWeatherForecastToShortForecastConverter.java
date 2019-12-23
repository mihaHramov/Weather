package com.aaa.bbb.ccc.weather.domain.map;

import android.util.Log;

import com.aaa.bbb.ccc.weather.data.model.BriefWeatherForecast;
import com.aaa.bbb.ccc.weather.domain.model.ShortForecast;
import com.aaa.bbb.ccc.weather.domain.model.Wind;
import com.aaa.bbb.ccc.weather.domain.utils.DateConverter;
import com.aaa.bbb.ccc.weather.domain.utils.WindTypeConverter;

import java.util.Calendar;

import rx.functions.Func1;

public class BriefWeatherForecastToShortForecastConverter implements Func1<BriefWeatherForecast, ShortForecast> {
    @Override
    public ShortForecast call(BriefWeatherForecast briefWeatherForecast) {
        ShortForecast shortForecast = new ShortForecast();
        shortForecast.setClouds(briefWeatherForecast.getClouds());
        Calendar date = DateConverter.getDateByInteger(briefWeatherForecast.getDate());
        Log.d("mihaHramov",date.toString());
        shortForecast.setDate(date);

        shortForecast.setHumidity(briefWeatherForecast.getHumidity());
        shortForecast.setPressure(briefWeatherForecast.getPressure());
        shortForecast.setSnow(briefWeatherForecast.getSnow());
        shortForecast.setWeatherType(briefWeatherForecast.getWeatherType());
        shortForecast.setTemperature(briefWeatherForecast.getTemperature());
        Wind wind = new Wind(WindTypeConverter.convert(briefWeatherForecast.getWind()),briefWeatherForecast.getWind().getSpeed());
        shortForecast.setWind(wind);
        return shortForecast;
    }
}
