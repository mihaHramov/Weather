package com.aaa.bbb.ccc.weather.presentation.weatherListScreenActivity.presentation.presenter;

import com.aaa.bbb.ccc.weather.data.repository.intrf.ISchedulerRepository;
import com.aaa.bbb.ccc.weather.domain.interactor.ICurrentWeatherForecastInteractor;
import com.aaa.bbb.ccc.weather.presentation.weatherListScreenActivity.presentation.view.blank.WeatherListView;

import moxy.InjectViewState;
import moxy.MvpPresenter;


@InjectViewState
public class WeatherListPresenter extends MvpPresenter<WeatherListView> {
    private ICurrentWeatherForecastInteractor mInteractor;
    private ISchedulerRepository mSchedulerRepository;



}
