package com.aaa.bbb.ccc.data.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class City {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    @ColumnInfo(name = "city_id")
    private Integer idCity;
    @ColumnInfo(name = "lang_name")
    private String langName;
    private String country;
    private Double lat;
    private Double lon;
    private Double latSin;
    private Double latCos;
    private Double lonSin;
    private Double lonCos;

    public Integer getIdCity() {
        return idCity;
    }

    public void setIdCity(Integer idCity) {
        this.idCity = idCity;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public Double getLatSin() {
        return latSin;
    }

    public void setLatSin(Double latSin) {
        this.latSin = latSin;
    }

    public Double getLatCos() {
        return latCos;
    }

    public void setLatCos(Double latCos) {
        this.latCos = latCos;
    }

    public Double getLonSin() {
        return lonSin;
    }

    public void setLonSin(Double lonSin) {
        this.lonSin = lonSin;
    }

    public Double getLonCos() {
        return lonCos;
    }

    public void setLonCos(Double lonCos) {
        this.lonCos = lonCos;
    }
}
