package com.aaa.bbb.ccc.data.map;

import android.annotation.SuppressLint;

import com.aaa.bbb.ccc.data.model.BriefWeatherForecast;
import com.aaa.bbb.ccc.data.model.api.weather.List;
import com.aaa.bbb.ccc.utils.DateConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import rx.functions.Func1;


public class FromResponseToHasTableOfBriefWeatherForecast implements Func1<java.util.List<List>, Map<Integer, java.util.List<BriefWeatherForecast>>> {
    @SuppressLint("UseSparseArrays")
    private Map<Integer, java.util.List<BriefWeatherForecast>> hasTableOfForecast = new HashMap<>();

    @Override
    public Map<Integer, java.util.List<BriefWeatherForecast>> call(java.util.List<List> listWeathers) {
        for (List listWeatherItem : listWeathers) {
            BriefWeatherForecast briefWeatherForecast = new FromListToBriefWeatherForecast().call(listWeatherItem);
            Calendar calendar = DateConverter.getDateByInteger(briefWeatherForecast.getDate());
            Integer dayNumber = calendar.get(Calendar.DAY_OF_MONTH);
            if (!hasTableOfForecast.containsKey(dayNumber)) {
                hasTableOfForecast.put(dayNumber, new ArrayList<>());
            }
            Objects.requireNonNull(hasTableOfForecast.get(dayNumber)).add(briefWeatherForecast);

        }
        return hasTableOfForecast;
    }
}
