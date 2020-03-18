package com.aaa.bbb.ccc.data.repository.date;

import com.aaa.bbb.ccc.utils.DateServices;

import org.junit.Assert;
import org.junit.Test;

import mockit.Mock;
import mockit.MockUp;

public class DateRepositoryTest {
    private static Integer fakeCurrentDateInMillis = 1599609600;

    private static class DateServicesMock extends MockUp<DateServices> {
        @Mock
        public static Integer getCurrentTime() {
            return fakeCurrentDateInMillis;
        }
    }

    @Test
    public void getCurrentTime() {
        new DateServicesMock();
        IDateRepository dateRepository = new DateRepository();
        Assert.assertEquals(fakeCurrentDateInMillis, dateRepository.getCurrentTime());
    }
}