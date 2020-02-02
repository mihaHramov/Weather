package com.aaa.bbb.ccc.data;

import androidx.core.util.Pair;

import com.aaa.bbb.ccc.data.map.FromCityApiToCity;
import com.aaa.bbb.ccc.data.map.TranslateLanguageMap;
import com.aaa.bbb.ccc.data.map.ZipCityAndTranslateInfo;
import com.aaa.bbb.ccc.data.model.City;
import com.aaa.bbb.ccc.data.model.api.weatherApi.Coord;

import org.junit.Assert;
import org.junit.Test;


public class MapClassTest {
    @Test
    public void ZipCityAndTranslateInfo_isCorrect() {
        City city = new City();
        ZipCityAndTranslateInfo map = new ZipCityAndTranslateInfo();
        City result = map.call(new Pair<>("name", "ru"), city);
        Assert.assertEquals("name", result.getName());
    }

    @Test
    public void TranslateLanguageMap_isCorrect() {
        String lang = "ru";
        String baseLang = "en";
        TranslateLanguageMap map = new TranslateLanguageMap(baseLang);
        String result = map.call(lang);
        Assert.assertEquals(baseLang + "-" + lang, result);
    }
    @Test
    public void FromCityApiToCity_isCorrect() {
        com.aaa.bbb.ccc.data.model.api.weatherApi.City city = new com.aaa.bbb.ccc.data.model.api.weatherApi.City();
        city.setId(0);
        city.setName("London");
        city.setCountry("uk");
        city.setPopulation(3000);
        city.setSunrise(102030);
        city.setSunset(203010);
        city.setTimezone(10000);
        Coord coord = new Coord();
        coord.setLat(10.3);
        coord.setLon(20.1);
        city.setCoord(coord);
        FromCityApiToCity map = new FromCityApiToCity();
        City result = map.call(city);
        Assert.assertEquals(city.getCoord().getLat(), result.getLat());
        Assert.assertEquals(city.getCoord().getLon(), result.getLon());
    }
}
