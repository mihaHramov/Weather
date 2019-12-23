package com.aaa.bbb.ccc.weather.data.repository.intrf;

import rx.Observable;

public interface IPermissionsRepository {
    Observable<Boolean> getPermission(String string);
}
