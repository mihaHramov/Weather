package com.aaa.bbb.ccc.data.map;

import rx.functions.Func1;

public class TranslateLanguageMap implements Func1<String, String> {
    private String defaultLanguage;

    @Override
    public String call(String s) {
        return defaultLanguage + "-" + s;
    }

    public TranslateLanguageMap(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }
}
