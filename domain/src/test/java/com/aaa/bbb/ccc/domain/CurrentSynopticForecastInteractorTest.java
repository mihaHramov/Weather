package com.aaa.bbb.ccc.domain;

import android.Manifest;

import com.aaa.bbb.ccc.model.Place;
import com.aaa.bbb.ccc.model.Location;
import com.aaa.bbb.ccc.model.SynopticForecast;
import com.aaa.bbb.ccc.data.model.api.weather.Coord;
import com.aaa.bbb.ccc.data.model.api.weather.WeatherResponse;
import com.aaa.bbb.ccc.data.network.OpenWeatherMapApi;
import com.aaa.bbb.ccc.data.repository.impl.WeatherForecastRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ICashRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ICityRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ILocationRepository;
import com.aaa.bbb.ccc.data.repository.intrf.IPermissionsRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ISchedulerRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ISettingsRepository;
import com.aaa.bbb.ccc.data.repository.intrf.IWeatherForecastRepository;
import com.aaa.bbb.ccc.domain.interactor.CurrentWeatherForecastInteractor;
import com.aaa.bbb.ccc.domain.interactor.ICurrentWeatherForecastInteractor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CurrentSynopticForecastInteractorTest {
    private ISchedulerRepository mSchedulerRepository;
    private IPermissionsRepository mPermissionsRepository;
    private IWeatherForecastRepository mRepositoryOfWeather;
    private ILocationRepository mLocationRepository;
    private ISettingsRepository mSettingsRepository;
    private ICityRepository mCityRepository;
    private OpenWeatherMapApi weatherApi;
    private ICashRepository cashRepository;

    private String defaultLang = "ru";
    private String defaultMetric = "metric";
    private String lat = "23.47";
    private String lon = "48.11";
    private Integer id = 10;
    private String translateName = "Лондон";

    private ICurrentWeatherForecastInteractor interactor;
    private String enName = "London";
    private TestSubscriber<com.aaa.bbb.ccc.model.SynopticForecast> testSubscriber;

    @Before
    public void setUp() {
        //mock settings repository
        testSubscriber = new TestSubscriber<>();
        mSettingsRepository = mock(ISettingsRepository.class);
        when(mSettingsRepository.getLanguage()).thenReturn(Observable.just(defaultLang));
        when(mSettingsRepository.getUnits()).thenReturn(Observable.just(defaultMetric));
        Location location = new Location(lat, lon);
        when(mSettingsRepository.getDefaultLocation()).thenReturn(Observable.just(location));

        //mock mLocation Repository
        mLocationRepository = mock(ILocationRepository.class);
        when(mLocationRepository.getCurrentLocation()).thenReturn(Observable.just(location));

        //mock mSchedulerRepository
        mSchedulerRepository = mock(ISchedulerRepository.class);
        when(mSchedulerRepository.getIO()).thenReturn(Schedulers.immediate());


        //mock IPermissionsRepository
        mPermissionsRepository = mock(IPermissionsRepository.class);
        when(mPermissionsRepository.getPermission(Manifest.permission.ACCESS_FINE_LOCATION)).thenReturn(Observable.just(true));

        //mock mRepositoryOfWeather
        mRepositoryOfWeather = mock(IWeatherForecastRepository.class);
        Place place = createCity();

        SynopticForecast synopticForecast = new SynopticForecast(place, new ArrayList<>());
        when(mRepositoryOfWeather.getWeatherForecast(lat, lon, defaultLang, defaultMetric)).thenReturn(Observable.just(synopticForecast));
        //mock mCityRepository
        mCityRepository = mock(ICityRepository.class);
        Place placeResult = createCity();
        placeResult.setName(translateName);
        when(mCityRepository.getCityTranslate(any(Place.class))).thenReturn(Observable.just(placeResult));
        weatherApi = Mockito.mock(OpenWeatherMapApi.class);
        cashRepository = Mockito.mock(ICashRepository.class);
        interactor = new CurrentWeatherForecastInteractor(mPermissionsRepository, mRepositoryOfWeather, mLocationRepository, mSettingsRepository, mSchedulerRepository, mCityRepository);
    }

    private Place createCity() {
        Place place = new Place();
        place.setName(enName);
        place.setId(20);
        place.setCountry("uk");
        return place;
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
                mLocationRepository, mSettingsRepository, mSchedulerRepository, mCityRepository);

        interactor.getCurrentWeather().subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        Assert.assertEquals(translateName, testSubscriber.getOnNextEvents().get(0).getPlace().getName());
        verify(mCityRepository).getCityTranslate(any(Place.class));
    }

    @Test
    public void testGetCurrentWeatherWhenServerReturnError() {
        Place place = createCity();
        SynopticForecast synopticForecast = new SynopticForecast(place, new ArrayList<>());
        when(mCityRepository.getCityTranslate(place)).thenReturn(Observable.just(place));
        when(cashRepository.getWeatherForecast(lat, lon, defaultLang, defaultMetric)).thenReturn(Observable.just(synopticForecast));
        mRepositoryOfWeather = new WeatherForecastRepository(weatherApi, cashRepository);
        when(weatherApi.getForecast(lat, lon, defaultLang, defaultMetric)).thenReturn(Observable.error(new Throwable("network error")));
        doNothing().when(cashRepository).saveWeatherForecast(any(SynopticForecast.class));
        interactor = new CurrentWeatherForecastInteractor(mPermissionsRepository, mRepositoryOfWeather,
                mLocationRepository, mSettingsRepository, mSchedulerRepository, mCityRepository);

        interactor.getCurrentWeather().subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        Assert.assertEquals(enName, testSubscriber.getOnNextEvents().get(0).getPlace().getName());
        verify(cashRepository).getWeatherForecast(lat, lon, defaultLang, defaultMetric);
    }

    @Test
    public void testInteractorWhenRepositoryReturnError() {
        Place place = createCity();
        when(mCityRepository.getCityTranslate(place)).thenReturn(Observable.just(place));
        when(mRepositoryOfWeather.getWeatherForecast(lat, lon, defaultLang, defaultMetric)).thenReturn(Observable.error(new Throwable("empty")));
        doNothing().when(cashRepository).saveWeatherForecast(any(SynopticForecast.class));
        interactor = new CurrentWeatherForecastInteractor(mPermissionsRepository, mRepositoryOfWeather,
                mLocationRepository, mSettingsRepository, mSchedulerRepository, mCityRepository);

        interactor.getCurrentWeather().subscribe(testSubscriber);
        testSubscriber.assertNotCompleted();
    }

    private WeatherResponse createWeatherResponse() {
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setCnt(1);
        weatherResponse.setMessage(333);
        weatherResponse.setCod("200");
        com.aaa.bbb.ccc.data.model.api.weather.City cityApi = new com.aaa.bbb.ccc.data.model.api.weather.City();
        cityApi.setTimezone(2000);
        cityApi.setSunrise(200);
        cityApi.setSunset(100);
        cityApi.setPopulation(100000);
        cityApi.setName(enName);
        cityApi.setId(id);
        cityApi.setCountry("uk");
        Coord coord = new Coord();
        coord.setLat(Double.valueOf(lat));
        coord.setLon(Double.valueOf(lon));
        cityApi.setCoord(coord);
        weatherResponse.setCity(cityApi);
        weatherResponse.setList(new ArrayList<>());
        return weatherResponse;
    }
}