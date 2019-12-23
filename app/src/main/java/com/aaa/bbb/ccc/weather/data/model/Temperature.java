package com.aaa.bbb.ccc.weather.data.model;

import java.io.Serializable;

public class Temperature implements Serializable {
    private Double min;
    private Double max;
    private Double middle;

    public Temperature(Double min, Double max, Double middle) {
        this.min = min;
        this.max = max;
        this.middle = middle;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMiddle() {
        return middle;
    }

    public void setMiddle(Double middle) {
        this.middle = middle;
    }
}
