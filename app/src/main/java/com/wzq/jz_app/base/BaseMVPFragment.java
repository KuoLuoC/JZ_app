package com.wzq.jz_app.base;

/**
 * 作者：wzq
 * 邮箱：wang_love152@163.com
 */
public abstract class BaseMVPFragment<T extends BaseContract.BasePresenter> extends BaseFragment
        implements BaseContract.BaseView{

    protected T mPresenter;

    protected abstract T bindPresenter();

    @Override
    protected void processLogic(){
        mPresenter = bindPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
