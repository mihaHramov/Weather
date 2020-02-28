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

    @Before
    @Override
    public void setUp() {
        super.setUp();
        cashRepository = new CashRepository(this.db);
    }

    @Test
    public void getCityTest() {
        String kievLang = "ru";
        Integer kievId = 2345;
        Place kiev = getPlace("Kiev", "ua", kievLang, kievId, "23.57", "48.19");
        Place london = getPlace("London", "uk", "en", 2345, "23.57", "48.19");
        TestSubscriber<Place> testSubscriber = new TestSubscriber<>();
        Observable.from(Arrays.asList(kiev, london))
                .doOnNext(place -> cashRepository.saveCity(place))
                .flatMap((Func1<Place, Observable<Place>>) place -> cashRepository.getCity(kievId, kievLang))
                .subscribeOn(Schedulers.immediate())
                .subscribe(testSubscriber);

        Place result = testSubscriber.getOnNextEvents().get(0);
        Assert.assertEquals(kiev.getName(), result.getName());
        Assert.assertEquals(kiev.getId(), result.getId());
        Assert.assertEquals(kiev.getCountry(), result.getCountry());
    }

    @Test
    public void saveCityTest() {
        String kievLang = "ru";
        Integer kievId = 2345;
        Place kiev = getPlace("Kiev", "ua", kievLang, kievId, "23.57", "48.19");
        int resultCount = Observable.just(kiev)
                .doOnNext(place -> cashRepository.saveCity(place))
                .flatMap((Func1<Place, Observable<Place>>) place -> cashRepository.getCity(kievId, kievLang))
                .subscribeOn(Schedulers.immediate())
                .test().assertCompleted().assertNoErrors().getValueCount();
        Assert.assertEquals(resultCount,1);
    }

    @Test
    public void saveWeatherForecastTest(){
        Integer date = 1574812800;
//        getForecast(1,)
    }


}
