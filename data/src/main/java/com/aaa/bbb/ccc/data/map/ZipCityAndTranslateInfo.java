package com.aaa.bbb.ccc.data.map;

import androidx.core.util.Pair;

import com.aaa.bbb.ccc.data.model.City;

import rx.functions.Func2;

public class ZipCityAndTranslateInfo implements Func2<Pair<String,String>,City, City> {
    @Override
    public City call( Pair<String, String> stringStringPair,City city) {
        city.setName(stringStringPair.first);
        city.setLangName(stringStringPair.second);
        return city;
    }
}
