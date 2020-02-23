package com.aaa.bbb.ccc.data.repository.permissions;

import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Observable;

public class PermissionsRepository implements IPermissionsRepository {
    private RxPermissions mPermissions;

    public PermissionsRepository(RxPermissions permissions) {
        this.mPermissions = permissions;
    }

    @Override
    public Observable<Boolean> getPermission(String string) {
        return mPermissions.request(string);
    }
}
