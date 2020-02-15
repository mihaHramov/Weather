package com.aaa.bbb.ccc.model;

import java.io.Serializable;

public class Temperature implements Serializable {
    private Double max;
    private Double min;

    public Temperature(Double max, Double min) {
        this.max = max;
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double maxTemperature) {
        this.max = maxTemperature;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double minTemperature) {
        this.min = minTemperature;
    }

}
