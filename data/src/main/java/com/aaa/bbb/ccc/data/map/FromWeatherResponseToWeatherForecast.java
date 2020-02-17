package com.aaa.bbb.ccc.data.map;

import com.aaa.bbb.ccc.model.Place;
import com.aaa.bbb.ccc.model.DailyForecast;
import com.aaa.bbb.ccc.model.SynopticForecast;
import com.aaa.bbb.ccc.data.model.api.weather.WeatherResponse;

import java.util.List;

import rx.functions.Func2;

public class FromWeatherResponseToWeatherForecast implements Func2<WeatherResponse, List<DailyForecast>, SynopticForecast> {
    @Override
    public SynopticForecast call(WeatherResponse weatherResponse, List<DailyForecast> dailyForecasts) {
        Place place = new FromCityApiToPlace().call(weatherResponse.getCity());
        return new SynopticForecast(place, dailyForecasts);
    }
}