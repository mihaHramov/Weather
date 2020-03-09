package com.aaa.bbb.ccc.weather.presentation.forecast.list.fragment;

import com.aaa.bbb.ccc.domain.interactor.ICurrentWeatherForecastInteractor;
import com.aaa.bbb.ccc.model.DailyForecast;
import com.aaa.bbb.ccc.model.Place;
import com.aaa.bbb.ccc.model.ShortForecast;
import com.aaa.bbb.ccc.model.SynopticForecast;
import com.aaa.bbb.ccc.testhelper.RxSchedulerTestRule;
import com.aaa.bbb.ccc.weather.navigation.Screens;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.terrakok.cicerone.Router;
import rx.Observable;

import static com.aaa.bbb.ccc.testhelper.MockTestHelper.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WeatherListScreenPresenterTest {
    private WeatherListScreenPresenter presenter;
    @Mock
    ICurrentWeatherForecastInteractor interactor;
    @Mock
    Router router;
    @Mock
    WeatherListScreenView view;
    @Rule
    public RxSchedulerTestRule rxSchedulerTestRule = new RxSchedulerTestRule();
    private Place place;
    private List<DailyForecast> dailyForecasts;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        place = getPlace("kiev", "ua", "uk", 10, "29.3", "30.3");
        dailyForecasts = Arrays.asList(getDailyForecast(2730), getDailyForecast(2330));
        SynopticForecast synopticForecast = new SynopticForecast(place, dailyForecasts);
        when(interactor.getCurrentWeather()).thenReturn(Observable.just(synopticForecast));
        presenter = new WeatherListScreenPresenter(interactor, router);
        presenter.attachView(view);
    }


    @Test
    public void onCreate() {
        presenter.onCreate();
        verify(view).showPlace(place.getName());
        List<ShortForecast> shortForecasts = new ArrayList<>();
        dailyForecasts.forEach(dailyForecast -> shortForecasts.add(dailyForecast.getPreview()));
        verify(view).showWeather(shortForecasts);
    }

    @Test
    public void onCreateWhenInteractorReturnError() {
        final String MOCK_ERROR = "mock error";
        when(interactor.getCurrentWeather()).thenReturn(Observable.error(new Throwable(MOCK_ERROR)));
        presenter.onCreate();
        verify(view).showError(MOCK_ERROR);
    }

    @Test
    public void onItemForecastClick() {
        Integer itemId = 0;
        presenter.onCreate();
        presenter.onItemForecastClick(itemId);
        verify(router).navigateTo(any(Screens.DetailsWeatherScreen.class));
    }
}