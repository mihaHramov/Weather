package com.aaa.bbb.ccc.data.utils;

import java.util.Calendar;
import java.util.Date;

public class DateConverter {
    private DateConverter() {
        throw new IllegalStateException("Utility class");
    }

    public static Calendar getDateByInteger(Integer integer) {
        Date date = new Date((long) integer * 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Integer convertToInteger(Calendar calendar) {
        return (int) calendar.getTime().getTime()/1000;
    }
}
