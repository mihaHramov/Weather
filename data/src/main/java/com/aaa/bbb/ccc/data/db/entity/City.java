package com.aaa.bbb.ccc.data.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class City {
    @PrimaryKey
    private long id;
    private String name;
    private String country;
    private Double latSin;
    private Double latCos;
    private Double lonSin;
    private Double lonCos;

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