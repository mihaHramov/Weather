package com.aaa.bbb.ccc.weather.presentation.forecast.list.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aaa.bbb.ccc.model.ShortForecast;
import com.aaa.bbb.ccc.model.SynopticForecast;
import com.aaa.bbb.ccc.weather.R;
import com.aaa.bbb.ccc.weather.WeatherApp;
import com.aaa.bbb.ccc.weather.presentation.forecast.list.fragment.WeatherListScreenFragment;
import com.squareup.picasso.Picasso;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;


public class WeatherListActivity extends MvpAppCompatActivity implements WeatherListView {
    @InjectPresenter
    WeatherListPresenter mWeatherListPresenter;

    @ProvidePresenter
    WeatherListPresenter provideWeatherListPresenter() {
        return WeatherApp.getInstance().getWeatherListActivityComponent().getPresenter();
    }

    private ImageView mIcon;
    private TextView mTemperature;
    private TextView mWeatherType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);
        mIcon = findViewById(R.id.icon);
        mTemperature = findViewById(R.id.temperature);
        mWeatherType = findViewById(R.id.weather_type);
    }

    @Override
    public void showWeather(SynopticForecast synopticForecast) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, WeatherListScreenFragment.newInstance(synopticForecast))
                .commit();
    }

    @Override
    public void showPlace(String place) {
        Toast.makeText(this, place, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showWeatherForecastForToday(ShortForecast forecast) {
        mWeatherType.setText(forecast.getWeatherType().getDescription());
        Picasso.get().load(forecast.getWeatherType().getIcon()).into(mIcon);
        String temperature = forecast.getTemperature().getMax().toString();
        mTemperature.setText(temperature);
    }
}