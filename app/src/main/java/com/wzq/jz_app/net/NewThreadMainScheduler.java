package com.wzq.jz_app.net;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewThreadMainScheduler<T> extends BaseScheduler<T>{

    public NewThreadMainScheduler(){

        super(Schedulers.newThread(), AndroidSchedulers.mainThread());
    }
}
