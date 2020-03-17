package com.aaa.bbb.ccc.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.annotation.Config;

import java.util.Calendar;


@RunWith(PowerMockRunner.class)
@Config(application = android.app.Application.class, manifest = "src/main/AndroidManifest.xml", sdk = 23)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest({DateServices.class})
public class DateServicesTest {
    private Integer fakeCurrentDateInMillis = 1599609600;//2020.09.09

    @Test
    public void getDateByInteger() {
        Calendar calendar = DateServices.getDateByInteger(fakeCurrentDateInMillis);
        int resultMonth = calendar.get(Calendar.MONTH);
        int resultDay = calendar.get(Calendar.DAY_OF_MONTH);
        int resultYear = calendar.get(Calendar.YEAR);
        Assert.assertEquals(2020, resultYear);
        Assert.assertEquals(9, resultDay);
        Assert.assertEquals(9 - 1, resultMonth);//mont start with zero
    }

    @Test
    public void getCurrentTime() {
        PowerMockito.mockStatic(System.class);
        PowerMockito.when(System.currentTimeMillis()).thenReturn(fakeCurrentDateInMillis.longValue());
        Integer expected = fakeCurrentDateInMillis / 1000;
        Assert.assertEquals(expected, DateServices.getCurrentTime());
    }
}