package com.aaa.bbb.ccc.weather.presentation.forecast.list.fragment;

import android.os.Bundle;

import com.aaa.bbb.ccc.domain.interactor.ICurrentWeatherForecastInteractor;
import com.aaa.bbb.ccc.model.DailyForecast;
import com.aaa.bbb.ccc.model.SynopticForecast;
import com.aaa.bbb.ccc.weather.navigation.Screens;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;


@InjectViewState
public class WeatherListScreenPresenter extends MvpPresenter<WeatherListScreenView> {
    private ICurrentWeatherForecastInteractor mInteractor;
    private SynopticForecast mSynopticForecast;
    private Router mRouter;


    public WeatherListScreenPresenter(
            ICurrentWeatherForecastInteractor mInteractor,
            Router mRouter) {
        this.mInteractor = mInteractor;
        this.mRouter = mRouter;
    }


    void onCreate(Bundle bundle) {
        if (bundle == null) {
            mInteractor.getCurrentWeather()
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(synopticForecast -> mSynopticForecast = synopticForecast)
                    .doOnNext(synopticForecast -> getViewState().showPlace(synopticForecast.getPlace().getName()))
                    .flatMap((Func1<SynopticForecast, Observable<DailyForecast>>) synopticForecast -> Observable.from(synopticForecast.getDailyForecast()))
                    .map(DailyForecast::getPreview)
                    .toList()
                    .subscribe(shortForecasts -> getViewState().showWeather(shortForecasts),
                            throwable -> getViewState().showError(throwable.getMessage()));
        }
    }


    void onItemForecastClick(Integer item) {
        mRouter.navigateTo(new Screens.DetailsWeatherScreen(item, mSynopticForecast));
    }
}
