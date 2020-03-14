package com.aaa.bbb.ccc.weather.presentation.forecast.details.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.aaa.bbb.ccc.model.DailyForecast;
import com.aaa.bbb.ccc.weather.R;
import com.aaa.bbb.ccc.weather.WeatherApp;
import com.aaa.bbb.ccc.weather.presentation.adapter.ForecastAdapter;

import javax.inject.Inject;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;


public class DetailsWeatherForecastFragment extends MvpAppCompatFragment implements DetailsWeatherForecastView {
    private static final String KEY = DetailsWeatherForecastFragment.class.getSimpleName();
    @Inject
    ForecastAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @InjectPresenter
    DetailsWeatherForecastPresenter mDetailsWeatherForecastPresenter;

    public static DetailsWeatherForecastFragment newInstance(DailyForecast dailyForecast) {
        DetailsWeatherForecastFragment fragment = new DetailsWeatherForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY, dailyForecast);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        WeatherApp.getInstance().getDetailsWeatherForecastFragmentComponent().inject(this);
        View view = inflater.inflate(R.layout.details_weather_forecast, container, false);
        initRecyclerView(view);
        DailyForecast dailyForecast = (DailyForecast) getArguments().getSerializable(KEY);
        mDetailsWeatherForecastPresenter.init(dailyForecast, savedInstanceState);
        return view;
    }

    private void initRecyclerView(View view) {
        mAdapter.setLayoutId(R.layout.details_weather_forecast_item);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showWeatherForecast(DailyForecast dailyForecast) {
        Toast.makeText(getActivity(), dailyForecast.getPreview().getDate().toString(), Toast.LENGTH_LONG).show();
        Toast.makeText(getActivity(), dailyForecast.getShortForecasts().size() + "", Toast.LENGTH_LONG).show();
        mAdapter.setItems(dailyForecast.getShortForecasts());
    }
}