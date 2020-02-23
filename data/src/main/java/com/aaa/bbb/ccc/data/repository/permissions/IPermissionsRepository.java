package com.aaa.bbb.ccc.data.repository.permissions;

import rx.Observable;

public interface IPermissionsRepository {
    Observable<Boolean> getPermission(String string);
}
