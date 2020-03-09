package com.aaa.bbb.ccc.data;

import com.aaa.bbb.ccc.data.model.entity.City;
import com.aaa.bbb.ccc.data.model.entity.Forecast;
import com.aaa.bbb.ccc.model.Temperature;
import com.aaa.bbb.ccc.model.WeatherType;
import com.aaa.bbb.ccc.model.Wind;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import static com.aaa.bbb.ccc.data.DbTestHelper.getCity;
import static com.aaa.bbb.ccc.data.DbTestHelper.getForecast;

@RunWith(RobolectricTestRunner.class)
public class DaoTest extends BaseDbTest {

    @Test
    public void insertTest() {
        City city = getCity("Rome", "ua", "ru", 23.3, 23.3, 1213133123);
        cityDao.insert(city);
        City result = cityDao.getByIdAndLanguage(city.getIdCity(), city.getLangName());
        Assert.assertEquals(city.getIdCity(), result.getIdCity());
        Assert.assertEquals(city.getCountry(), result.getCountry());
        Assert.assertEquals(city.getName(), result.getName());
        Assert.assertEquals(city.getLangName(), "ru");
    }

    @Test
    public void getByIdAndLanguageTest() {
        City city = getCity("Rome", "ua", "ru", 23.3, 23.3, 1213133123);
        cityDao.insert(city);
        city.setIdCity(13);
        cityDao.insert(city);
        city.setLangName("en");
        city.setIdCity(12);
        cityDao.insert(city);
        City cityResult = cityDao.getByIdAndLanguage(12, "en");
        Assert.assertEquals(city.getLangName(), cityResult.getLangName());
        Assert.assertEquals(city.getIdCity(), cityResult.getIdCity());
        Assert.assertEquals(3, cityResult.getId());
    }

    @Test
    public void getCityByCoordinates() {
        Integer date = 1574812800;

        City cityRome = getCity("Rome", "ua", "ru", Double.valueOf("3.3"), Double.valueOf("3.3"), 1);
        cityDao.insert(cityRome);//id = 1

        City cityParis = getCity("Paris", "ua", "ru", 28.3, 73.3, 2);//id = 2
        cityDao.insert(cityParis);

        City cityLondon = getCity("London", "ua", "ru", 13.3, 13.3, 3);
        cityDao.insert(cityLondon);//id 3

        forecastDao.insert(getForecast(1, date));
        forecastDao.insert(getForecast(2, date));
        forecastDao.insert(getForecast(3, date));

        City result = cityDao.getCityByCoordinates(cityRome.getLatSin(), cityRome.getLatCos(), cityRome.getLonSin(), cityRome.getLonCos(), date);
        Assert.assertEquals(cityRome.getName(), result.getName());
    }

    @Test
    public void insertForecast() {
        Integer cityId = 3;
        City cityLondon = getCity("London", "ua", "ru", 23.3, 23.3, cityId);
        cityDao.insert(cityLondon);//id 3
        Integer date = 1574812800;
        Double doubleVal = 10.0;
        Integer intVal = 10;
        Forecast forecast = getForecast(cityId, date);
        forecast.setRain(doubleVal);
        forecast.setPressure(intVal);
        forecast.setClouds(intVal);
        forecast.setSnow(doubleVal);
        Temperature temperature = new Temperature(doubleVal, doubleVal);
        forecast.setTemperature(temperature);
        WeatherType weatherType = new WeatherType("description", "icon.png");
        forecast.setWeatherType(weatherType);
        Wind wind = new Wind(doubleVal, intVal);
        forecast.setWind(wind);
        forecastDao.insert(forecast);
        forecast.setDate(date+1);
        forecastDao.insert(forecast);
        forecast.setDate(date+3);
        forecastDao.insert(forecast);
        List<Forecast> resultList = forecastDao.getForecastByCityIdAndDate(cityId, date);
        Assert.assertEquals(3,resultList.size());
        Forecast resuilt = resultList.get(0);
        Assert.assertEquals(resuilt.getRain(),doubleVal);
        Assert.assertEquals(resuilt.getSnow(),doubleVal);
        Assert.assertEquals(resuilt.getTemperature().getMax(),temperature.getMax());
        Assert.assertEquals(resuilt.getTemperature().getMin(),temperature.getMin());
        Assert.assertEquals(resuilt.getWind().getWindSpeed(),wind.getWindSpeed());
        Assert.assertEquals(resuilt.getWind().getDeg(),wind.getDeg());
        Assert.assertEquals(resuilt.getWeatherType().getDescription(),weatherType.getDescription());
        Assert.assertEquals(resuilt.getWeatherType().getIcon(),weatherType.getIcon());
    }
}