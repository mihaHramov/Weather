package com.aaa.bbb.ccc.data.map;


import com.aaa.bbb.ccc.data.model.BriefWeatherForecast;
import com.aaa.bbb.ccc.data.model.Temperature;
import com.aaa.bbb.ccc.data.model.WeatherType;
import com.aaa.bbb.ccc.data.model.weatherApi.List;
import com.aaa.bbb.ccc.data.model.weatherApi.Weather;

import rx.functions.Func1;


public class FromListToBriefWeatherForecast implements Func1<List, BriefWeatherForecast> {

    @Override
    public BriefWeatherForecast call(List listWeatherItem) {
        BriefWeatherForecast briefWeatherForecast = new BriefWeatherForecast();
        briefWeatherForecast.setDate(listWeatherItem.getDt());
        briefWeatherForecast.setWind(listWeatherItem.getWind());

        if (listWeatherItem.getRain() != null) {
            briefWeatherForecast.setRain(listWeatherItem.getRain().get3h());
        }
        if (listWeatherItem.getSnow() != null) {
            briefWeatherForecast.setSnow(listWeatherItem.getSnow().get3h());
        }
        if (listWeatherItem.getClouds() != null && listWeatherItem.getClouds().getAll() != null) {
            briefWeatherForecast.setClouds(listWeatherItem.getClouds().getAll());
        }


        briefWeatherForecast.setHumidity(listWeatherItem.getMain().getHumidity());
        briefWeatherForecast.setPressure(listWeatherItem.getMain().getPressure());
        Temperature temperature = new Temperature(listWeatherItem.getMain().getTempMin(), listWeatherItem.getMain().getTempMax(), listWeatherItem.getMain().getTemp());
        briefWeatherForecast.setTemperature(temperature);
        Weather weather = listWeatherItem.getWeather().get(0);
        String weatherTypeIconUrlTemplate = "http://openweathermap.org/img/wn/%1$s@2x.png";
        String weatherIconUrl = String.format(weatherTypeIconUrlTemplate, weather.getIcon());
        WeatherType weatherType = new WeatherType(weather.getDescription(), weatherIconUrl);
        briefWeatherForecast.setWeatherType(weatherType);
        return briefWeatherForecast;
    }
}
