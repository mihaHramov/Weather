package com.aaa.bbb.ccc.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.aaa.bbb.ccc.data.model.Temperature;
import com.aaa.bbb.ccc.data.model.WeatherType;
import com.aaa.bbb.ccc.data.model.weatherApi.Wind;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = City.class,parentColumns = "id",childColumns = "city_id",onDelete = CASCADE))
public class Forecast {
    @PrimaryKey
    private Integer date;
    private Double snow;
    private Integer clouds;
    private Integer humidity;
    private Integer pressure;

    @ColumnInfo(name = "city_id")
    private long cityId;

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    @Embedded
    private Wind wind;
    @Embedded
    private Temperature temperature;
    @Embedded
    private WeatherType weatherType;

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

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
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

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }
}