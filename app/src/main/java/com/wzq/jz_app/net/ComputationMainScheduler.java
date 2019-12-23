package com.wzq.jz_app.net;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ComputationMainScheduler<T> extends BaseScheduler<T> {


    public ComputationMainScheduler() {
        super(Schedulers.computation(), AndroidSchedulers.mainThread());
    }
}
