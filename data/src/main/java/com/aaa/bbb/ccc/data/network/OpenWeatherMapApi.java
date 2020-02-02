package com.aaa.bbb.ccc.data.network;

import com.aaa.bbb.ccc.data.model.api.weather.WeatherResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface OpenWeatherMapApi {
    @GET("forecast/?type=like&appid=bbe1f99da2af5300d8384fb8d80a9bff")
    Observable<WeatherResponse> getForecast(@Query("lat") String lat, @Query("lon") String lon, @Query("lang") String lang, @Query("units") String units);
}
