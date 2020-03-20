package com.aaa.bbb.ccc.weather.presentation.forecast.list.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aaa.bbb.ccc.model.ShortForecast;
import com.aaa.bbb.ccc.weather.R;
import com.aaa.bbb.ccc.weather.WeatherApp;
import com.aaa.bbb.ccc.weather.presentation.adapter.ForecastAdapter;
import com.squareup.picasso.Picasso;

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
        void show(String place);
    }

    private OnPlaceLister placeLister;
    private ImageView mIcon;
    private TextView mTemperature;
    private TextView mWeatherType;
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
        return WeatherApp.getInstance().getWeatherListFragmentModule().getPresenter();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_list_scrin, container, false);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WeatherApp
                .getInstance()
                .getWeatherListFragmentModule()
                .inject(this);
        RecyclerView mList = view.findViewById(R.id.listWeather);
        mIcon = view.findViewById(R.id.icon);
        mTemperature = view.findViewById(R.id.temperature);
        mWeatherType = view.findViewById(R.id.weather_type);
        mList.setLayoutManager(layoutManager);
        adapter.setLister(id -> mWeatherListScreenPresenter.onItemForecastClick(id));
        mList.setAdapter(adapter);
    }

    @Override
    public void showPlace(String place) {
        if (placeLister != null) {
            placeLister.show(place);
        }
    }

    @Override
    public void showWeather(List<ShortForecast> dailyForecast) {
        adapter.setItems(dailyForecast);
    }

    @Override
    public void showWeatherForecastForToday(ShortForecast forecast) {
        mWeatherType.setText(forecast.getWeatherType().getDescription());
        Picasso.get().load(forecast.getWeatherType().getIcon()).into(mIcon);
        String temperature = forecast.getTemperature().getMax().toString();
        mTemperature.setText(temperature);
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
        if (getActivity() != null) {
            Navigator navigator = new SupportAppNavigator(getActivity(), -1);
            navigatorHolder.setNavigator(navigator);
        }
    }
}
