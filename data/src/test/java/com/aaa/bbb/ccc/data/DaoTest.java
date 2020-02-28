package com.aaa.bbb.ccc.data;

import com.aaa.bbb.ccc.data.model.entity.City;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

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

        City cityRome = getCity("Rome", "ua", "ru", 23.3, 23.3, 1);
        cityDao.insert(cityRome);//id = 1

        City cityParis = getCity("Paris", "ua", "ru", 23.3, 23.3, 2);//id = 2
        cityDao.insert(cityParis);

        City cityLondon = getCity("London", "ua", "ru", 23.3, 23.3, 3);
        cityDao.insert(cityLondon);//id 3

        forecastDao.insert(getForecast(1, date));
        forecastDao.insert(getForecast(2, date));
        forecastDao.insert(getForecast(3, date));

        City result = cityDao.getCityByCoordinates(cityRome.getLatSin(), cityRome.getLatCos(), cityRome.getLonSin(), cityRome.getLonCos(), date);
        Assert.assertEquals(cityRome.getName(), result.getName());
    }


}