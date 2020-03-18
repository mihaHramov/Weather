package com.aaa.bbb.ccc.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

import mockit.Mock;
import mockit.MockUp;



public class DateServicesTest {
    private static Integer fakeCurrentDateInMillis = 1599609600;//2020.09.09

    private static class SystemMock extends MockUp<System> {

        @Mock
        public static long currentTimeMillis() {
            return fakeCurrentDateInMillis;
        }
    }

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
        new SystemMock();
        Integer expected = fakeCurrentDateInMillis / 1000;
        Assert.assertEquals(expected, DateServices.getCurrentTime());
    }
}