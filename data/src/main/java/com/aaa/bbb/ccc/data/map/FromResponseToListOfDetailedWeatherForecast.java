package com.aaa.bbb.ccc.data.map;

import com.aaa.bbb.ccc.data.model.api.weather.WeatherResponse;
import com.aaa.bbb.ccc.model.DailyForecast;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class FromResponseToListOfDetailedWeatherForecast implements Func1<WeatherResponse, Observable<List<DailyForecast>>> {
    @Override
    public Observable<List<DailyForecast>> call(WeatherResponse responseArg) {
        return Observable.from(responseArg.getList())
                .map(new FromListToBriefWeatherForecast())
                .compose(new ForecastTransformer());
    }
}
