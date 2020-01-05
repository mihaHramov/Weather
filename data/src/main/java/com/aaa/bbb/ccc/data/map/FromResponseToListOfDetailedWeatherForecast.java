package com.aaa.bbb.ccc.data.map;

import com.aaa.bbb.ccc.data.model.BriefWeatherForecast;
import com.aaa.bbb.ccc.data.model.DetailedWeatherForecast;
import com.aaa.bbb.ccc.data.model.weatherApi.WeatherResponse;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

public class FromResponseToListOfDetailedWeatherForecast implements Func1<WeatherResponse, Observable<List<DetailedWeatherForecast>>> {
    @Override
    public Observable<List<DetailedWeatherForecast>> call(WeatherResponse responseArg) {
        return Observable.just(responseArg)
                .map(WeatherResponse::getList)
                .map(new FromResponseToHasTableOfBriefWeatherForecast())
                .flatMap((Func1<Map<Integer, List<BriefWeatherForecast>>, Observable<Map.Entry<Integer, List<BriefWeatherForecast>>>>) integerListMap -> Observable.from(integerListMap.entrySet()))
                .map(new FromEntrySetOfIntegerAndListOfForecastToListOfDetailedWeatherForecast())
                .sorted((detailedWeatherForecast, detailedWeatherForecast2) -> detailedWeatherForecast.getDate().compareTo(detailedWeatherForecast2.getDate()))
                .toList();
    }
}
