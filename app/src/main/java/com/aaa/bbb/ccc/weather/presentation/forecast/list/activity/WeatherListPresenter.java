package com.aaa.bbb.ccc.weather.presentation.forecast.list.activity;

import com.aaa.bbb.ccc.domain.interactor.ICurrentWeatherForecastInteractor;
import com.aaa.bbb.ccc.model.ShortForecast;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import rx.android.schedulers.AndroidSchedulers;


@InjectViewState
public class WeatherListPresenter extends MvpPresenter<WeatherListView> {
    private ICurrentWeatherForecastInteractor mInteractor;

    public WeatherListPresenter(ICurrentWeatherForecastInteractor mInteractor) {
        this.mInteractor = mInteractor;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mInteractor.getCurrentWeather()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(synopticForecast -> getViewState().showPlace(synopticForecast.getPlace().getName()))
                .doOnNext(synopticForecast -> {
                    ShortForecast forecast = synopticForecast.getDailyForecast().get(0).getPreview();
                    getViewState().showWeatherForecastForToday(forecast);
                })
                .subscribe(shortForecasts -> getViewState().showWeather(shortForecasts),
                        throwable -> getViewState().showError(throwable.getMessage()));
    }
}
