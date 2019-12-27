package com.aaa.bbb.ccc.domain.model;

import java.io.Serializable;

public class Place implements Serializable {
    private String name;
    private String country;
    private String sunrise;
    private String sunset;

    public Place(String name, String country, String sunrise, String sunset) {
        this.name = name;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
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

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
}
