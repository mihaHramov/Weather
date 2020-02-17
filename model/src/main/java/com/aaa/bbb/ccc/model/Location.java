package com.aaa.bbb.ccc.model;

import java.io.Serializable;

public class Location implements Serializable {
    private String lat;
    private String lot;

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




}
