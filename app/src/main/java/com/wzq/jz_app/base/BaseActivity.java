package com.wzq.jz_app.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.wzq.jz_app.R;
import com.wzq.jz_app.utils.ThemeManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 作者：wzq
 * 邮箱：wang_love152@163.com
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected static String TAG;

    protected Activity mActivity;
    protected Context mContext;

    protected CompositeDisposable mDisposable;

    protected Toolbar mToolbar;

    /****************************abstract*************************************/

    @LayoutRes
    protected abstract int getLayoutId();


    /************************初始化************************************/
    protected void addDisposable(Disposable d) {
        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(d);
    }

    /**
     * 配置Toolbar
     */
    protected void setUpToolbar(Toolbar toolbar) {
    }

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    protected void initData(Bundle savedInstanceState) {
    }

    /**
     * 初始化零件
     */
    protected void initWidget() {
    }

    /**
     * 初始化事件
     */
    protected void initEvent() {
    }

    /**
     * 初始化点击事件
     */
    protected void initClick() {
    }

    /**
     * 执行逻辑
     */
    protected void processLogic() {
    }

    protected void beforeDestroy() {
    }

    /*************************lifecycle*****************************************************/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置 Activity 屏幕方向
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 隐藏 ActionBar
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        // 设置主题色，，，一定要在setView之前
        ThemeManager.getInstance().init(this);

        EventBus.getDefault().register(this);
        setContentView(getLayoutId());
        mActivity = this;
        mContext = this;
        // 设置 TAG
        TAG = this.getClass().getSimpleName();
        //init
        initData(savedInstanceState);
        initToolbar(true);
        initWidget();
        initEvent();
        initClick();
        processLogic();
    }

    protected void initToolbar(boolean b) {
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null) {
            supportActionBar(mToolbar);
            setUpToolbar(mToolbar);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        beforeDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        EventBus.getDefault().unregister(this);
    }

    /**************************used method*******************************************/

    protected ActionBar supportActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(
                (v) -> finish()
        );
        return actionBar;
    }


    /**
 * Evenbus广播通知
 * @param tag
 */
@Subscribe
public void eventBusListener(String tag){
    if (TextUtils.equals(tag, "finish")) {
        finish();
    }
}
}
