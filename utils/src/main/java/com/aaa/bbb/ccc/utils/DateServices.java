package com.aaa.bbb.ccc.utils;

import java.util.Calendar;
import java.util.Date;

public class DateServices {
    private static final Integer ONE_THOUSAND = 1000;

    private DateServices() {
        throw new IllegalStateException("Utility class");
    }

    public static Calendar getDateByInteger(Integer integer) {
        Date date = new Date((long) integer * ONE_THOUSAND);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

    public static Integer getCurrentTime() {
        return (int) System.currentTimeMillis() / ONE_THOUSAND;
    }
}
