package com.aaa.bbb.ccc.utils;

import java.util.Calendar;
import java.util.Date;

public class DateServices {
    private DateServices() {
        throw new IllegalStateException("Utility class");
    }

    public static Calendar getDateByInteger(Integer integer) {
        Date date = new Date((long) integer * 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
    public static Integer getCurrentTime(){
        return (int) (new Date().getTime() / 1000);
    }
}
