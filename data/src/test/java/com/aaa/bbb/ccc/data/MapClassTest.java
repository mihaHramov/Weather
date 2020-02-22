package com.aaa.bbb.ccc.data;

import androidx.core.util.Pair;

import com.aaa.bbb.ccc.data.map.FromCityApiToPlace;
import com.aaa.bbb.ccc.data.map.TranslateLanguageMap;
import com.aaa.bbb.ccc.data.map.WindTypeConverter;
import com.aaa.bbb.ccc.data.map.ZipCityAndTranslateInfo;
import com.aaa.bbb.ccc.data.model.api.weather.Coord;
import com.aaa.bbb.ccc.data.model.api.weather.Wind;
import com.aaa.bbb.ccc.model.Place;
import com.aaa.bbb.ccc.model.WindType;

import org.junit.Assert;
import org.junit.Test;


public class MapClassTest {
    @Test
    public void testZipCityAndTranslateInfo() {
        Place place = new Place();
        ZipCityAndTranslateInfo map = new ZipCityAndTranslateInfo();
        Place result = map.call(new Pair<>("name", "ru"), place);
        Assert.assertEquals("name", result.getName());
    }

    @Test
    public void testTranslateLanguageMap() {
        String lang = "ru";
        String baseLang = "en";
        TranslateLanguageMap map = new TranslateLanguageMap(baseLang);
        String result = map.call(lang);
        Assert.assertEquals(baseLang + "-" + lang, result);
    }

    @Test
    public void testFromCityApiToCity() {
        com.aaa.bbb.ccc.data.model.api.weather.City city = new com.aaa.bbb.ccc.data.model.api.weather.City();
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
        FromCityApiToPlace map = new FromCityApiToPlace();
        Place result = map.call(city);
        Assert.assertEquals(city.getCountry(), result.getCountry());
    }

    @Test
    public void testWindType() {

        Wind wind = new Wind();
        wind.setSpeed(300.0);
        wind.setDeg(360);
        WindType result = WindTypeConverter.convert(wind);
        Assert.assertEquals(WindType.N, result);

        wind.setDeg(3);
        result = WindTypeConverter.convert(wind);
        Assert.assertEquals(WindType.NE, result);

        wind.setDeg(90);
        result = WindTypeConverter.convert(wind);
        Assert.assertEquals(WindType.E, result);
        wind.setDeg(100);
        result = WindTypeConverter.convert(wind);
        Assert.assertEquals(WindType.SE, result);

        wind.setDeg(165);
        result = WindTypeConverter.convert(wind);
        Assert.assertEquals(WindType.SW, result);

        wind.setDeg(160);
        result = WindTypeConverter.convert(wind);
        Assert.assertEquals(WindType.S, result);
        wind.setDeg(270);
        result = WindTypeConverter.convert(wind);
        Assert.assertEquals(WindType.W, result);
        wind.setDeg(290);
        result = WindTypeConverter.convert(wind);
        Assert.assertEquals(WindType.NW, result);
    }
}
