package com.aaa.bbb.ccc.weather.map;

import com.aaa.bbb.ccc.domain.model.Place;
import com.aaa.bbb.ccc.domain.model.SynopticForecast;
import com.aaa.bbb.ccc.weather.model.ShortForecast;
import com.aaa.bbb.ccc.weather.model.Temperature;
import com.aaa.bbb.ccc.weather.model.Wind;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class SynopticMapper implements Func1<SynopticForecast, Observable<com.aaa.bbb.ccc.weather.model.SynopticForecast>> {
    @Override
    public Observable<com.aaa.bbb.ccc.weather.model.SynopticForecast> call(SynopticForecast synopticForecast) {
        Observable<String> place = Observable.just(synopticForecast.getPlace())
                .map(Place::getName);
        return Observable
                .from(synopticForecast.getDailyForecast())
                .map(dailyForecast -> {
                    com.aaa.bbb.ccc.weather.model.DailyForecast dailyForecast1 = new com.aaa.bbb.ccc.weather.model.DailyForecast();
                    dailyForecast1.setDate(dailyForecast.getDate());
                    List<ShortForecast> shortForecastList = new ArrayList<>();
                    for (com.aaa.bbb.ccc.domain.model.ShortForecast shortForecast : dailyForecast.getShortForecasts()) {
                        shortForecastList.add(getShortForecast(shortForecast));
                    }
                    dailyForecast1.setShortForecasts(shortForecastList);
                    return dailyForecast1;
                })
                .toList()
                .zipWith(place, (dailyForecasts, place1) -> {
                    com.aaa.bbb.ccc.weather.model.SynopticForecast forecast = new com.aaa.bbb.ccc.weather.model.SynopticForecast();
                    forecast.setDailyForecast(dailyForecasts);
                    forecast.setPlace(place1);
                    return forecast;
                });
    }

    private com.aaa.bbb.ccc.weather.model.ShortForecast getShortForecast(com.aaa.bbb.ccc.domain.model.ShortForecast shortForecast) {
        com.aaa.bbb.ccc.weather.model.ShortForecast shortForecastUi = new com.aaa.bbb.ccc.weather.model.ShortForecast();
        int hour = shortForecast.getDate().get(Calendar.HOUR);
        int minute = shortForecast.getDate().get(Calendar.MINUTE);
        String date = hour + ":" + minute;
        shortForecastUi.setDate(date);
        shortForecastUi.setClouds(shortForecast.getClouds().toString());
        shortForecastUi.setHumidity(shortForecast.getHumidity().toString());
        shortForecastUi.setIcon(shortForecast.getWeatherType().getIcon());
        shortForecastUi.setDescription(shortForecast.getWeatherType().getDescription());
        shortForecastUi.setPressure(shortForecast.getPressure().toString());
        if (shortForecast.getRain() != null) {
            shortForecastUi.setRain(shortForecast.getRain().toString());
        }
        if (shortForecast.getSnow() != null) {
            shortForecastUi.setSnow(shortForecast.getSnow().toString());
        }
        shortForecastUi.setPrecipitation(shortForecast.getPrecipitation().toString());
        if (shortForecast.getWind() != null) {
            Wind wind = new Wind();
            wind.setSpeed(shortForecast.getWind().getWindSpeed().toString());
            wind.setName(getName(shortForecast));
            shortForecastUi.setWind(wind);
        }
        if (shortForecast.getTemperature() != null) {
            com.aaa.bbb.ccc.domain.model.Temperature temperature = shortForecast.getTemperature();
            Temperature temperatureUi = new Temperature();
            temperatureUi.setMax(temperature.getMax().toString());
            temperatureUi.setMin(temperature.getMin().toString());
            shortForecastUi.setTemperature(temperatureUi);
        }

        return shortForecastUi;
    }

    private String getName(com.aaa.bbb.ccc.domain.model.ShortForecast shortForecast) {
        switch (shortForecast.getWind().getWindType()) {
            case E:
                return "Восток";
            case N:
                return "Север";
            case S:
                return "Юг";
            case W:
                return "Запад";
            case NE:
                return "Северо-Восток";
            case NW:
                return "Северо-Запад";
            case SE:
                return "Юго-Восток";
            case SW:
                return "Юго-Запад";
            default:
                return "";

        }
    }
}
