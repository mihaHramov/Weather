package com.aaa.bbb.ccc.weather.data.map;

import com.aaa.bbb.ccc.weather.data.model.BriefWeatherForecast;
import com.aaa.bbb.ccc.weather.data.model.DetailedWeatherForecast;
import com.aaa.bbb.ccc.weather.data.model.weatherApi.WeatherResponce;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

public class FromResponseToListOfDetailedWeatherForecast implements Func1<WeatherResponce, Observable<List<DetailedWeatherForecast>>> {
    @Override
    public Observable<List<DetailedWeatherForecast>> call(WeatherResponce responseArg) {
        return Observable.just(responseArg)
                .map(WeatherResponce::getList)
                .map(new FromResponseToHasTableOfBriefWeatherForecast())
                .flatMap((Func1<Map<Integer, List<BriefWeatherForecast>>, Observable<Map.Entry<Integer, List<BriefWeatherForecast>>>>) integerListMap -> Observable.from(integerListMap.entrySet()))
                .map(new FromEntrySetOfIntegerAndListOfForecastToListOfDetailedWeatherForecast())
                .sorted((detailedWeatherForecast, detailedWeatherForecast2) -> detailedWeatherForecast.getDate().compareTo(detailedWeatherForecast2.getDate()))
                .toList();
    }
}
