package com.aaa.bbb.ccc.weather.presentation.forecast.list.activity;

import android.os.Bundle;

import com.aaa.bbb.ccc.weather.R;
import com.aaa.bbb.ccc.weather.presentation.forecast.list.fragment.WeatherListScreenFragment;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;


public class WeatherListActivity extends MvpAppCompatActivity implements WeatherListView {
    @InjectPresenter
    WeatherListPresenter mWeatherListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);
    }

    @Override
    public void showWeatherForecast() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new WeatherListScreenFragment())
                .commit();
    }
}
