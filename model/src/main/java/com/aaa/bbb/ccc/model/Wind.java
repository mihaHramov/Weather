package com.aaa.bbb.ccc.model;

import java.io.Serializable;

public class Wind implements Serializable {
    private WindType windType;
    private Double windSpeed;

    public Wind(WindType type, Double speed) {
        windType = type;
        windSpeed = speed;
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
