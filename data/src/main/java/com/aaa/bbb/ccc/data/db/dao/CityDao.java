package com.aaa.bbb.ccc.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.aaa.bbb.ccc.data.db.entity.City;

@Dao
public interface CityDao {

    @Insert
    void insert(City employee);
}
