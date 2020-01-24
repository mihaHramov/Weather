package com.aaa.bbb.ccc.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.aaa.bbb.ccc.data.db.dao.CityDao;
import com.aaa.bbb.ccc.data.db.dao.ForecastDao;
import com.aaa.bbb.ccc.data.db.entity.City;
import com.aaa.bbb.ccc.data.db.entity.Forecast;

@Database(entities = {City.class, Forecast.class}, version = 1,exportSchema = false)
public abstract  class WeatherDatabase extends RoomDatabase {
    public static final String NAME = WeatherDatabase.class.getName();
    public abstract CityDao getCityDao();
    public abstract ForecastDao getForecastDao();
}
