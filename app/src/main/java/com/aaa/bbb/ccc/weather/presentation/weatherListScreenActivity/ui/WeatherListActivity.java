package com.aaa.bbb.ccc.weather.presentation.weatherListScreenActivity.ui;

import android.os.Bundle;

import com.aaa.bbb.ccc.weather.R;
import com.aaa.bbb.ccc.weather.presentation.weatherListSceenFragment.ui.WeatherListScreenFragment;
import com.aaa.bbb.ccc.weather.presentation.weatherListScreenActivity.presentation.presenter.WeatherListPresenter;
import com.aaa.bbb.ccc.weather.presentation.weatherListScreenActivity.presentation.view.blank.WeatherListView;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;


public class WeatherListActivity extends MvpAppCompatActivity implements WeatherListView {
    @InjectPresenter
    WeatherListPresenter mWeatherListPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new WeatherListScreenFragment())
                    .commit();
        }
    }
}
