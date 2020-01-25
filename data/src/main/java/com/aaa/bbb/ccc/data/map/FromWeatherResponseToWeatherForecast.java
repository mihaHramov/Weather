package com.aaa.bbb.ccc.data.map;

import com.aaa.bbb.ccc.data.model.City;
import com.aaa.bbb.ccc.data.model.DetailedWeatherForecast;
import com.aaa.bbb.ccc.data.model.WeatherForecast;
import com.aaa.bbb.ccc.data.model.weatherApi.WeatherResponse;

import java.util.List;

import rx.functions.Func2;

public class FromWeatherResponseToWeatherForecast implements Func2<WeatherResponse, List<DetailedWeatherForecast>, WeatherForecast> {
    @Override
    public WeatherForecast call(WeatherResponse weatherResponse, List<DetailedWeatherForecast> detailedWeatherForecasts) {
        City city = new FromCityApiToCity().call(weatherResponse.getCity());
        return new WeatherForecast(city,detailedWeatherForecasts);
    }
}