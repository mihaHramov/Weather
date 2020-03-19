package com.aaa.bbb.ccc.data.repository.country;

import com.aaa.bbb.ccc.data.model.Country;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(application = android.app.Application.class, manifest = "src/main/AndroidManifest.xml", sdk = 25)
public class CountryRepositoryTest {
    private ICountryRepository repository;

    @Before
    public void setUp()  {
        repository = new CountryRepository(RuntimeEnvironment.application);
    }

    @Test
    public void getCountryByCode() {
        String expectedIso ="SX";
        String expectedLat = "18.033333";
        String expectedLon = "-63.05";
        int expectedSize = 1;
        List<Country> country = repository.getCountryByCode(expectedIso)
                .test()
                .assertNoErrors()
                .assertCompleted()
                .getOnNextEvents();
        Assert.assertEquals(expectedSize,country.size());
        Assert.assertEquals(expectedIso,country.get(0).getIso());
        Assert.assertEquals(expectedLat,country.get(0).getLat());
        Assert.assertEquals(expectedLon,country.get(0).getLon());
    }
}