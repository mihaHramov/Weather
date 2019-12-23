package com.aaa.bbb.ccc.weather.presentation.weatherListSceenFragment.presentation.presenter;

import android.os.Bundle;
import android.util.Log;

import com.aaa.bbb.ccc.weather.domain.interactor.CurrentWeatherForecastInteractor;
import com.aaa.bbb.ccc.weather.domain.model.DailyForecast;
import com.aaa.bbb.ccc.weather.domain.model.SynopticForecast;
import com.aaa.bbb.ccc.weather.navigation.Screens;
import com.aaa.bbb.ccc.weather.presentation.weatherListSceenFragment.presentation.view.WeatherListScreenView;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


@InjectViewState
public class WeatherListScreenPresenter extends MvpPresenter<WeatherListScreenView> {
    private CurrentWeatherForecastInteractor mInteractor;
    private SynopticForecast mSynopticForecast;

    public WeatherListScreenPresenter(CurrentWeatherForecastInteractor mInteractor, Router mRouter) {
        this.mInteractor = mInteractor;
        this.mRouter = mRouter;
    }

    private Router mRouter;


    public void onCreate(Bundle bundle) {
        if (bundle == null) {
            mInteractor.getCurrentWeather().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(synopticForecast -> mSynopticForecast = synopticForecast)
                    //show place
                    .doOnNext(synopticForecast -> getViewState().showPlace(synopticForecast.getPlace()))
                    .flatMap((Func1<SynopticForecast, Observable<DailyForecast>>) synopticForecast -> Observable.from(synopticForecast.getDailyForecast()))
                    .map(DailyForecast::getPreview)
                    .toList()
                    .subscribe(shortForecasts -> getViewState().showWeather(shortForecasts));
        }
    }


    public void onItemForecastClick(Integer item) {
        mRouter.navigateTo(new Screens.DetailsWeatherScreen(item, mSynopticForecast));
    }
}
