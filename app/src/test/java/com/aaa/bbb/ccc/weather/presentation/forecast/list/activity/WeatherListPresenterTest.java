package com.aaa.bbb.ccc.weather.presentation.forecast.list.activity;

import org.junit.Test;
import org.mockito.Mockito;

public class WeatherListPresenterTest {

    @Test
    public void onFirstViewAttach() {
        WeatherListView mockView = Mockito.mock(WeatherListView.class);
        WeatherListPresenter presenter = new WeatherListPresenter();
        presenter.attachView(mockView);
        Mockito.verify(mockView).showWeatherForecast();
    }
}