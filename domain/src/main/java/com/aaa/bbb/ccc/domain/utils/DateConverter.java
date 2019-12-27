package com.aaa.bbb.ccc.domain.utils;


import java.util.Calendar;
import java.util.Date;

public class DateConverter {
    public static Calendar getDateByInteger(Integer integer) {
        Date date = new Date((long) integer * 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
