package com.wzq.jz_app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.wzq.jz_app.R;
import com.wzq.jz_app.base.BaseActivity;
import com.wzq.jz_app.model.bean.remote.MyUser;
import com.wzq.jz_app.utils.DataClearUtils;
import com.wzq.jz_app.utils.GlideCacheUtil;
import com.wzq.jz_app.utils.ProgressUtils;
import com.wzq.jz_app.utils.SnackbarUtils;
import com.wzq.jz_app.utils.ToastUtils;
import com.wzq.jz_app.widget.CommonItemLayout;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * 作者：wzq
 * 邮箱：wang_love152@163.com
 * 设置activity
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private CommonItemLayout changeCL;
    private CommonItemLayout forgetCL;
    private CommonItemLayout storeCL;
    private CommonItemLayout payCL;
    private CommonItemLayout exportCL;

    private MyUser currentUser;
    private View outexit;
    private CommonItemLayout cil_vesion;

    /************************************************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        currentUser = BmobUser.getCurrentUser(MyUser.class);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        toolbar = findViewById(R.id.toolbar);
        changeCL = findViewById(R.id.cil_change);
        forgetCL = findViewById(R.id.cil_forget);
        storeCL = findViewById(R.id.cil_store);
        payCL = findViewById(R.id.cil_pay);
        exportCL = findViewById(R.id.cil_export);
        outexit =findViewById(R.id.exit);
        cil_vesion =findViewById(R.id.cil_vesion);

        //初始化Toolbar
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        try {
            storeCL.setRightText(DataClearUtils.getTotalCacheSize(SettingActivity.this)+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initClick() {
        super.initClick();
        changeCL.setOnClickListener(this);
        forgetCL.setOnClickListener(this);
        storeCL.setOnClickListener(this);
        payCL.setOnClickListener(this);
        exportCL.setOnClickListener(this);
        outexit.setOnClickListener(this);
        cil_vesion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cil_change:  //修改密码
                if (currentUser == null)
//                    SnackbarUtils.show(mContext, "请先登陆");
                    Toast.makeText(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
                else
                showChangeDialog();
                break;
            case R.id.cil_forget:  //忘记密码
                if (currentUser == null)
//                    SnackbarUtils.show(mContext, "请先登陆");
                    Toast.makeText(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
                else
                showForgetPwDialog();
                break;
            case R.id.cil_store:  //缓存
                showCacheDialog();
                break;

            case R.id.cil_pay:  //支付方式管理
//                startActivity(new Intent(this,PayEditActivity.class));
                break;
            case R.id.cil_export://导出账单
//                String filename= Environment.getExternalStorageDirectory()+"/AndroidExcelDemo";
//                File file=new File(filename);
//                if(!file.exists()){
//                    file.mkdirs();
//                }
//                String excleCountName="/Wangzhiqiang.xls";
//                String[] title = {"本地id", "服务器端id", "金额","内容","用户id","支付方式","图标","账单分类","分类图标","创建时间","收入支出","版本"};
//                String sheetName = "demoSheetName";
//
//                List<BBill> bBills = LocalRepository.getInstance().getBBills();
//                filename=filename+excleCountName;
//                ExcelUtil.initExcel(filename, title);
//                ExcelUtil.writeObjListToExcel(bBills, filename,getApplicationContext());
                break;
            case R.id.exit:
                exitUser();
                break;

            case R.id.cil_vesion:
                Toast.makeText(getApplicationContext(), "已是最新版本！敬请使用", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 显示忘记密码对话框
     */
    public void showForgetPwDialog() {
        new MaterialDialog.Builder(this)
                .title("找回密码")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("请输入注册邮箱", null, (dialog, input) -> {
                    if (input.equals("")) {
                        SnackbarUtils.show(mContext, "内容不能为空！");

                    } else {
                        //找回密码
                        BmobUser.resetPasswordByEmail(input.toString(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    ToastUtils.show(mContext, "重置密码请求成功，请到邮箱进行密码重置操作");
                                } else {
                                    ToastUtils.show(mContext, "失败:" + e.getMessage());
                                }
                            }
                        });
                    }
                })
                .positiveText("确定")
                .negativeText("取消")
                .show();
    }

    /**
     * 显示修改密码对话框
     */
    public void showChangeDialog() {

        new MaterialDialog.Builder(mContext)
                .title("修改密码")
                .customView(R.layout.dialog_change_password, false)
                .positiveText("确定")
                .onPositive((dialog, which) -> {
                    View view = dialog.getCustomView();
                    TextInputLayout til = view.findViewById(R.id.change_til_password);
                    TextInputLayout til1 = view.findViewById(R.id.change_til_repassword);
                    String passport = til.getEditText().getText().toString();
                    String repaspsort = til.getEditText().getText().toString();
                    if (passport.equals("") || repaspsort.equals("")) {
                        ToastUtils.show(mContext, "不能为空！");
                    } else if (passport.equals(repaspsort)) {
                        //修改密码
                        changePw(passport);
                    } else {
                        ToastUtils.show(mContext, "两次输入不一致！");
                    }
                })
                .negativeText("取消")
                .show();
    }

    /**
     * 显示清除缓存对话框
     */
    public void showCacheDialog() {

        new MaterialDialog.Builder(mContext)
                .title("清除缓存")
                .positiveText("确定")
                .onPositive((dialog, which) -> {
//                    GlideCacheUtil.getInstance().clearImageDiskCache(mContext);
                    //清除本地数据
//                    LocalRepository.getInstance().deleteAllBills();
                    DataClearUtils.clearAllCache(mContext);
                    try {
                        storeCL.setRightText(DataClearUtils.getTotalCacheSize(mContext)+"");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .negativeText("取消")
                .show();
    }

    /**
     * 更新用户密码
     */
    public void changePw(String password) {
        if (currentUser == null)
            return;
        ProgressUtils.show(mContext, "正在修改...");
        currentUser.setPassword(password);
        currentUser.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                ProgressUtils.dismiss();
                if (e != null)
                    ToastUtils.show(mContext, "修改失败");
                else {
                    ToastUtils.show(mContext, "修改密码成功,请重新登录");
                    Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                    intent.putExtra("exit", "1");
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * * 退出登陆 Dialog
     */
    private void exitUser(){
        String str="";
        if (currentUser == null)
           str="您未登录！是否跳转到登录页面";
        else{
            str="退出后将不能操作部分功能";
        }
        new MaterialDialog.Builder(mContext)
                .title("确认退出")
                .content(str)
                .positiveText("确定")
                .onPositive((dialog, which) -> {
                    GlideCacheUtil.getInstance().clearImageDiskCache(mContext);
                    MyUser.logOut();
                    //清除本地数据
//                    LocalRepository.getInstance().deleteAllBills();
                     //退出登录，同时清除缓存用户对象。
                    BmobUser.logOut();
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.putExtra("exit", "1");
                    startActivity(intent);
                    EventBus.getDefault().post("finish");
                })
                .negativeText("取消")
                .show();
    }}


