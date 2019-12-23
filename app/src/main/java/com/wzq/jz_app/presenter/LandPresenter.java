package com.wzq.jz_app.presenter;



import android.widget.Toast;

import com.wzq.jz_app.base.RxPresenter;
import com.wzq.jz_app.model.bean.remote.MyUser;
import com.wzq.jz_app.presenter.contract.LandContract;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * 作者：wzq
 * 邮箱：wang_love152@163.com
 */
public class LandPresenter extends RxPresenter<LandContract.View> implements LandContract.Presenter{
    private String TAG="LandPresenter";
    @Override
    public void login(String username, String password) {
        MyUser.loginByAccount(username, password, new LogInListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if(e==null) {
                    mView.landSuccess(myUser);
                }else {
                    String error=e.toString();
                    if(error.contains("incorrect")){
                        Toast.makeText(getApplicationContext(), "账号或者密码错误！", Toast.LENGTH_SHORT).show();
                        mView.onSuccess();
                    }else {
                        mView.onFailure(e);
                    }
                }
            }
        });
    }
    @Override
    public void signup(String username, String password, String mail) {
        MyUser myUser =new MyUser();
        myUser.setUsername(username);
        myUser.setPassword(password);
        myUser.setEmail(mail);
        myUser.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if(e==null)
                    mView.landSuccess(myUser);
                else{
                    String error=e.toString();
                    if(error.contains("already")){
                        Toast.makeText(getApplicationContext(), "邮箱已经被注册，请重新填写！", Toast.LENGTH_SHORT).show();
                        mView.onSuccess();
                    }else {
                        mView.onFailure(e);
                    }
                }
            }
        });
    }
}
