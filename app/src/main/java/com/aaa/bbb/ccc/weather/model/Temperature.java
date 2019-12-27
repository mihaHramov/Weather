package com.aaa.bbb.ccc.weather.model;

import java.io.Serializable;

public class Temperature implements Serializable {
    private String max;
    private String min;

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }
}
