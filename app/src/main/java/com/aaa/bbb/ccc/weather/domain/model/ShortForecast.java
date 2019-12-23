package com.aaa.bbb.ccc.weather.domain.model;

import com.aaa.bbb.ccc.weather.data.model.Temperature;
import com.aaa.bbb.ccc.weather.data.model.WeatherType;

import java.io.Serializable;
import java.util.Calendar;

public class ShortForecast implements Serializable {
    private Calendar date;
    private Double snow;
    private Wind wind;
    private Integer clouds;
    private WeatherType weatherType;
    private Integer humidity;
    private Integer pressure;
    private Temperature temperature;

    public Temperature getTemperature() {
        return temperature;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Double getSnow() {
        return snow;
    }

    public void setSnow(Double snow) {
        this.snow = snow;
    }


    public Integer getClouds() {
        return clouds;
    }

    public void setClouds(Integer clouds) {
        this.clouds = clouds;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }
}
