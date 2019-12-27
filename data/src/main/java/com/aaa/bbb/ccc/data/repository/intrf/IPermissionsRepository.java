package com.aaa.bbb.ccc.data.repository.intrf;

import rx.Observable;

public interface IPermissionsRepository {
    Observable<Boolean> getPermission(String string);
}
