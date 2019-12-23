package com.wzq.jz_app.net;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TrampolineMainScheduler<T> extends BaseScheduler<T> {

    public TrampolineMainScheduler(){

        super(Schedulers.trampoline(), AndroidSchedulers.mainThread());
    }
}
