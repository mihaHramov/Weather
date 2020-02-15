package com.aaa.bbb.ccc.domain.model;

import com.aaa.bbb.ccc.model.WeatherType;
import com.aaa.bbb.ccc.model.Temperature;
import com.aaa.bbb.ccc.model.Wind;
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
    private Double rain;

    public Double getRain() {
        return rain;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }

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

    public Double getPrecipitation() {
        if (snow > 0)
            return snow;
        else if (rain > 0) {
            return rain;
        }
        return 0.0;
    }
}
