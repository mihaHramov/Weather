package com.aaa.bbb.ccc.data.model;

public class Location {
    private String lat;

    public String getLat() {
        return lat;
    }

    public String getLot() {
        return lot;
    }

    public Location(String lat, String lot) {
        this.lat = lat;
        this.lot = lot;
    }

    private String lot;


}
