package com.aaa.bbb.ccc.weather.presentation.forecast.list.fragment;

import com.aaa.bbb.ccc.model.DailyForecast;
import com.aaa.bbb.ccc.model.SynopticForecast;
import com.aaa.bbb.ccc.weather.navigation.Screens;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;
import rx.Observable;


@InjectViewState
public class WeatherListScreenPresenter extends MvpPresenter<WeatherListScreenView> {
    private SynopticForecast mSynopticForecast;
    private Router mRouter;


    public WeatherListScreenPresenter(Router mRouter) {
        this.mRouter = mRouter;
    }

    void setSynopticForecast(SynopticForecast mSynopticForecast) {
        this.mSynopticForecast = mSynopticForecast;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Observable.from(mSynopticForecast.getDailyForecast())
                .map(DailyForecast::getPreview).toList()
                .subscribe(shortForecasts -> getViewState().showWeather(shortForecasts));
    }


    void onItemForecastClick(Integer item) {
        mRouter.navigateTo(new Screens.DetailsWeatherScreen(item, mSynopticForecast));
    }
}
