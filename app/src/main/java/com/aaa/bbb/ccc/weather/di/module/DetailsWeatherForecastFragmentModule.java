package com.aaa.bbb.ccc.weather.di.module;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aaa.bbb.ccc.weather.presentation.adapter.DetailForecastAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class DetailsWeatherForecastFragmentModule {
    @Provides
    DetailForecastAdapter provideDetailForecastAdapter() {
        return new DetailForecastAdapter();
    }

    @Provides
    RecyclerView.LayoutManager provideLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }
}
