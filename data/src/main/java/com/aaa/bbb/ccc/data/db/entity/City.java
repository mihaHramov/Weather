package com.aaa.bbb.ccc.data.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class City {
    @PrimaryKey
    public long id;
    public String name;
    public String country;
    public int serverId;
    public Double lat;
    public Double lon;
}
