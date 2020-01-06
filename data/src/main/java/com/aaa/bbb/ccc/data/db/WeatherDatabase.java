package com.aaa.bbb.ccc.data.db;

import androidx.room.Database;

import com.aaa.bbb.ccc.data.db.dao.CityDao;
import com.aaa.bbb.ccc.data.db.entity.City;

@Database(entities = {City.class}, version = 1)
public abstract  class WeatherDatabase {
    public abstract CityDao getCityDao();
}
