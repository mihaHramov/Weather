package com.aaa.bbb.ccc.data.repository.cash;

import com.aaa.bbb.ccc.data.db.WeatherDatabase;
import com.aaa.bbb.ccc.data.db.dao.CityDao;
import com.aaa.bbb.ccc.data.db.dao.ForecastDao;
import com.aaa.bbb.ccc.data.map.ForecastTransformer;
import com.aaa.bbb.ccc.data.map.FromCityEntryToCity;
import com.aaa.bbb.ccc.data.map.FromCityToCityEntity;
import com.aaa.bbb.ccc.data.map.FromEntryForecastToForecast;
import com.aaa.bbb.ccc.data.map.FromWeatherForecastToForecastEntity;
import com.aaa.bbb.ccc.data.model.entity.City;
import com.aaa.bbb.ccc.data.model.entity.Forecast;
import com.aaa.bbb.ccc.model.Place;
import com.aaa.bbb.ccc.model.SynopticForecast;

import rx.Observable;

public class CashRepository implements ICashRepository {
    private CityDao cityDao;
    private ForecastDao forecastDao;

    public CashRepository(WeatherDatabase weatherDataBase) {
        cityDao = weatherDataBase.getCityDao();
        forecastDao = weatherDataBase.getForecastDao();
    }

    @Override
    public Observable<Place> getCity(Integer id, String lang) {
        return Observable.just(cityDao.getByIdAndLanguage(id, lang)).map(new FromCityEntryToCity());
    }

    @Override
    public void saveCity(Place place) {
        cityDao.insert(new FromCityToCityEntity().call(place));
    }

    @Override
    public void saveWeatherForecast(SynopticForecast synopticForecast) {
        FromWeatherForecastToForecastEntity map = new FromWeatherForecastToForecastEntity();
        for (Forecast forecast : map.call(synopticForecast)) {
            forecastDao.insert(forecast);
        }
    }

    private Observable<Double> parseCoord(String coord) {
        return Observable.just(coord).map(Double::parseDouble);
    }

    private City getCity(Double latDouble, Double lonDouble, Integer date) {
        return cityDao.getCityByCoordinates(Math.sin(latDouble), Math.cos(latDouble), Math.sin(lonDouble), Math.cos(lonDouble), date);
    }

    @Override
    public Observable<SynopticForecast> getWeatherForecast(String lat, String lon, Integer date) {
        Observable<Integer> dateObservable = Observable.just(date);
        Observable<Place> placeObservable = Observable.combineLatest(parseCoord(lat), parseCoord(lon), dateObservable, this::getCity)
                .map(new FromCityEntryToCity());
        return Observable.zip(placeObservable, dateObservable, (place, integer) -> forecastDao.getForecastByCityIdAndDate(place.getId(), integer))
                .flatMap(Observable::from)
                .map(new FromEntryForecastToForecast())
                .compose(new ForecastTransformer())
                .zipWith(placeObservable, (dailyForecasts, place) -> new SynopticForecast(place, dailyForecasts));
    }
}
