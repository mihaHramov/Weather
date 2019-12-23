package com.aaa.bbb.ccc.weather.data.map;

import com.aaa.bbb.ccc.weather.data.model.BriefWeatherForecast;
import com.aaa.bbb.ccc.weather.data.model.DetailedWeatherForecast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import rx.functions.Func1;

public class FromEntrySetOfIntegerAndListOfForecastToListOfDetailedWeatherForecast implements Func1<Map.Entry<Integer, List<BriefWeatherForecast>>, DetailedWeatherForecast> {
    @Override
    public DetailedWeatherForecast call(Map.Entry<Integer, List<BriefWeatherForecast>> integerListEntry) {
        DetailedWeatherForecast detailedWeatherForecast = new DetailedWeatherForecast();
        detailedWeatherForecast.setDate(integerListEntry.getKey());
        List<BriefWeatherForecast> list = integerListEntry.getValue();
        Collections.sort(list, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        detailedWeatherForecast.setBriefWeatherForecasts(list);//
        return detailedWeatherForecast;
    }
}
