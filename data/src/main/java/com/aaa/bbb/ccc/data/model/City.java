package com.aaa.bbb.ccc.data.model;

import java.io.Serializable;

public class City implements Serializable {
    private Integer id;
    private String name;
    private String langName;
    private String country;
    private Integer sunrise;
    private Integer sunset;
    private Double lat;
    private Double lon;
    public Integer getSunrise() {
        return sunrise;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return this.lat;
    }
    public Double getLon() {
        return this.lon;
    }
}
