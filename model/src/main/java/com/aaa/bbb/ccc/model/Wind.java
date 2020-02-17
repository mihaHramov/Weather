package com.aaa.bbb.ccc.model;

import java.io.Serializable;

public class Wind implements Serializable {
    private WindType windType;
    private Double windSpeed;
    private Integer deg;

    public Wind(WindType type, Double speed) {
        windType = type;
        windSpeed = speed;
    }

    public Integer getDeg() {
        return deg;
    }

    public void setDeg(Integer deg) {
        this.deg = deg;
    }

    public WindType getWindType() {
        return windType;
    }

    public void setWindType(WindType windType) {
        this.windType = windType;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }
}
