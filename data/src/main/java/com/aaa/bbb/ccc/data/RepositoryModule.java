package com.aaa.bbb.ccc.data;

import android.content.Context;

import androidx.room.Room;

import com.aaa.bbb.ccc.data.db.WeatherDatabase;
import com.aaa.bbb.ccc.data.network.OpenWeatherMapApi;
import com.aaa.bbb.ccc.data.network.TranslateApi;
import com.aaa.bbb.ccc.data.repository.impl.CashRepository;
import com.aaa.bbb.ccc.data.repository.impl.CityRepository;
import com.aaa.bbb.ccc.data.repository.impl.LocationRepository;
import com.aaa.bbb.ccc.data.repository.impl.PermissionsRepository;
import com.aaa.bbb.ccc.data.repository.impl.SchedulerRepository;
import com.aaa.bbb.ccc.data.repository.impl.SettingsRepository;
import com.aaa.bbb.ccc.data.repository.impl.WeatherForecastRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ICashRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ICityRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ILocationRepository;
import com.aaa.bbb.ccc.data.repository.intrf.IPermissionsRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ISchedulerRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ISettingsRepository;
import com.aaa.bbb.ccc.data.repository.intrf.IWeatherForecastRepository;
import com.aaa.bbb.ccc.data.utils.Const;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tbruyelle.rxpermissions.RxPermissions;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
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
    IWeatherForecastRepository provideWeatherForecastRepository(OpenWeatherMapApi api, ICashRepository cashRepository) {
        return new WeatherForecastRepository(api, cashRepository);
    }

    @Provides
    ICityRepository provideCityRepository(ICashRepository cashRepository, TranslateApi translateApi) {
        return new CityRepository(cashRepository, translateApi);
    }

    @Provides
    ICashRepository provideCashRepository(WeatherDatabase weatherDatabase) {
        return new CashRepository(weatherDatabase);
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
    @Named("OpenWeatherMapApiBaseUrl")
    String getOpenWeatherMapApiBaseUrl() {
        return Const.OpenWeatherMapApiBaseUrl;
    }

    @Provides
    RxJavaCallAdapterFactory provideFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    @Provides
    @Named("OpenWeatherMapApiBaseHttpUrl")
    HttpUrl provideHttpUrlOpenWeatherMapApi(@Named("OpenWeatherMapApiBaseUrl") String url) {
        return HttpUrl.get(url);
    }

    @Provides
    OpenWeatherMapApi provideOpenWeatherMapApi(
            GsonConverterFactory gsonConverterFactory,
            OkHttpClient client, RxJavaCallAdapterFactory factory,
            @Named("OpenWeatherMapApiBaseHttpUrl") HttpUrl url) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(factory)
                .baseUrl(url)
                .client(client)
                .build();//базовый url
        return retrofit.create(OpenWeatherMapApi.class);
    }


    @Provides
    @Named("TranslateApiBaseUrl")
    String getTranslateApiBaseUrl() {
        return Const.TranslateApiBaseUrl;
    }

    @Provides
    TranslateApi provideTranslateApi(GsonConverterFactory gsonConverterFactory,
                                     OkHttpClient client, RxJavaCallAdapterFactory factory,
                                     @Named("TranslateApiBaseHttpUrl") HttpUrl url) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(factory)
                .baseUrl(url)
                .client(client)
                .build();//базовый url
        return retrofit.create(TranslateApi.class);
    }

    @Provides
    @Named("TranslateApiBaseHttpUrl")
    HttpUrl translateBaseUrl(@Named("TranslateApiBaseUrl") String baseUrl) {
        return HttpUrl.get(baseUrl);
    }

    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    OkHttpClient provideOkhttpClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    @Provides
    WeatherDatabase provideWeatherDatabase(Context context) {
        return Room.databaseBuilder(context, WeatherDatabase.class, WeatherDatabase.NAME).build();
    }
}
