package com.aaa.bbb.ccc.data.model;

public class Wind {
    private Double speed;
    private Integer deg;

    public Wind(Double windSpeed, Integer deg) {
        this.speed = windSpeed;
        this.deg = deg;
    }

    public Wind() {
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getDeg() {
        return deg;
    }

    public void setDeg(Integer deg) {
        this.deg = deg;
    }
}
