package com.aaa.bbb.ccc.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.aaa.bbb.ccc.data.model.entity.Forecast;

import java.util.List;

@Dao
public interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Forecast forecast);

    @Query("SELECT * " +
            "FROM forecast WHERE city_id = :id AND date>=:date; ")
    List<Forecast> getForecastByCityIdAndDate(Integer id, Integer date);
}
