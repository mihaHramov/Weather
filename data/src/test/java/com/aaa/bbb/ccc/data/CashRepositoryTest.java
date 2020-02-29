package com.aaa.bbb.ccc.data;

import com.aaa.bbb.ccc.data.repository.cash.CashRepository;
import com.aaa.bbb.ccc.data.repository.cash.ICashRepository;
import com.aaa.bbb.ccc.model.Place;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Arrays;

import rx.Observable;
import rx.functions.Func1;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static com.aaa.bbb.ccc.data.DbTestHelper.getPlace;


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
        TestSubscriber<Place> testSubscriber = new TestSubscriber<>();
        Observable.from(Arrays.asList(kiev, london))
                .doOnNext(place -> cashRepository.saveCity(place))
                .flatMap((Func1<Place, Observable<Place>>) place -> cashRepository.getCity(CITY_ID, RU))
                .subscribeOn(Schedulers.immediate())
                .subscribe(testSubscriber);

        Place result = testSubscriber.getOnNextEvents().get(0);
        Assert.assertEquals(kiev.getName(), result.getName());
        Assert.assertEquals(kiev.getId(), result.getId());
        Assert.assertEquals(kiev.getCountry(), result.getCountry());
    }

    @Test
    public void saveCityTest() {
        Place kiev = getPlace(KIEV_CITY, UA, RU, CITY_ID, LAT, LON);
        int resultCount = Observable.just(kiev)
                .doOnNext(place -> cashRepository.saveCity(place))
                .flatMap((Func1<Place, Observable<Place>>) place -> cashRepository.getCity(CITY_ID, RU))
                .subscribeOn(Schedulers.immediate())
                .test().assertCompleted().assertNoErrors().getValueCount();
        Assert.assertEquals(resultCount, 1);
    }

}
