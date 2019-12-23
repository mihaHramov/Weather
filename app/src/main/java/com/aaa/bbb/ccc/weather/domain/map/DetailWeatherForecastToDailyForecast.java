package com.aaa.bbb.ccc.weather.domain.map;

import com.aaa.bbb.ccc.weather.data.model.DetailedWeatherForecast;
import com.aaa.bbb.ccc.weather.domain.model.DailyForecast;
import com.aaa.bbb.ccc.weather.domain.model.ShortForecast;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class DetailWeatherForecastToDailyForecast implements Func1<DetailedWeatherForecast, Observable<DailyForecast>> {
    @Override
    public Observable<DailyForecast> call(DetailedWeatherForecast detailedWeatherForecast) {
        DailyForecast forecast = new DailyForecast();
        forecast.setDate(detailedWeatherForecast.getDate().toString());
        Observable<DailyForecast> date = Observable.just(forecast);

        Observable<List<ShortForecast>> listShortForecast = Observable.from(detailedWeatherForecast.getBriefWeatherForecasts())
                .map(new BriefWeatherForecastToShortForecastConverter())
                .toList();
        return date.zipWith(listShortForecast, (dailyForecast, shortForecasts) -> {
            dailyForecast.setShortForecasts(shortForecasts);
            return dailyForecast;
        });
    }
}
