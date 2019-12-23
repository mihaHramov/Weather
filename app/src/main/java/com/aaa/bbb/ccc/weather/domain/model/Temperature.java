package com.aaa.bbb.ccc.weather.domain.model;

public class Temperature {
    private Double maxTemperature;
    private Double minTemperature;
    private Double midTemperature;

    public Temperature(Double maxTemperature, Double minTemperature, Double midTemperature) {
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.midTemperature = midTemperature;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Double getMidTemperature() {
        return midTemperature;
    }

    public void setMidTemperature(Double midTemperature) {
        this.midTemperature = midTemperature;
    }
}
