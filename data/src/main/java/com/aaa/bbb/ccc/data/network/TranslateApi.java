package com.aaa.bbb.ccc.data.network;

import com.aaa.bbb.ccc.data.model.api.translate.TranslateResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface TranslateApi {
    class Const {
        Const() {
            throw new IllegalStateException("Utility class");
        }
        public static final String OPEN_WEATHER_MAP_API_BASE_LANGUAGE = "en";
    }
    @GET("translate?key=trnsl.1.1.20191203T201408Z.4f47aaac61038623.808706d69ca874e035b1c7a8b730e0a9e60f9136")
    Observable<TranslateResponse> getTranslate(@Query("text") String text, @Query("lang") String lang);
}
