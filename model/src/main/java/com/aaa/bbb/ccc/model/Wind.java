package com.aaa.bbb.ccc.model;

import java.io.Serializable;

public class Wind implements Serializable {
    private Double windSpeed;
    private Integer deg;

    public Wind(Double speed , Integer deg) {
        this.windSpeed = speed;
        this.deg = deg;
    }

    public Wind() {
    }

    public Integer getDeg() {
        return deg;
    }

    public WindType getWindType() {
        return WindTypeConverter.convert(deg);
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setDeg(Integer deg) {
        this.deg = deg;
    }
}
