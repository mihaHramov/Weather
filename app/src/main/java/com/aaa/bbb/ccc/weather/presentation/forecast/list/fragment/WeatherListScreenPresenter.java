package com.aaa.bbb.ccc.weather.presentation.forecast.list.fragment;

import com.aaa.bbb.ccc.domain.interactor.ICurrentWeatherForecastInteractor;
import com.aaa.bbb.ccc.model.DailyForecast;
import com.aaa.bbb.ccc.model.ShortForecast;
import com.aaa.bbb.ccc.model.SynopticForecast;
import com.aaa.bbb.ccc.weather.navigation.Screens;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;


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

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mInteractor.getCurrentWeather()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(synopticForecast -> mSynopticForecast = synopticForecast)
                .doOnNext(synopticForecast -> getViewState().showPlace(synopticForecast.getPlace().getName()))
                .doOnNext(synopticForecast -> {
                    ShortForecast forecast  = synopticForecast.getDailyForecast().get(0).getPreview();
                    getViewState().showWeatherForecastForToday(forecast);
                })
                .flatMap(synopticForecast -> Observable.from(synopticForecast.getDailyForecast()))
                .map(DailyForecast::getPreview)
                .toList()
                .subscribe(shortForecasts -> getViewState().showWeather(shortForecasts),
                        throwable -> getViewState().showError(throwable.getMessage()));
    }


    void onItemForecastClick(Integer item) {
        mRouter.navigateTo(new Screens.DetailsWeatherScreen(item, mSynopticForecast));
    }
}
