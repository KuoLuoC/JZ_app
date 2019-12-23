package com.wzq.jz_app.ui.activity;

import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.wzq.jz_app.R;
import com.wzq.jz_app.base.BaseMVPActivity;
import com.wzq.jz_app.model.bean.remote.MyUser;
import com.wzq.jz_app.presenter.LandPresenter;
import com.wzq.jz_app.presenter.contract.LandContract;
import com.wzq.jz_app.utils.ProgressUtils;
import com.wzq.jz_app.utils.SnackbarUtils;
import com.wzq.jz_app.utils.StringUtils;
import com.wzq.jz_app.utils.ToastUtils;
import com.wzq.jz_app.widget.OwlView;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 作者：wzq
 * 邮箱：wang_love152@163.com
 * 用户登录、注册activity
 */
public class LoginActivity extends BaseMVPActivity<LandContract.Presenter>
        implements LandContract.View, View.OnFocusChangeListener, View.OnClickListener {

    private OwlView mOwlView;
    private EditText emailET;
    private EditText usernameET;
    private EditText  usernameET1;
    private EditText passwordET;
    private EditText rpasswordET;
    private TextView signTV;
    private TextView forgetTV;
    private Button loginBtn;

    //是否是登陆操作
    private boolean isLogin = true;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_land;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mOwlView=findViewById(R.id.land_owl_view);
        emailET=findViewById(R.id.login_et_email);
        usernameET=findViewById(R.id.login_et_username);
        usernameET1=findViewById(R.id.login_et_username1);

        passwordET=findViewById(R.id.login_et_password);
        rpasswordET=findViewById(R.id.login_et_rpassword);
        signTV=findViewById(R.id.login_tv_sign);
        forgetTV=findViewById(R.id.login_tv_forget);
        loginBtn=findViewById(R.id.login_btn_login);
    }

    @Override
    protected void initClick() {
        super.initClick();
        passwordET.setOnFocusChangeListener(this);
        rpasswordET.setOnFocusChangeListener(this);
        signTV.setOnClickListener(this);
        forgetTV.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            mOwlView.open();
        } else {
            mOwlView.close();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_login:  //button
                if (isLogin) {
                    login();  //登陆
                } else {
                    sign();  //注册
                }
                break;
            case R.id.login_tv_sign:  //sign
                if (isLogin) {
                    //置换注册界面
                    signTV.setText("登录");
                    loginBtn.setText("注册");
                    rpasswordET.setVisibility(View.VISIBLE);
                    emailET.setVisibility(View.VISIBLE);
                    usernameET.setVisibility(View.GONE);
                    usernameET1.setVisibility(View.VISIBLE);
                } else {
                    //置换登陆界面
                    signTV.setText("注册");
                    loginBtn.setText("登录");
                    usernameET.setVisibility(View.VISIBLE);
                    usernameET1.setVisibility(View.GONE);
                    rpasswordET.setVisibility(View.GONE);
                    emailET.setVisibility(View.GONE);
                }
                isLogin = !isLogin;
                break;
            case R.id.login_tv_forget:  //忘记密码
                showForgetPwDialog();
                break;
            default:
                break;
        }
    }

    /**
     * 执行登陆动作
     */
    public void login() {
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        if (username.length() == 0 || password.length() == 0) {
            SnackbarUtils.show(mContext, "用户名或密码不能为空");
            return;
        }

        ProgressUtils.show(this, "正在登陆...");

        mPresenter.login(username, password);
    }

    /**
     * 执行注册动作
     */
    public void sign() {
        String email = emailET.getText().toString();
        String username = usernameET1.getText().toString();
        String password = passwordET.getText().toString();
        String rpassword = rpasswordET.getText().toString();
        if (email.length() == 0 || username.length() == 0 || password.length() == 0 || rpassword.length() == 0) {
            SnackbarUtils.show(mContext, "请填写必要信息");
            return;
        }
        if (!StringUtils.checkEmail(email)) {
            SnackbarUtils.show(mContext, "请输入正确的邮箱格式");
            return;
        }
        if (!password.equals(rpassword)) {
            SnackbarUtils.show(mContext, "两次密码不一致");
            return;
        }

        ProgressUtils.show(this, "正在注册...");
        usernameET.setText(username);
        mPresenter.signup(username,password,email);

    }

    /***********************************************************************/

    @Override
    protected LandContract.Presenter bindPresenter() {
        return new LandPresenter();
    }

    @Override
    public void landSuccess(MyUser user) {
        ProgressUtils.dismiss();
        if (isLogin) {
            setResult(RESULT_OK, new Intent());
            Intent intent = new Intent(this, MainActivity1.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            //置换登陆界面
            signTV.setText("注册");
            loginBtn.setText("登录");
            rpasswordET.setVisibility(View.GONE);
            emailET.setVisibility(View.GONE);
            usernameET1.setVisibility(View.GONE);
            usernameET.setVisibility(View.VISIBLE);
            isLogin=true;

        }
        Log.i(TAG,user.toString());
    }

    @Override
    public void onSuccess() {
        ProgressUtils.dismiss();
    }

    @Override
    public void onFailure(Throwable e) {
        ProgressUtils.dismiss();
        SnackbarUtils.show(mContext, e.getMessage());
        Log.e(TAG,e.getMessage());
    }


    //禁止使用返回键返回到上一页,但是可以直接退出程序**
    //退出时的时间
    private long mExitTime;
    //对返回键进行监听
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//
//            exit();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    public void exit() {
//        if ((System.currentTimeMillis() - mExitTime) > 2000) {
//            Toast.makeText(LoginActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
//            mExitTime = System.currentTimeMillis();
//        } else {
////            MyConfig.clearSharePre(this, "users");
//            this.finish();
//            System.exit(0);
//        }
//    }

    /**
     * 显示忘记密码对话框
     */
    public void showForgetPwDialog() {
                new MaterialDialog.Builder(this)
                        .title("找回密码")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input("请输入注册邮箱", null, (dialog, input) -> {
                            String inputStr=input.toString();
                            if (input.equals("")) {
                                SnackbarUtils.show(mContext, "内容不能为空！");
                            } else if(!StringUtils.checkEmail(inputStr)) {
                                Toast.makeText(LoginActivity.this,
                                        "请输入正确的邮箱格式", Toast.LENGTH_LONG).show();
                            }else {
                                //找回密码
                                BmobUser.resetPasswordByEmail(input.toString(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    ToastUtils.show(mContext, "重置密码请求成功，请到邮箱进行密码重置操作");
                                } else {
                                    ToastUtils.show(mContext, "重置密码请求失败，请确认输入邮箱正确！");
                                }
                            }
                        });
                    }
                })
                .positiveText("确定")
                .negativeText("取消")
                .show();
    }

}
