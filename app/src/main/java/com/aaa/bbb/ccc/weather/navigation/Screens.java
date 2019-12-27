package com.aaa.bbb.ccc.weather.navigation;

import android.content.Context;
import android.content.Intent;

import com.aaa.bbb.ccc.weather.model.SynopticForecast;
import com.aaa.bbb.ccc.weather.presentation.detailsWeatherForecastActivity.DetailsWeatherForecastActivity;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {
    public static final class DetailsWeatherScreen extends SupportAppScreen {
        private Integer id;
        private SynopticForecast mSynopticForecast;

        public DetailsWeatherScreen(Integer id, SynopticForecast synopticForecast) {
            this.id = id;
            this.mSynopticForecast = synopticForecast;
        }

        @Override
        public Intent getActivityIntent(Context context) {
            return DetailsWeatherForecastActivity.getIntent(context, mSynopticForecast, id);
        }
    }
}
