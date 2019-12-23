package com.wzq.jz_app.presenter.contract;

import com.wzq.jz_app.base.BaseContract;
import com.wzq.jz_app.model.bean.remote.MyUser;

/**
 * 作者：wzq
 * 邮箱：wang_love152@163.com
 */
public interface UserInfoContract extends BaseContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View>{
        /**
         * 更新用户信息
         */
        void updateUser(MyUser user);
    }
}
