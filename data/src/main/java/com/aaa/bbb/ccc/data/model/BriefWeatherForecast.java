package com.aaa.bbb.ccc.data.model;


import com.aaa.bbb.ccc.data.model.weatherApi.Wind;

public class BriefWeatherForecast {
    private Double snow;
    private Wind wind;
    private Integer clouds;
    private WeatherType weatherType;
    private Integer humidity;
    private Integer pressure;
    private Integer date;
    private Temperature temperature;


    public Temperature getTemperature() {
        return temperature;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Double getRain() {
        return rain;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }

    private Double rain;

    public Double getSnow() {
        return snow;
    }

    public void setSnow(Double snow) {
        this.snow = snow;
    }

    public Integer getClouds() {
        return clouds;
    }

    public void setClouds(Integer cloudsPercent) {
        this.clouds = cloudsPercent;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setDate(Integer date) {
        this.date = date;
    }
    public Integer getDate() {
        return date;
    }
}
