package com.aaa.bbb.ccc.data.map;


import com.aaa.bbb.ccc.model.ShortForecast;
import com.aaa.bbb.ccc.model.Temperature;
import com.aaa.bbb.ccc.model.WeatherType;
import com.aaa.bbb.ccc.data.model.api.weather.List;
import com.aaa.bbb.ccc.data.model.api.weather.Weather;
import com.aaa.bbb.ccc.model.Wind;
import com.aaa.bbb.ccc.model.WindType;
import com.aaa.bbb.ccc.data.utils.DateConverter;

import rx.functions.Func1;


public class FromListToBriefWeatherForecast implements Func1<List, ShortForecast> {

    @Override
    public ShortForecast call(List listWeatherItem) {
        ShortForecast shortForecast = new ShortForecast();
        shortForecast.setDate(listWeatherItem.getDt());
        com.aaa.bbb.ccc.data.model.api.weather.Wind windApiModel = listWeatherItem.getWind();
        Wind wind = new Wind(windApiModel.getSpeed(),windApiModel.getDeg());
        shortForecast.setWind(wind);

        if (listWeatherItem.getRain() != null) {
            shortForecast.setRain(listWeatherItem.getRain().get3h());
        }
        if (listWeatherItem.getSnow() != null) {
            shortForecast.setSnow(listWeatherItem.getSnow().get3h());
        }
        if (listWeatherItem.getClouds() != null && listWeatherItem.getClouds().getAll() != null) {
            shortForecast.setClouds(listWeatherItem.getClouds().getAll());
        }


        shortForecast.setHumidity(listWeatherItem.getMain().getHumidity());
        shortForecast.setPressure(listWeatherItem.getMain().getPressure());
        Temperature temperature = new Temperature(listWeatherItem.getMain().getTempMax(),listWeatherItem.getMain().getTempMin());
        shortForecast.setTemperature(temperature);
        Weather weather = listWeatherItem.getWeather().get(0);
        String weatherTypeIconUrlTemplate = "http://openweathermap.org/img/wn/%1$s@2x.png";
        String weatherIconUrl = String.format(weatherTypeIconUrlTemplate, weather.getIcon());
        WeatherType weatherType = new WeatherType(weather.getDescription(), weatherIconUrl);
        shortForecast.setWeatherType(weatherType);
        return shortForecast;
    }
}
