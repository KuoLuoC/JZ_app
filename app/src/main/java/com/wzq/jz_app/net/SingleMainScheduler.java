package com.wzq.jz_app.net;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SingleMainScheduler<T> extends BaseScheduler<T> {

    public SingleMainScheduler(){

        super(Schedulers.single(), AndroidSchedulers.mainThread());
    }
}
