package com.wzq.jz_app.presenter;


import com.wzq.jz_app.base.RxPresenter;
import com.wzq.jz_app.model.bean.remote.MyUser;
import com.wzq.jz_app.presenter.contract.UserInfoContract;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 *作者：wzq
 * 邮箱：wang_love152@163.com
 */
public class UserInfoPresenter extends RxPresenter<UserInfoContract.View>
        implements UserInfoContract.Presenter {

    private String TAG = "UserInfoPresenter";

    @Override
    public void updateUser(MyUser user) {
        user.update(user.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null)
                    mView.onSuccess();
                else
                    mView.onFailure(e);
            }
        });
    }
}
