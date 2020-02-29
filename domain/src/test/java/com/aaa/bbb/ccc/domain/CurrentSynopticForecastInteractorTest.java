package com.aaa.bbb.ccc.domain;

import android.Manifest;

import com.aaa.bbb.ccc.data.network.OpenWeatherMapApi;
import com.aaa.bbb.ccc.data.repository.cash.ICashRepository;
import com.aaa.bbb.ccc.data.repository.city.ICityRepository;
import com.aaa.bbb.ccc.data.repository.forecast.IWeatherForecastRepository;
import com.aaa.bbb.ccc.data.repository.forecast.WeatherForecastRepository;
import com.aaa.bbb.ccc.data.repository.location.ILocationRepository;
import com.aaa.bbb.ccc.data.repository.permissions.IPermissionsRepository;
import com.aaa.bbb.ccc.data.repository.settings.ISettingsRepository;
import com.aaa.bbb.ccc.domain.interactor.CurrentWeatherForecastInteractor;
import com.aaa.bbb.ccc.domain.interactor.ICurrentWeatherForecastInteractor;
import com.aaa.bbb.ccc.model.Location;
import com.aaa.bbb.ccc.model.Place;
import com.aaa.bbb.ccc.model.SynopticForecast;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import rx.Observable;
import rx.observers.TestSubscriber;

import static com.aaa.bbb.ccc.domain.ModelTestHelper.createPlace;
import static com.aaa.bbb.ccc.domain.ModelTestHelper.createWeatherResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CurrentSynopticForecastInteractorTest {
    private String defaultLang = "ru";
    private String defaultMetric = "metric";
    private String lat = "23.47";
    private String lon = "48.11";
    private String translateName = "Лондон";
    private ICurrentWeatherForecastInteractor interactor;
    private String enName = "London";
    private TestSubscriber<SynopticForecast> testSubscriber;
    private Place place;
    @Mock
    IPermissionsRepository mPermissionsRepository;
    @Mock
    IWeatherForecastRepository mRepositoryOfWeather;
    @Mock
    ILocationRepository mLocationRepository;
    @Mock
    ISettingsRepository mSettingsRepository;
    @Mock
    ICityRepository mCityRepository;
    @Mock
    OpenWeatherMapApi weatherApi;
    @Mock
    ICashRepository cashRepository;

    @Rule
    public RxSchedulerRule rxSchedulerRule = new RxSchedulerRule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testSubscriber = new TestSubscriber<>();
        when(mSettingsRepository.getLanguage()).thenReturn(Observable.just(defaultLang));
        when(mSettingsRepository.getUnits()).thenReturn(Observable.just(defaultMetric));
        Location location = new Location(lat, lon);
        when(mSettingsRepository.getDefaultLocation()).thenReturn(Observable.just(location));
        when(mLocationRepository.getCurrentLocation()).thenReturn(Observable.just(location));
        when(mPermissionsRepository.getPermission(Manifest.permission.ACCESS_FINE_LOCATION)).thenReturn(Observable.just(true));
        place = createPlace(enName);
        SynopticForecast synopticForecast = new SynopticForecast(place, new ArrayList<>());
        when(mRepositoryOfWeather.getWeatherForecast(lat, lon, defaultLang, defaultMetric)).thenReturn(Observable.just(synopticForecast));
        when(mCityRepository.getCityTranslate(any(Place.class))).thenReturn(Observable.just(createPlace(translateName)));
        interactor = new CurrentWeatherForecastInteractor(mPermissionsRepository, mRepositoryOfWeather, mLocationRepository, mSettingsRepository, mCityRepository);
    }

    @Test
    public void getCurrentWeather() {
        interactor.getCurrentWeather().subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        Assert.assertEquals(translateName, testSubscriber.getOnNextEvents().get(0).getPlace().getName());
        verify(mPermissionsRepository).getPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        verify(mSettingsRepository).getLanguage();
        verify(mSettingsRepository).getUnits();
        verify(mSettingsRepository, never()).getDefaultLocation();
        verify(mLocationRepository).getCurrentLocation();
        verify(mCityRepository).getCityTranslate(any(Place.class));
        verify(mRepositoryOfWeather).getWeatherForecast(lat, lon, defaultLang, defaultMetric);
    }

    @Test
    public void testInteractorWhenUserNotGrandPermissions() {
        when(mPermissionsRepository.getPermission(Manifest.permission.ACCESS_FINE_LOCATION)).thenReturn(Observable.just(false));
        interactor.getCurrentWeather().subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        Assert.assertEquals(translateName, testSubscriber.getOnNextEvents().get(0).getPlace().getName());

        verify(mPermissionsRepository).getPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        verify(mSettingsRepository).getLanguage();
        verify(mSettingsRepository).getUnits();
        verify(mSettingsRepository).getDefaultLocation();
        verify(mLocationRepository, never()).getCurrentLocation();
        verify(mCityRepository).getCityTranslate(any(Place.class));
        verify(mRepositoryOfWeather).getWeatherForecast(lat, lon, defaultLang, defaultMetric);
    }

    @Test
    public void getCurrentWeatherIntegrationTest() {
        mRepositoryOfWeather = new WeatherForecastRepository(weatherApi, cashRepository);
        when(weatherApi.getForecast(lat, lon, defaultLang, defaultMetric)).thenReturn(Observable.just(createWeatherResponse()));
        doNothing().when(cashRepository).saveWeatherForecast(any(SynopticForecast.class));
        interactor = new CurrentWeatherForecastInteractor(mPermissionsRepository, mRepositoryOfWeather,
                mLocationRepository, mSettingsRepository, mCityRepository);

        interactor.getCurrentWeather().subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        Assert.assertEquals(translateName, testSubscriber.getOnNextEvents().get(0).getPlace().getName());
        verify(mCityRepository).getCityTranslate(any(Place.class));
    }

    @Test
    public void testGetCurrentWeatherWhenServerReturnError() {
        SynopticForecast synopticForecast = new SynopticForecast(place, new ArrayList<>());
        when(mCityRepository.getCityTranslate(place)).thenReturn(Observable.just(place));
        when(cashRepository.getWeatherForecast(lat, lon, defaultLang, defaultMetric)).thenReturn(Observable.just(synopticForecast));
        mRepositoryOfWeather = new WeatherForecastRepository(weatherApi, cashRepository);
        when(weatherApi.getForecast(lat, lon, defaultLang, defaultMetric)).thenReturn(Observable.error(new Throwable("network error")));
        doNothing().when(cashRepository).saveWeatherForecast(any(SynopticForecast.class));
        interactor = new CurrentWeatherForecastInteractor(mPermissionsRepository, mRepositoryOfWeather,
                mLocationRepository, mSettingsRepository, mCityRepository);

        interactor.getCurrentWeather().subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        Assert.assertEquals(enName, testSubscriber.getOnNextEvents().get(0).getPlace().getName());
        verify(cashRepository).getWeatherForecast(lat, lon, defaultLang, defaultMetric);
    }

    @Test
    public void testInteractorWhenRepositoryReturnError() {
        when(mCityRepository.getCityTranslate(place)).thenReturn(Observable.just(place));
        when(mRepositoryOfWeather.getWeatherForecast(lat, lon, defaultLang, defaultMetric)).thenReturn(Observable.error(new Throwable("empty")));
        doNothing().when(cashRepository).saveWeatherForecast(any(SynopticForecast.class));
        interactor = new CurrentWeatherForecastInteractor(mPermissionsRepository, mRepositoryOfWeather,
                mLocationRepository, mSettingsRepository, mCityRepository);

        interactor.getCurrentWeather().subscribe(testSubscriber);
        testSubscriber.assertNotCompleted();
    }

}