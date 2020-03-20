package com.aaa.bbb.ccc.data.repository.settings;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.LocaleList;
import android.telephony.TelephonyManager;

import com.aaa.bbb.ccc.data.R;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Locale;

import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(application = android.app.Application.class, manifest = "src/main/AndroidManifest.xml", sdk = 25)
public class SettingsTest {
    private ISettingsRepository settingsRepository;
    private Context context;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        settingsRepository = new SettingsRepository(context);
    }

    @Test
    public void testLang() {
        String lang = "ru";
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        context.createConfigurationContext(config);
        String result = settingsRepository.getLanguage()
                .subscribeOn(Schedulers.immediate())
                .test()
                .assertCompleted()
                .assertNoErrors()
                .getOnNextEvents().get(0);
        Assert.assertEquals(result, lang);
    }

    @Test
    public void getUnits() {
        String metric = context.getString(R.string.default_unit);
        String result = settingsRepository.getUnits()
                .subscribeOn(Schedulers.immediate())
                .test().getOnNextEvents().get(0);
        Assert.assertEquals(metric, result);
    }

    @Test
    public void getCountry() {
        Locale expected = Locale.CANADA;
        context = configContext();
        LocaleList localeList = Mockito.mock(LocaleList.class);
        when(context.getResources().getConfiguration().getLocales()).thenReturn(localeList);
        when(context.getResources().getConfiguration().getLocales().get(0)).thenReturn(expected);
        String result = getCountry(context);
        Assert.assertEquals(expected.getCountry(), result);
    }
    @Test
    public void getCountryWhenSystemServiceNotNullAndSimCountryIsoNotEmpty() {
        Locale expected = Locale.CANADA;
        context = configContext();
        LocaleList localeList = Mockito.mock(LocaleList.class);
        when(context.getResources().getConfiguration().getLocales()).thenReturn(localeList);
        when(context.getResources().getConfiguration().getLocales().get(0)).thenReturn(expected);
        TelephonyManager telephonyManager = mock(TelephonyManager.class);
        when(telephonyManager.getSimCountryIso()).thenReturn(expected.getCountry());
        when(context.getSystemService(Context.TELEPHONY_SERVICE)).thenReturn(telephonyManager);
        String result = getCountry(context);
        Assert.assertEquals(expected.getCountry(), result);
    }

    @Test
    public void getCountryWhenSystemServiceNotNullAndSimCountryIsoEmpty() {
        Locale expected = Locale.CANADA;
        context = configContext();
        LocaleList localeList = Mockito.mock(LocaleList.class);
        when(context.getResources().getConfiguration().getLocales()).thenReturn(localeList);
        when(context.getResources().getConfiguration().getLocales().get(0)).thenReturn(Locale.CHINA);
        TelephonyManager telephonyManager = mock(TelephonyManager.class);
        when(telephonyManager.getSimCountryIso()).thenReturn("");
        when(telephonyManager.getNetworkCountryIso()).thenReturn(expected.getCountry());
        when(context.getSystemService(Context.TELEPHONY_SERVICE)).thenReturn(telephonyManager);
        String result = getCountry(context);
        Assert.assertEquals(expected.getCountry(), result);
    }
    @Test
    @Config(application = android.app.Application.class, manifest = "src/main/AndroidManifest.xml", sdk = 23)
    public void getCountryWhenSdkVersionEquals23() {
        context = configContext();
        Locale expected = Locale.CHINA;
        context.getResources().getConfiguration().locale = expected;
        String result = getCountry(context);
        Assert.assertEquals(expected.getCountry(), result);
    }

    private String getCountry(Context context) {
        return new SettingsRepository(context)
                .getCountry()
                .test()
                .assertCompleted()
                .assertNoErrors()
                .getOnNextEvents()
                .get(0);
    }

    private Context configContext() {
        Context mockContext = Mockito.mock(Context.class);
        Resources resources = Mockito.mock(Resources.class);
        when(mockContext.getResources()).thenReturn(resources);
        Configuration configuration = Mockito.mock(Configuration.class);
        when(mockContext.getResources().getConfiguration()).thenReturn(configuration);
        return mockContext;
    }

}