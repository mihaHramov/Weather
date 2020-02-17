package com.aaa.bbb.ccc.data.map;

import androidx.core.util.Pair;

import com.aaa.bbb.ccc.model.Place;

import rx.functions.Func2;

public class ZipCityAndTranslateInfo implements Func2<Pair<String,String>, Place, Place> {
    @Override
    public Place call(Pair<String, String> stringStringPair, Place place) {
        place.setName(stringStringPair.first);
        place.setLangName(stringStringPair.second);
        return place;
    }
}
