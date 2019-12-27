package com.aaa.bbb.ccc.data.repository.intrf;

import rx.Scheduler;

public interface ISchedulerRepository {
    Scheduler getIO();
    Scheduler getComputation();
    Scheduler getMain();
}
