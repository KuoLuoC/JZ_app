package com.wzq.jz_app.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wzq.jz_app.utils.BaseProgressDialog;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 作者：wzq
 * 邮箱：wang_love152@163.com
 */
public abstract class BaseFragment extends Fragment {

    protected String TAG;

    protected CompositeDisposable mDisposable;
    private BaseProgressDialog progressDialog;
    protected Activity mActivity;
    protected Context mContext;

    private View root = null;
    private Toolbar toolbar;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    /*******************************init area*********************************/
    protected void addDisposable(Disposable d){
        if (mDisposable == null){
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(d);
    }


    protected void initData(Bundle savedInstanceState){

    }

    /**
     * 初始化点击事件
     */
    protected void initClick(){
    }

    /**
     * 逻辑使用区
     */
    protected void processLogic(){
    }

    /**
     * 初始化零件
     */
    protected void initWidget(Bundle savedInstanceState){
    }

    protected void beforeDestroy(){

    }
    /**
     * 展示进度条和提示框
     */
    public void showProgressDialog(String text) {
        if (this.progressDialog == null) {
            this.progressDialog = new BaseProgressDialog(getContext());
        }

        this.progressDialog.setText(text);
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
    }

    public void showProgressDialog(String text, boolean cancelable) {
        if (this.progressDialog == null) {
            this.progressDialog = new BaseProgressDialog(getContext());
        }

        this.progressDialog.setText(text);
        this.progressDialog.setCancelable(cancelable);
        this.progressDialog.show();
    }

    public void showProgressDialog(int text) {
        if (this.progressDialog == null) {
            this.progressDialog = new BaseProgressDialog(getContext());
        }

        this.progressDialog.setText(text);
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
    }

    public void showProgressDialog(int text, boolean cancelable) {
        if (this.progressDialog == null) {
            this.progressDialog = new BaseProgressDialog(getContext());
        }

        this.progressDialog.setText(text);
        this.progressDialog.setCancelable(cancelable);
        this.progressDialog.show();
    }

    public void cancelProgressDialog() {
        if (this.progressDialog != null) {
            this.progressDialog.setCancelable(false);
            this.progressDialog.cancel();
        }

    }

    public void cancelProgressDialogImmediately() {
        if (this.progressDialog != null) {
            this.progressDialog.setCancelable(false);
            this.progressDialog.cancelImmediately();
        }

    }

//    public void showTextDialog(String text) {
//        if (this.textDialog == null) {
//            this.textDialog = new BaseTextDialog(this);
//        }
//
//        this.textDialog.setText(text);
//        this.textDialog.show();
//    }
//
//    public void showTextDialog(int text) {
//        if (this.textDialog == null) {
//            this.textDialog = new BaseTextDialog(this);
//        }
//
//        this.textDialog.setText(text);
//        this.textDialog.show();
//    }
//
//    public void cancelTextDialog() {
//        if (this.textDialog != null) {
//            this.textDialog.cancel();
//        }

//    }

    /**
     * 设置监听事件
     */
    protected abstract void setListener();

    /******************************lifecycle area*****************************************/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int resId = getLayoutId();
        root = inflater.inflate(resId,container,false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(savedInstanceState);
        TAG=getName();
        initWidget(savedInstanceState);
        initClick();
        processLogic();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        beforeDestroy();
        if (mDisposable != null){
            mDisposable.clear();
        }
    }

    /**************************公共类*******************************************/
    public String getName(){
        return getClass().getName();
    }

    protected <VT> VT getViewById(int id){
        if (root == null){
            return  null;
        }
        return (VT) root.findViewById(id);
    }


//    /**
//     * 定义toolbar
//     */
//    public void initToolbar(Boolean hasBack) {
////        initClose(rootView,R.id.close_contact);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        title = (TextView) findViewById(R.id.toolbar_title);
//        close_contact = (ImageView) findViewById(R.id.close_contact);
//        return_contact = (ImageView) findViewById(R.id.return_contact);
//        loadingConnect = (ImageView) findViewById(R.id.title_connect_loading);
//        searchZone = (ImageView) findViewById(R.id.search_zone);
//        rightImage = (ImageView) findViewById(R.id.right_icon);
//        rightTitle = (TextView) findViewById(R.id.right_title);
//        if (searchZone != null) {
//            searchZone.setVisibility(View.GONE);
//        }
//
//        //替换掉titlebar
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
//        if (actionBar != null) {
//            if (hasBack) {
//                actionBar.setDisplayHomeAsUpEnabled(true);
//                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                    }
//                });
//            } else {
//                actionBar.setDisplayHomeAsUpEnabled(false);
//            }
//            if (noleft){
////                getClose_contact().setVisibility(View.GONE);
//                getReturn_contact().setVisibility(View.GONE);
//            }
//            else{
//                getReturn_contact().setVisibility(View.VISIBLE);
////                getClose_contact().setVisibility(View.VISIBLE);
//            }
//            actionBar.setDisplayShowTitleEnabled(false);
//        }
//
//
//    }

}
