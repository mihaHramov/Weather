package com.aaa.bbb.ccc.domain;

import android.Manifest;

import com.aaa.bbb.ccc.data.model.City;
import com.aaa.bbb.ccc.data.model.Location;
import com.aaa.bbb.ccc.data.model.WeatherForecast;
import com.aaa.bbb.ccc.data.model.api.weatherApi.Coord;
import com.aaa.bbb.ccc.data.model.api.weatherApi.WeatherResponse;
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
import com.aaa.bbb.ccc.domain.model.SynopticForecast;

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

public class CurrentWeatherForecastInteractorTest {
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
    private TestSubscriber<SynopticForecast> testSubscriber;

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
        City city = createCity();

        WeatherForecast weatherForecast = new WeatherForecast(city, new ArrayList<>());
        when(mRepositoryOfWeather.getWeatherForecast(lat, lon, defaultLang, defaultMetric)).thenReturn(Observable.just(weatherForecast));
        //mock mCityRepository
        mCityRepository = mock(ICityRepository.class);
        City cityResult = createCity();
        cityResult.setName(translateName);
        when(mCityRepository.getCityTranslate(any(City.class))).thenReturn(Observable.just(cityResult));
        weatherApi = Mockito.mock(OpenWeatherMapApi.class);
        cashRepository = Mockito.mock(ICashRepository.class);
        interactor = new CurrentWeatherForecastInteractor(mPermissionsRepository, mRepositoryOfWeather, mLocationRepository, mSettingsRepository, mSchedulerRepository, mCityRepository);
    }

    private City createCity() {
        City city = new City();
        city.setName(enName);
        city.setId(20);
        city.setSunrise(1020);
        city.setSunset(2030);
        city.setCountry("uk");
        return city;
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
        verify(mCityRepository).getCityTranslate(any(City.class));
        verify(mRepositoryOfWeather).getWeatherForecast(lat, lon, defaultLang, defaultMetric);
    }

    @Test
    public void getCurrentWeather_when_permissions_return_false() {
        when(mPermissionsRepository.getPermission(Manifest.permission.ACCESS_FINE_LOCATION)).thenReturn(Observable.just(false));
        interactor.getCurrentWeather().subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        Assert.assertEquals(translateName, testSubscriber.getOnNextEvents().get(0).getPlace().getName());

        verify(mPermissionsRepository).getPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        verify(mSettingsRepository).getLanguage();
        verify(mSettingsRepository).getUnits();
        verify(mSettingsRepository).getDefaultLocation();
        verify(mLocationRepository, never()).getCurrentLocation();
        verify(mCityRepository).getCityTranslate(any(City.class));
        verify(mRepositoryOfWeather).getWeatherForecast(lat, lon, defaultLang, defaultMetric);
    }

    @Test
    public void getCurrentWeatherIntegrationTest() {
        mRepositoryOfWeather = new WeatherForecastRepository(weatherApi, cashRepository);
        when(weatherApi.getForecast(lat, lon, defaultLang, defaultMetric)).thenReturn(Observable.just(createWeatherResponse()));
        doNothing().when(cashRepository).saveWeatherForecast(any(WeatherForecast.class));
        interactor = new CurrentWeatherForecastInteractor(mPermissionsRepository, mRepositoryOfWeather,
                mLocationRepository, mSettingsRepository, mSchedulerRepository, mCityRepository);

        interactor.getCurrentWeather().subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        Assert.assertEquals(translateName, testSubscriber.getOnNextEvents().get(0).getPlace().getName());
        verify(mCityRepository).getCityTranslate(any(City.class));
    }

    @Test
    public void getCurrentWeather_when_api_return_error_Test() {
        City city = createCity();
        WeatherForecast weatherForecast = new WeatherForecast(city, new ArrayList<>());
        when(mCityRepository.getCityTranslate(city)).thenReturn(Observable.just(city));
        when(cashRepository.getWeatherForecast(lat, lon, defaultLang, defaultMetric)).thenReturn(Observable.just(weatherForecast));
        mRepositoryOfWeather = new WeatherForecastRepository(weatherApi, cashRepository);
        when(weatherApi.getForecast(lat, lon, defaultLang, defaultMetric)).thenReturn(Observable.error(new Throwable("network error")));
        doNothing().when(cashRepository).saveWeatherForecast(any(WeatherForecast.class));
        interactor = new CurrentWeatherForecastInteractor(mPermissionsRepository, mRepositoryOfWeather,
                mLocationRepository, mSettingsRepository, mSchedulerRepository, mCityRepository);

        interactor.getCurrentWeather().subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        Assert.assertEquals(enName, testSubscriber.getOnNextEvents().get(0).getPlace().getName());
        verify(cashRepository).getWeatherForecast(lat, lon, defaultLang, defaultMetric);
    }

    @Test
    public void getCurrentWeather_when_weatherRepository_return_error_Test() {
        City city = createCity();
        when(mCityRepository.getCityTranslate(city)).thenReturn(Observable.just(city));
        when(mRepositoryOfWeather.getWeatherForecast(lat, lon, defaultLang, defaultMetric)).thenReturn(Observable.error(new Throwable("empty")));
        doNothing().when(cashRepository).saveWeatherForecast(any(WeatherForecast.class));
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
        com.aaa.bbb.ccc.data.model.api.weatherApi.City cityApi = new com.aaa.bbb.ccc.data.model.api.weatherApi.City();
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