package com.aaa.bbb.ccc.data;

import androidx.room.Room;

import com.aaa.bbb.ccc.data.db.WeatherDatabase;
import com.aaa.bbb.ccc.data.db.dao.CityDao;
import com.aaa.bbb.ccc.data.db.dao.ForecastDao;

import org.junit.After;
import org.junit.Before;
import org.robolectric.RuntimeEnvironment;

public class BaseDbTest {
    WeatherDatabase db;
    CityDao cityDao;
    ForecastDao forecastDao;

    @Before
    public void setUp() {
        db = Room.inMemoryDatabaseBuilder(
                RuntimeEnvironment.application,
                WeatherDatabase.class)
                .allowMainThreadQueries()
                .build();
        cityDao = db.getCityDao();
        forecastDao = db.getForecastDao();
    }

    @After
    public void tearDown() {
        db.close();
    }
}
