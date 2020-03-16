package com.aaa.bbb.ccc.utils;

import java.util.Calendar;

public class DateServices {
    private DateServices() {
        throw new IllegalStateException("Utility class");
    }

    public static Calendar getDateByInteger(Integer integer) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(integer);
        return calendar;
    }

    public static Integer getCurrentTime() {
        return (int) System.currentTimeMillis() / 1000;
    }
}
