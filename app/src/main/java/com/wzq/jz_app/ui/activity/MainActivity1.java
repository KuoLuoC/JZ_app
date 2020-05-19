package com.wzq.jz_app.ui.activity;

/**
 * 作者：wzq on 2019/4/7.
 * 邮箱：wang_love152@163.com
 */


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.wzq.jz_app.MyApplication;
import com.wzq.jz_app.R;
import com.wzq.jz_app.base.BaseActivity;
import com.wzq.jz_app.model.bean.remote.MyUser;
import com.wzq.jz_app.net.IoMainScheduler;
import com.wzq.jz_app.ui.fragment.MineFragment;
import com.wzq.jz_app.ui.news.NewsBean;
import com.google.gson.Gson;

import cn.bmob.v3.BmobUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class MainActivity1 extends BaseActivity {
    // tab用参数
    private TabHost tabHost;
    private RadioGroup radiogroup;
    private int menuid;
    private int lastid;//点击记账前的页面id
    protected static final int USERINFOACTIVITY_CODE = 0;
    protected static final int LOGINACTIVITY_CODE = 1;
    private RadioButton tb_group;
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    private String theme;
    private MyUser currentUser;


    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main1;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        test();
        currentUser = BmobUser.getCurrentUser(MyUser.class);
        //判断是否登录，登录则将未登录的账单
        if(currentUser!=null){

        }


        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        Intent it_get = getIntent();
         theme = it_get.getStringExtra("THEME");

        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("home").setIndicator("")
                .setContent(R.id.main_activity_home));
        tabHost.addTab(tabHost.newTabSpec("chart").setIndicator("")
                .setContent(R.id.fragment_month_chart));
        tabHost.addTab(tabHost.newTabSpec("more").setIndicator("")
                .setContent(R.id.main_activity_more));
        tabHost.addTab(tabHost.newTabSpec("mine").setIndicator("")
                .setContent(R.id.main_activity_mine));

        if("4".equals(theme)){
            tb_group = findViewById(R.id.radio_mine);
            tb_group.setChecked(true);
            setCurrentTabWithAnim(tabHost.getCurrentTab(), 3, "mine");
        }
        RadioButton[] rb = new RadioButton[5];
        //将RadioButton装进数组中
        rb[0] = (RadioButton) findViewById(R.id.radio_home);
        rb[1] = (RadioButton) findViewById(R.id.radio_chart);
        rb[2] = (RadioButton) findViewById(R.id.radio_add);
        rb[3] = (RadioButton) findViewById(R.id.radio_more);
        rb[4] = (RadioButton) findViewById(R.id.radio_mine);

        //for循环对每一个RadioButton图片进行缩放
        for (int i = 0; i < rb.length; i++) {
            //挨着给每个RadioButton加入drawable限制边距以控制显示大小
            Drawable[] drawables = rb[i].getCompoundDrawables();
            //获取drawables，2/5表示图片要缩小的比例
            Rect r = new Rect(0, 0, drawables[1].getMinimumWidth() * 7 / 30, drawables[1].getMinimumHeight() * 7 / 30);
            //定义一个Rect边界
            drawables[1].setBounds(r);
            //给每一个RadioButton设置图片大小
            if (i != 2) {
                rb[i].setCompoundDrawables(null, drawables[1], null, null);
            } else {
                Rect r1 = new Rect(0, 0, drawables[1].getMinimumWidth() * 2 / 3, drawables[1].getMinimumHeight() * 2 / 3);
                //定义一个Rect边界
                drawables[1].setBounds(r1);
                rb[i].setCompoundDrawables(null, drawables[1], null, null);

            }
        }

        radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                menuid = checkedId;
                int currentTab = tabHost.getCurrentTab();
                switch (checkedId) {
                    case R.id.radio_home:
//                        tabHost.setCurrentTabByTag("main");
                        //如果需要动画效果就使用
                        setCurrentTabWithAnim(currentTab, 0, "home");
//                        getSupportActionBar().setTitle("账单");//设置顶部标题
                        lastid = checkedId;
                        break;
                    case R.id.radio_chart:
                        //tabHost.setCurrentTabByTag("mycenter");
                        setCurrentTabWithAnim(currentTab, 1, "chart");
                        lastid = checkedId;
////                        getSupportActionBar().setTitle("图表");
                        break;
                    case R.id.radio_more:
                        setCurrentTabWithAnim(currentTab, 2, "more");
                        lastid = checkedId;
//					tabHost.setCurrentTabByTag("search");
//                        getSupportActionBar().setTitle("新闻");
                        break;

                    case R.id.radio_mine:
//					tabHost.setCurrentTabByTag("mine");
                        setCurrentTabWithAnim(currentTab, 3, "mine");
                        lastid = checkedId;
//                        getSupportActionBar().setTitle("我的");
                        break;

                    case R.id.radio_add:
                        //fab点击事件
//                        if (BmobUser.getCurrentUser(MyUser.class) == null) {
//                            Toast.makeText(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
////                                SnackbarUtils.show(getApplicationContext(), "请先登录");
//                        } else {
                            Intent intent = new Intent(getBaseContext(), AddActivity.class);
                            startActivityForResult(intent, 0);
//                        }
                }
                // 刷新actionbar的menu
                getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        switch (menuid) {
            case R.id.radio_home:
                getMenuInflater().inflate(R.menu.main, menu);
                break;
            case R.id.radio_chart:
                menu.clear();
                break;
            case R.id.radio_add:
                menu.clear();
                break;
            case R.id.radio_more:
                menu.clear();
                break;
            case R.id.radio_mine:
                menu.clear();
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 这个方法是关键，用来判断动画滑动的方向
    private void setCurrentTabWithAnim(int now, int next, String tag) {
        if (now > next) {
            tabHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
            tabHost.setCurrentTabByTag(tag);
            tabHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
        } else {
            tabHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
            tabHost.setCurrentTabByTag(tag);
            tabHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
        }
    }

    /**
     * 监听Activity返回值
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //记住点击记账前一个tab,返回后默认选中之前的状态
        switch (lastid) {
            case R.id.radio_home:
                tb_group = findViewById(R.id.radio_home);
                tb_group.setChecked(true);
                break;
            case R.id.radio_chart:
                tb_group = findViewById(R.id.radio_chart);
                tb_group.setChecked(true);
                break;
            case R.id.radio_more:
                tb_group = findViewById(R.id.radio_more);
                tb_group.setChecked(true);
                break;
            case R.id.radio_mine:
                tb_group = findViewById(R.id.radio_mine);
                tb_group.setChecked(true);
                break;
            default:
                tb_group = findViewById(R.id.radio_home);
                tb_group.setChecked(true);
        }

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case USERINFOACTIVITY_CODE:
//                    setDrawerHeaderAccount();
                    break;
                case LOGINACTIVITY_CODE:
//                    setDrawerHeaderAccount();
                    break;
            }
        }
    }


    //退出时的时间
    private long mExitTime;
    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity1.this, "再点一次，返回桌面", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
//            直接关闭当前活动页面
//            this.finish();
//            System.exit(0);

          //moveTaskToBack(false);//方法1退回到桌面进入后台
         //方法2退回到桌面进入后台
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeIntent);
        }
    }

    @Override
    public void eventBusListener(String tag) {
        super.eventBusListener(tag);
    }



    /**
     * 新闻列表
     */
    private void test() {
        MyApplication.application.getApiService().test().compose
                (new IoMainScheduler<>()).subscribe(new Observer<NewsBean>() {
            @Override
            public void onSubscribe(Disposable d) {
                Toast toast = Toast.makeText(getApplicationContext(), "加载数据", Toast.LENGTH_SHORT);

            }

            @Override
            public void onNext(NewsBean responseBody) {
                if (responseBody != null) {
                    preferences = getSharedPreferences("test", MODE_PRIVATE);
                    editor = preferences.edit();
                    Gson gson=new Gson();
                    String result=gson.toJson(responseBody);
                    editor.putString("mode",result);
                    editor.commit();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
