package com.aaa.bbb.ccc.weather.presentation.detailsWeatherForecastActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.aaa.bbb.ccc.weather.R;
import com.aaa.bbb.ccc.weather.domain.model.SynopticForecast;
import com.aaa.bbb.ccc.weather.presentation.detailsWeatherForecastFragment.DetailsWeatherForecastFragment;

import moxy.MvpAppCompatActivity;

public class DetailsWeatherForecastActivity extends MvpAppCompatActivity implements DetailsWeatherForecastActivityView {
    private final static String id = "id";
    private final static String list = "DailyForecast";

    public static Intent getIntent(Context context, SynopticForecast dailyForecasts, Integer item) {
        Intent intent = new Intent(context, DetailsWeatherForecastActivity.class);
        intent.putExtra(list, dailyForecasts);
        intent.putExtra(id, item);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        SynopticForecast synopticForecast = (SynopticForecast) extras.getSerializable(list);
        int item = extras.getInt(id);
        DetailsWeatherForecastFragment fragment = DetailsWeatherForecastFragment
                .newInstance(synopticForecast.getDailyForecast().get(item));
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
