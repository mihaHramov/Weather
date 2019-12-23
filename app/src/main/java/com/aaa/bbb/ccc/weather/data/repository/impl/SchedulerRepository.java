package com.aaa.bbb.ccc.weather.data.repository.impl;

import com.aaa.bbb.ccc.weather.data.repository.intrf.ISchedulerRepository;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SchedulerRepository implements ISchedulerRepository {
    public Scheduler getIO() {
        return Schedulers.io();
    }

    public Scheduler getComputation() {
        return Schedulers.computation();
    }

    public Scheduler getMain() {
        return AndroidSchedulers.mainThread();
    }

}
