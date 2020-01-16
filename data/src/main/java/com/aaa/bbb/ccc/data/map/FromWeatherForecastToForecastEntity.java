package com.aaa.bbb.ccc.data.map;

import com.aaa.bbb.ccc.data.db.entity.Forecast;
import com.aaa.bbb.ccc.data.model.BriefWeatherForecast;
import com.aaa.bbb.ccc.data.model.DetailedWeatherForecast;
import com.aaa.bbb.ccc.data.model.WeatherForecast;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

public class FromWeatherForecastToForecastEntity implements Func1<WeatherForecast, List<Forecast>> {
    @Override
    public List<Forecast> call(WeatherForecast weatherForecast) {
        List<Forecast> resultList = new ArrayList<>();
        FromBriefForecastToForecastEntity map = new FromBriefForecastToForecastEntity();
        for (DetailedWeatherForecast detailedWeatherForecast : weatherForecast.getForecasts()) {
            for (BriefWeatherForecast briefWeatherForecast : detailedWeatherForecast.getBriefWeatherForecasts()) {
                resultList.add(map.call(briefWeatherForecast,weatherForecast.getLocality()));
            }
        }
        return resultList;
    }
}
