package com.aaa.bbb.ccc.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.aaa.bbb.ccc.data.db.entity.City;

@Dao
public interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(City employee);

    @Query("SELECT * FROM city WHERE city_id=:id and lang_name like :lang")
    City getByIdAndLanguage(long  id,String lang);

    @Query("SELECT *," +
            "(latSin*:sinLat+latCos*:cosLat*(lonSin*:sinLon+lonCos*:cosLon))\n" +
            "AS distance\n" +
            "FROM city WHERE id IN(SELECT city_id FROM forecast WHERE date>=:date )\n" +
            "ORDER BY distance DESC\n"+
            "LIMIT 1;" )
    City getCityByCoordinates(Double sinLat, Double cosLat,Double sinLon,Double cosLon,Integer date);
}
