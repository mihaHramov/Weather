package com.aaa.bbb.ccc.weather.domain.utils;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class DateConverter {
    public static Calendar getDateByInteger(Integer integer) {
        Log.d("mihaHramov1","integer"+integer);
        Date date = new java.util.Date((long) integer * 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
