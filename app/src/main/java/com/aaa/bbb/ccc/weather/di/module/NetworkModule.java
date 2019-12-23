package com.aaa.bbb.ccc.weather.di.module;

import com.aaa.bbb.ccc.weather.data.network.OpenWeatherMapApi;
import com.aaa.bbb.ccc.weather.data.network.TranslateApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    @Provides
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    GsonConverterFactory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    OpenWeatherMapApi provideOpenWeatherMapApi(GsonConverterFactory gsonConverterFactory,OkHttpClient  client) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(OpenWeatherMapApi.BASE_URL)
                .client(client)
                .build();//базовый url
        return retrofit.create(OpenWeatherMapApi.class);
    }

    @Provides
    TranslateApi provieTranslateApi(GsonConverterFactory gsonConverterFactory,OkHttpClient  client){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(TranslateApi.BASE_URL)
                .client(client)
                .build();//базовый url
        return retrofit.create(TranslateApi.class);
    }
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        return   interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    OkHttpClient provieOkhttpClient(HttpLoggingInterceptor interceptor){
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }
}
