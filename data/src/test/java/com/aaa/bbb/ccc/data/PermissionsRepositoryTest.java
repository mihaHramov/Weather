package com.aaa.bbb.ccc.data;

import android.Manifest;

import com.aaa.bbb.ccc.data.repository.impl.PermissionsRepository;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class PermissionsRepositoryTest {
    @Mock
    RxPermissions rxPermissions;
    private PermissionsRepository mPermissionsRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)).thenReturn(Observable.just(true));
        mPermissionsRepository = new PermissionsRepository(rxPermissions);
    }

    @Test
    public void testRepositoryAndMap() {
        TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
        mPermissionsRepository.getPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(testSubscriber);
        Boolean result = testSubscriber.getOnNextEvents().get(0);
        Assert.assertEquals(true,result);
        verify(rxPermissions).request(Manifest.permission.ACCESS_FINE_LOCATION);
    }
}
