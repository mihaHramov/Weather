package com.aaa.bbb.ccc.weather;

import android.app.Application;
import com.aaa.bbb.ccc.weather.di.component.AppComponent;
import com.aaa.bbb.ccc.weather.di.component.DaggerAppComponent;
import com.aaa.bbb.ccc.weather.di.component.DetailsWeatherForecastFragmentComponent;
import com.aaa.bbb.ccc.weather.di.component.WeatherListFragmentComponent;
import com.aaa.bbb.ccc.weather.di.module.AppModule;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;


public class WeatherApp extends Application {
    private static AppComponent component;
    private String wat = "{\"cod\":\"200\",\"message\":0,\"cnt\":2,\"list\":[{\"dt\":1576648800,\"main\":{\"temp\":4.05,\"feels_like\":0.65,\"temp_min\":4.05,\"temp_max\":4.05,\"pressure\":1023,\"sea_level\":1023,\"grnd_level\":1000,\"humidity\":94,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"переменная облачность\",\"icon\":\"03d\"}],\"clouds\":{\"all\":28},\"wind\":{\"speed\":2.75,\"deg\":228},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-12-18 06:00:00\"},{\"dt\":1576659600,\"main\":{\"temp\":7.53,\"feels_like\":4.02,\"temp_min\":7.53,\"temp_max\":7.53,\"pressure\":1022,\"sea_level\":1022,\"grnd_level\":1000,\"humidity\":80,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"облачно с прояснениями\",\"icon\":\"04d\"}],\"clouds\":{\"all\":74},\"wind\":{\"speed\":3.21,\"deg\":228},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-12-18 09:00:00\"}],\"city\":{\"id\":702320,\"name\":\"Makiyivka\",\"coord\":{\"lat\":48.0478,\"lon\":37.9258},\"country\":\"UA\",\"population\":15000,\"timezone\":7200,\"sunrise\":1576646011,\"sunset\":1576676151}}";

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
//        Gson gson = new GsonBuilder()
//                .setPrettyPrinting()
//                .create();

//        Observable.just(gson.fromJson(wat, WeatherResponce.class))
//                .subscribe(response -> {
//                    Log.d("mihaHramov w", wat);
//                    Log.d("mihaHramov h", gson.toJson(response));
//                    Log.d("mihaHramov m",response.getCity().getCountry());
//                });
        //где R = 6371 км — средний радиус земного шара.
//        Math.sin()
//        Math.cos()
        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static WeatherListFragmentComponent getWeatherListFragmentModule() {
        return component.getWeatherListFragmentComponent();
    }

    public static DetailsWeatherForecastFragmentComponent getDetailsWeatherForecastFragmentComponent() {
        return component.getDetailsWeatherForecastFragmentComponent();
    }
}