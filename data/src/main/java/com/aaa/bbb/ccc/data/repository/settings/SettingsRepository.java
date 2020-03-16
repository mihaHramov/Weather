package com.aaa.bbb.ccc.data.repository.settings;

import android.content.Context;
import android.content.SharedPreferences;

import com.aaa.bbb.ccc.data.R;
import com.aaa.bbb.ccc.model.Location;

import java.util.Locale;

import rx.Observable;

public class SettingsRepository implements ISettingsRepository {
    private SharedPreferences preferences;
    private Context context;

    public SettingsRepository(Context context) {
        String key = SettingsRepository.class.getName();
        preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        this.context = context;
    }

    @Override
    public Observable<String> getLanguage() {
        return Observable.just(Locale.getDefault().getLanguage());
    }

    @Override
    public Observable<String> getUnits() {
        return Observable.fromCallable(() -> preferences.getString("unit", context.getString(R.string.default_unit)));
    }

    @Override
    public Observable<Location> getDefaultLocation() {
        return Observable.just(new Location("48.05", "37.93"));
    }

}