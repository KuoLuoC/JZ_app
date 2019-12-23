package com.wzq.jz_app.presenter.contract;

import com.wzq.jz_app.base.BaseContract;
import com.wzq.jz_app.model.bean.remote.MyUser;

/**
 * 作者：wzq
 * 邮箱：wang_love152@163.com
 */
public interface LandContract extends BaseContract {

    interface View extends BaseContract.BaseView {

        void landSuccess(MyUser user);

    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        /**
         * 用户登陆
         */
        void login(String username, String password);

        /**
         * 用户注册
         */
        void signup(String username, String password, String mail);
    }
}
