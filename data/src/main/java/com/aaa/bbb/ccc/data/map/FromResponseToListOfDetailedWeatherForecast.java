package com.aaa.bbb.ccc.data.map;

import com.aaa.bbb.ccc.model.DailyForecast;
import com.aaa.bbb.ccc.data.model.api.weather.WeatherResponse;
import com.aaa.bbb.ccc.data.utils.DateConverter;

import java.util.Calendar;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class FromResponseToListOfDetailedWeatherForecast implements Func1<WeatherResponse, Observable<List<DailyForecast>>> {
    @Override
    public Observable<List<DailyForecast>> call(WeatherResponse responseArg) {
        return Observable.from(responseArg.getList())
                .map(new FromListToBriefWeatherForecast())
                .groupBy(list ->DateConverter.getDateByInteger(list.getDate()).get(Calendar.DAY_OF_MONTH))
                .flatMap(groupedObservable -> groupedObservable.sorted((o1, o2) -> o1.getDate().compareTo(o2.getDate())).toList(),//сгрупировал и отсортировал  прогнозы в течении дня
                        (grouped, shortForecasts) -> {//собрал прогноз на день
                            DailyForecast dailyForecast = new DailyForecast();
                            dailyForecast.setDate(shortForecasts.get(0).getDate());
                            dailyForecast.setShortForecasts(shortForecasts);
                            return dailyForecast;
                        })
                .sorted((detailedWeatherForecast, detailedWeatherForecast2) -> detailedWeatherForecast.getDate().compareTo(detailedWeatherForecast2.getDate()))//отсортировал прогнозы по дням
                .toList();
    }
}
