package com.aaa.bbb.ccc.weather.presentation.forecast.list.activity;

import com.aaa.bbb.ccc.domain.interactor.ICurrentWeatherForecastInteractor;
import com.aaa.bbb.ccc.model.DailyForecast;
import com.aaa.bbb.ccc.model.Place;
import com.aaa.bbb.ccc.model.SynopticForecast;
import com.aaa.bbb.ccc.testhelper.RxSchedulerTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import rx.Observable;

import static com.aaa.bbb.ccc.testhelper.MockTestHelper.getDailyForecast;
import static com.aaa.bbb.ccc.testhelper.MockTestHelper.getPlace;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WeatherListPresenterTest {
    private WeatherListPresenter presenter;
    @Mock
    ICurrentWeatherForecastInteractor interactor;
    @Mock
    WeatherListActivity view;
    @Rule
    public RxSchedulerTestRule rxSchedulerTestRule = new RxSchedulerTestRule();
    private Place place;
    private SynopticForecast synopticForecast;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new WeatherListPresenter(interactor);
        place = getPlace("kiev", "ua", "uk", 10, "29.3", "30.3");
        List<DailyForecast> dailyForecasts = Arrays.asList(getDailyForecast(2730), getDailyForecast(2330));
        synopticForecast = new SynopticForecast(place, dailyForecasts);
        when(interactor.getCurrentWeather()).thenReturn(Observable.just(synopticForecast));
    }

    @Test
    public void onCreateWhenInteractorReturnError() {
        final String MOCK_ERROR = "mock error";
        when(interactor.getCurrentWeather()).thenReturn(Observable.error(new Throwable(MOCK_ERROR)));
        presenter.attachView(view);
        verify(view).showError(MOCK_ERROR);
    }

    @Test
    public void onCreate() {
        presenter.attachView(view);
        verify(view).showPlace(place.getName());
        verify(view).showWeather(synopticForecast);
    }
}