package com.aaa.bbb.ccc.data.network;

import com.aaa.bbb.ccc.data.model.translateApi.TranslateResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface TranslateApi {
    String BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/";
    @GET("translate?key=trnsl.1.1.20191203T201408Z.4f47aaac61038623.808706d69ca874e035b1c7a8b730e0a9e60f9136")
    Observable<TranslateResponse> getTranslate(@Query("text") String text, @Query("lang") String lang);
}
