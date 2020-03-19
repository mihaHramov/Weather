package com.aaa.bbb.ccc.data.repository.country;

import android.content.Context;

import com.aaa.bbb.ccc.data.R;
import com.aaa.bbb.ccc.data.model.Country;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import rx.Observable;

public class CountryRepository implements ICountryRepository {
    private Context context;

    public CountryRepository(Context context) {
        this.context = context;
    }

    @Override
    public Observable<Country> getCountryByCode(String iso) {
        String userJson = context.getString(R.string.countries_json);
        Type userListType = new TypeToken<List<Country>>() {}.getType();
        Gson gson = new Gson();
        List<Country> userArray = gson.fromJson(userJson, userListType);
        return Observable.from(userArray).first(country -> country.getIso().equals(iso));
    }
}
