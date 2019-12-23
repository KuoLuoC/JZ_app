package com.wzq.jz_app.net;

import io.reactivex.observers.ResourceObserver;

public class BaseObserver<T> extends ResourceObserver<T> {

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
