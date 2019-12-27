package com.aaa.bbb.ccc.data;

import android.content.Context;

import com.aaa.bbb.ccc.data.network.OpenWeatherMapApi;
import com.aaa.bbb.ccc.data.network.TranslateApi;
import com.aaa.bbb.ccc.data.repository.impl.LocationRepository;
import com.aaa.bbb.ccc.data.repository.impl.PermissionsRepository;
import com.aaa.bbb.ccc.data.repository.impl.SchedulerRepository;
import com.aaa.bbb.ccc.data.repository.impl.SettingsRepository;
import com.aaa.bbb.ccc.data.repository.impl.WeatherForecastRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ILocationRepository;
import com.aaa.bbb.ccc.data.repository.intrf.IPermissionsRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ISchedulerRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ISettingsRepository;
import com.aaa.bbb.ccc.data.repository.intrf.IWeatherForecastRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tbruyelle.rxpermissions.RxPermissions;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RepositoryModule {
    @Provides
    ISchedulerRepository schedulerRepository() {
        return new SchedulerRepository();
    }

    @Provides
    ISettingsRepository provideISettingsRepository() {
        return new SettingsRepository();
    }

    @Provides
    ILocationRepository provideLocationRepository() {
        return new LocationRepository();
    }

    @Provides
    IWeatherForecastRepository provideWeatherForecastRepository(OpenWeatherMapApi api, TranslateApi translateApi) {
        return new WeatherForecastRepository(api, translateApi);
    }

    @Provides
    RxPermissions provideRxPermissions(Context context) {
        return RxPermissions.getInstance(context);
    }

    @Provides
    IPermissionsRepository providePermissionsRepository(RxPermissions rxPermissions) {
        return new PermissionsRepository(rxPermissions);
    }


    @Provides
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    GsonConverterFactory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    OpenWeatherMapApi provideOpenWeatherMapApi(
            GsonConverterFactory gsonConverterFactory,
            OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(OpenWeatherMapApi.BASE_URL)
                .client(client)
                .build();//базовый url
        return retrofit.create(OpenWeatherMapApi.class);
    }




    @Provides
    TranslateApi provideTranslateApi(GsonConverterFactory gsonConverterFactory,
                                    OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl( TranslateApi.BASE_URL)
                .client(client)
                .build();//базовый url
        return retrofit.create(TranslateApi.class);
    }

    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    OkHttpClient provieOkhttpClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }
}
