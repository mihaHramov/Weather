package com.aaa.bbb.ccc.utils;
public final class Const {
    private Const() {
        throw new IllegalStateException("Utility class");
    }
    public static final String TRANSLATE_API_BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/";
    public static final String OPEN_WEATHER_MAP_API_BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String OPEN_WEATHER_MAP_API_BASE_LANGUAGE = "en";
}
