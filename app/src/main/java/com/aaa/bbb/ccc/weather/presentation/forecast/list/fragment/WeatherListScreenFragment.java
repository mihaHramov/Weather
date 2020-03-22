package com.aaa.bbb.ccc.weather.presentation.forecast.list.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aaa.bbb.ccc.model.ShortForecast;
import com.aaa.bbb.ccc.model.SynopticForecast;
import com.aaa.bbb.ccc.weather.R;
import com.aaa.bbb.ccc.weather.WeatherApp;
import com.aaa.bbb.ccc.weather.presentation.adapter.ForecastAdapter;

import java.util.List;

import javax.inject.Inject;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;


public class WeatherListScreenFragment extends MvpAppCompatFragment implements WeatherListScreenView {
    private static final String KEY = WeatherListScreenFragment.class.getName();
    @Inject
    ForecastAdapter adapter;
    @Inject
    LinearLayoutManager layoutManager;
    @Inject
    NavigatorHolder navigatorHolder;

    @InjectPresenter
    WeatherListScreenPresenter mWeatherListScreenPresenter;

    @ProvidePresenter
    WeatherListScreenPresenter provideWeatherListScreenFragment() {
        mWeatherListScreenPresenter = WeatherApp.getInstance().getWeatherListFragmentComponent().getPresenter();
        mWeatherListScreenPresenter.setSynopticForecast((SynopticForecast) getArguments().getSerializable(KEY));
        return mWeatherListScreenPresenter;
    }

    public static Fragment newInstance(SynopticForecast synopticForecast) {
        Bundle arg = new Bundle();
        arg.putSerializable(KEY, synopticForecast);
        Fragment fragment = new WeatherListScreenFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_list_scrin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WeatherApp
                .getInstance()
                .getWeatherListFragmentComponent()
                .inject(this);
        RecyclerView mList = view.findViewById(R.id.listWeather);
        mList.setLayoutManager(layoutManager);
        adapter.setLister(id -> mWeatherListScreenPresenter.onItemForecastClick(id));
        mList.setAdapter(adapter);
    }

    @Override
    public void showWeather(List<ShortForecast> dailyForecast) {
        adapter.setItems(dailyForecast);
    }

    @Override
    public void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            Navigator navigator = new SupportAppNavigator(getActivity(), -1);
            navigatorHolder.setNavigator(navigator);
        }
    }
}
