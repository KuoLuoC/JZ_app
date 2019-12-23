package com.wzq.jz_app.base;

/**
 * 作者：wzq
 * 邮箱：wang_love152@163.com
 */
public interface BaseContract {

    interface BasePresenter<T> {

        void attachView(T view);

        void detachView();
    }

    interface BaseView {

        void onSuccess();

        void onFailure(Throwable e);


    }
}
