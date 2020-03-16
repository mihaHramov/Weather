package com.aaa.bbb.ccc.data.repository.date;

import com.aaa.bbb.ccc.utils.DateServices;

public class DateRepository implements IDateRepository {
    @Override
    public Integer getCurrentTime() {
        return DateServices.getCurrentTime();
    }
}
