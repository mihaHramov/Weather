package com.aaa.bbb.ccc.data;

import com.aaa.bbb.ccc.data.repository.cash.CashRepository;
import com.aaa.bbb.ccc.data.repository.cash.ICashRepository;
import com.aaa.bbb.ccc.model.DailyForecast;
import com.aaa.bbb.ccc.model.Place;
import com.aaa.bbb.ccc.model.SynopticForecast;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

import static com.aaa.bbb.ccc.testhelper.MockTestHelper.*;


@RunWith(RobolectricTestRunner.class)
public class CashRepositoryTest extends BaseDbTest {
    private ICashRepository cashRepository;
    private static final String LON = "48.19";
    private static final String LAT = "23.57";
    private static final String UA = "UA";
    private static final String RU = "ru";
    private static final String KIEV_CITY = "Kiev";
    private static final Integer CITY_ID = 2345;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        cashRepository = new CashRepository(this.db);
    }

    @Test
    public void getCityTest() {
        Place kiev = getPlace(KIEV_CITY, UA, RU, CITY_ID, LAT, LON);
        Place london = getPlace("London", "uk", "en", CITY_ID, LAT, LON);
        Place result = Observable.from(Arrays.asList(kiev, london))
                .doOnNext(place -> cashRepository.saveCity(place))
                .flatMap(place -> cashRepository.getCity(CITY_ID, RU))
                .subscribeOn(Schedulers.immediate())
                .test().getOnNextEvents().get(0);

        Assert.assertEquals(kiev.getName(), result.getName());
        Assert.assertEquals(kiev.getId(), result.getId());
        Assert.assertEquals(kiev.getCountry(), result.getCountry());
    }

    @Test
    public void saveCityTest() {
        Place kiev = getPlace(KIEV_CITY, UA, RU, CITY_ID, LAT, LON);
        int resultCount = Observable.just(kiev)
                .doOnNext(place -> cashRepository.saveCity(place))
                .flatMap(place -> cashRepository.getCity(CITY_ID, RU))
                .subscribeOn(Schedulers.immediate())
                .test().assertCompleted().assertNoErrors().getValueCount();
        Assert.assertEquals(resultCount, 1);
    }

    @Test
    public void saveForecastTest() {
        Integer date = 1583020800;
        Integer secondDate = 1583107200;
        List<DailyForecast> dailyForecasts = Arrays.asList(getDailyForecast(date), getDailyForecast(secondDate));
        Place place = getPlace(KIEV_CITY, UA, RU, CITY_ID, LAT, LON);
        SynopticForecast result = Observable.just(place)
                .doOnNext(place1 -> cashRepository.saveCity(place1))
                .flatMap(place12 -> Observable.just(dailyForecasts), SynopticForecast::new)
                .doOnNext(synopticForecast -> cashRepository.saveWeatherForecast(synopticForecast))
                .flatMap(synopticForecast -> cashRepository.getWeatherForecast(LAT, LON, date))
                .subscribeOn(Schedulers.immediate())
                .test()
                .assertCompleted()
                .assertNoErrors().getOnNextEvents().get(0);
        Assert.assertEquals(place.getName(), result.getPlace().getName());
        Assert.assertEquals(dailyForecasts.size(), result.getDailyForecast().size());
    }
}