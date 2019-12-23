package com.aaa.bbb.ccc.weather.data.repository.intrf;

import rx.Scheduler;

public interface ISchedulerRepository {
    Scheduler getIO();
    Scheduler getComputation();
    Scheduler getMain();
}
