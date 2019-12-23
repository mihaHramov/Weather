package com.aaa.bbb.ccc.weather.data.model;

import java.io.Serializable;

public class WeatherType implements Serializable {
    private String description;
    private String icon;

    public WeatherType(String description, String icon) {
        this.description = description;
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
