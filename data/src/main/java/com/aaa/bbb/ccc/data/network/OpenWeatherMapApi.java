package com.aaa.bbb.ccc.data.network;

import com.aaa.bbb.ccc.data.model.weatherApi.WeatherResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface OpenWeatherMapApi {
    String BASE_LANGUAGE = "en";
    String BASE_URL= "http://api.openweathermap.org/data/2.5/";
    @GET("forecast/?type=like&appid=bbe1f99da2af5300d8384fb8d80a9bff")
    Observable<WeatherResponse> getForecast(@Query("lat") String lat, @Query("lon") String lon, @Query("lang") String lang, @Query("units") String units);

}
