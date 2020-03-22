package com.aaa.bbb.ccc.weather.presentation.forecast.list.fragment;

import com.aaa.bbb.ccc.model.DailyForecast;
import com.aaa.bbb.ccc.model.Place;
import com.aaa.bbb.ccc.model.SynopticForecast;
import com.aaa.bbb.ccc.weather.navigation.Screens;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import ru.terrakok.cicerone.Router;

import static com.aaa.bbb.ccc.testhelper.MockTestHelper.getDailyForecast;
import static com.aaa.bbb.ccc.testhelper.MockTestHelper.getPlace;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;

public class WeatherListScreenPresenterTest {
    private WeatherListScreenPresenter presenter;
    @Mock
    Router router;
    @Mock
    WeatherListScreenView view;

    @Before
    public void setUp()  {
        MockitoAnnotations.initMocks(this);
        Place place = getPlace("kiev", "ua", "uk", 10, "29.3", "30.3");
        List<DailyForecast> dailyForecasts = Arrays.asList(getDailyForecast(2730), getDailyForecast(2330));
        SynopticForecast synopticForecast = new SynopticForecast(place, dailyForecasts);
        presenter = new WeatherListScreenPresenter(router);
        presenter.setSynopticForecast(synopticForecast);
    }

    @Test
    public void onFirstViewAttach() {
        presenter.attachView(view);
        verify(view).showWeather(anyList());
    }

    @Test
    public void onItemForecastClick() {
        presenter.attachView(view);
        Integer itemId = 0;
        presenter.onItemForecastClick(itemId);
        verify(router).navigateTo(any(Screens.DetailsWeatherScreen.class));
    }
}