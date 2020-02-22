package com.aaa.bbb.ccc.data.repository.PermissionsRepository;

import rx.Observable;

public interface IPermissionsRepository {
    Observable<Boolean> getPermission(String string);
}
