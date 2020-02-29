package com.aaa.bbb.ccc.data.map;

import com.aaa.bbb.ccc.data.utils.DateConverter;
import com.aaa.bbb.ccc.model.DailyForecast;
import com.aaa.bbb.ccc.model.ShortForecast;

import java.util.Calendar;
import java.util.List;

import rx.Observable;

public class ForecastTransformer implements Observable.Transformer<ShortForecast, List<DailyForecast>> {
    @Override
    public Observable<List<DailyForecast>> call(Observable<ShortForecast> shortForecastObservable) {
        return shortForecastObservable
                .groupBy(list -> DateConverter.getDateByInteger(list.getDate()).get(Calendar.DAY_OF_MONTH))
                .flatMap(groupedObservable -> groupedObservable.toSortedList(this::compare))//сгрупировал и отсортировал  прогнозы в течении дня
                .map(DailyForecast::new)//собрал прогноз на день
                .toSortedList(this::compare);
    }


    private int compare(ShortForecast o1, ShortForecast o2) {
        return o1.getDate().compareTo(o2.getDate());
    }

    private int compare(DailyForecast o1, DailyForecast o2) {
        return o1.getPreview().getDate().compareTo(o2.getPreview().getDate());
    }
}
