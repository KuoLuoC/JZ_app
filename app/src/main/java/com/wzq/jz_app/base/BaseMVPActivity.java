package com.wzq.jz_app.base;

/**
 * 作者：wzq
 * 邮箱：wang_love152@163.com
 */
public abstract class BaseMVPActivity<T extends BaseContract.BasePresenter> extends BaseActivity{

    protected T mPresenter;

    protected abstract T bindPresenter();

    @Override
    protected void processLogic() {
        attachView(bindPresenter());
    }

    private void attachView(T presenter){
        mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
