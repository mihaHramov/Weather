package com.aaa.bbb.ccc.weather.presentation.weatherListSceenFragment.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aaa.bbb.ccc.weather.R;
import com.aaa.bbb.ccc.weather.WeatherApp;
import com.aaa.bbb.ccc.weather.domain.model.Place;
import com.aaa.bbb.ccc.weather.domain.model.ShortForecast;
import com.aaa.bbb.ccc.weather.presentation.adapter.ShortForecastAdapter;
import com.aaa.bbb.ccc.weather.presentation.weatherListSceenFragment.presentation.presenter.WeatherListScreenPresenter;
import com.aaa.bbb.ccc.weather.presentation.weatherListSceenFragment.presentation.view.WeatherListScreenView;

import java.util.List;

import javax.inject.Inject;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;


public class WeatherListScreenFragment extends MvpAppCompatFragment implements WeatherListScreenView {
    public interface OnPlaceLister {
        void show(Place place);
    }

    private OnPlaceLister placeLister;
    @Inject
    ShortForecastAdapter adapter;
    @Inject
    LinearLayoutManager layoutManager;
    @Inject
    NavigatorHolder navigatorHolder;

    private Navigator navigator;

    @InjectPresenter
    WeatherListScreenPresenter mWeatherListScreenPresenter;

    @ProvidePresenter
    WeatherListScreenPresenter provideWeatherListScreenFragment() {
        return WeatherApp.getWeatherListFragmentModule().getPresenter();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        mWeatherListScreenPresenter.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_weather_list_scrin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WeatherApp.getWeatherListFragmentModule().inject(this);
        RecyclerView mList = view.findViewById(R.id.listWeather);
        mList.setLayoutManager(layoutManager);
        adapter.setOnItemClickLister(id -> mWeatherListScreenPresenter.onItemForecastClick(id));
        mList.setAdapter(adapter);
        navigator = new SupportAppNavigator(getActivity(), -1);
    }

    @Override
    public void showPlace(Place place) {
        if (placeLister != null) {
            placeLister.show(place);
        }
    }

    @Override
    public void showWeather(List<ShortForecast> dailyForecast) {
        adapter.setItems(dailyForecast);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnPlaceLister) {
            placeLister = (OnPlaceLister) context;
        }
    }

    @Override
    public void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        navigatorHolder.setNavigator(navigator);
    }
}
