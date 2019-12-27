package com.aaa.bbb.ccc.weather.presentation.detailsWeatherForecastActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.aaa.bbb.ccc.weather.model.SynopticForecast;
import com.aaa.bbb.ccc.weather.R;
import com.aaa.bbb.ccc.weather.presentation.detailsWeatherForecastFragment.DetailsWeatherForecastFragment;

import moxy.MvpAppCompatActivity;

public class DetailsWeatherForecastActivity extends MvpAppCompatActivity implements DetailsWeatherForecastActivityView {
    private final static String ID = "id";
    private final static String LIST = "DailyForecast";

    public static Intent getIntent(Context context, SynopticForecast dailyForecasts, Integer item) {
        Intent intent = new Intent(context, DetailsWeatherForecastActivity.class);
        intent.putExtra(LIST, dailyForecasts);
        intent.putExtra(ID, item);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        SynopticForecast synopticForecast = (SynopticForecast) extras.getSerializable(LIST);
        int item = extras.getInt(ID);
        DetailsWeatherForecastFragment fragment = DetailsWeatherForecastFragment
                .newInstance(synopticForecast.getDailyForecast().get(item));
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
