package com.aaa.bbb.ccc.data.repository.date;

import com.aaa.bbb.ccc.utils.DateServices;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(RobolectricTestRunner.class)
@Config(application = android.app.Application.class, manifest = "src/main/AndroidManifest.xml", sdk = 23)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest({DateServices.class})
public class DateRepositoryTest {
    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @Test
    public void getCurrentTime() {
        IDateRepository dateRepository = new DateRepository();
        PowerMockito.mockStatic(DateServices.class);
        Integer fakeCurrentDateInMillis = 1599609600;//2020.09.09
        when(DateServices.getCurrentTime()).thenReturn(fakeCurrentDateInMillis);
        Assert.assertEquals(fakeCurrentDateInMillis, dateRepository.getCurrentTime());
    }
}