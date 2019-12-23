package com.wzq.jz_app.net;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class IoMainScheduler<T> extends BaseScheduler<T>{

    public IoMainScheduler(){
        super(Schedulers.io(), AndroidSchedulers.mainThread());
    }
}
