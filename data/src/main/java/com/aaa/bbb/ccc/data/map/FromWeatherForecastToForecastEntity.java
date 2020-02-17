package com.aaa.bbb.ccc.data.map;

import com.aaa.bbb.ccc.data.model.entity.Forecast;
import com.aaa.bbb.ccc.model.ShortForecast;
import com.aaa.bbb.ccc.model.DailyForecast;
import com.aaa.bbb.ccc.model.SynopticForecast;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

public class FromWeatherForecastToForecastEntity implements Func1<SynopticForecast, List<Forecast>> {
    @Override
    public List<Forecast> call(SynopticForecast synopticForecast) {
        List<Forecast> resultList = new ArrayList<>();
        FromBriefForecastToForecastEntity map = new FromBriefForecastToForecastEntity();
        for (DailyForecast dailyForecast : synopticForecast.getDailyForecast()) {
            for (ShortForecast shortForecast : dailyForecast.getShortForecasts()) {
                resultList.add(map.call(shortForecast, synopticForecast.getPlace()));
            }
        }
        return resultList;
    }
}
