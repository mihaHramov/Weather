package com.aaa.bbb.ccc.domain.model;

import java.io.Serializable;

public class Temperature implements Serializable {
    private Double maxTemperature;
    private Double minTemperature;

    public Temperature(Double maxTemperature, Double minTemperature) {
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
    }

    public Double getMax() {
        return maxTemperature;
    }

    public void setMax(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Double getMin() {
        return minTemperature;
    }

    public void setMin(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

}
