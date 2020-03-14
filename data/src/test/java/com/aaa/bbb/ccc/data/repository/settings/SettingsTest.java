package com.aaa.bbb.ccc.data.repository.settings;

import android.content.res.Configuration;
import android.content.res.Resources;

import com.aaa.bbb.ccc.data.R;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Locale;

import rx.schedulers.Schedulers;

@RunWith(RobolectricTestRunner.class)
@Config(application = android.app.Application.class, manifest = "src/main/AndroidManifest.xml", sdk = 23)
public class SettingsTest {
    private ISettingsRepository settingsRepository;

    @Before
    public void setUp() {
        settingsRepository = new SettingsRepository(RuntimeEnvironment.application);
    }

    @Test
    public void testLang() {
        String lang = "ru";
        Locale locale = new Locale(lang, "RU");
        Locale.setDefault(locale);
        Resources res = RuntimeEnvironment.application.getResources();
        Configuration config = res.getConfiguration();
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());
        String result = settingsRepository.getLanguage()
                .subscribeOn(Schedulers.immediate())
                .test()
                .assertCompleted()
                .assertNoErrors()
                .getOnNextEvents().get(0);
        Assert.assertEquals(result,lang);

    }

    @Test
    public void getUnits() {
        String metric = RuntimeEnvironment.application.getString(R.string.default_unit);
        String result = settingsRepository.getUnits()
                .subscribeOn(Schedulers.immediate())
                .test().getOnNextEvents().get(0);
        Assert.assertEquals(metric, result);
    }

}