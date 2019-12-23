package com.wzq.jz_app.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.wzq.jz_app.MyApplication;
import com.wzq.jz_app.R;
import com.wzq.jz_app.base.BaseMVPFragment;
import com.wzq.jz_app.common.Constants;
import com.wzq.jz_app.model.bean.local.BBill;
import com.wzq.jz_app.model.bean.local.BSort;
import com.wzq.jz_app.model.bean.local.MonthListBean;
import com.wzq.jz_app.model.bean.local.NoteBean;
import com.wzq.jz_app.model.bean.remote.MyUser;
import com.wzq.jz_app.model.event.SyncEvent;
import com.wzq.jz_app.model.repository.LocalRepository;
import com.wzq.jz_app.presenter.MonthListPresenter;
import com.wzq.jz_app.presenter.contract.MonthListContract;
import com.wzq.jz_app.ui.activity.AddActivity;
import com.wzq.jz_app.ui.adapter.MainFragmentPagerAdapter;
import com.wzq.jz_app.ui.adapter.MonthListAdapter;
import com.wzq.jz_app.utils.DateUtils;
import com.wzq.jz_app.utils.ProgressUtils;
import com.wzq.jz_app.utils.SharedPUtils;
import com.wzq.jz_app.utils.SnackbarUtils;
import com.wzq.jz_app.widget.stickyheader.StickyHeaderGridLayoutManager;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobUser;

import static android.app.Activity.RESULT_OK;

/**
 * 作者：wzq
 * 邮箱：wang_love152@163.com
 */
public class HomeFragment extends BaseMVPFragment<MonthListContract.Presenter>
        implements MonthListContract.View {

    private RecyclerView rvList;
    int part, index;
    private static final int SPAN_SIZE = 1;
    private String setYear = DateUtils.getCurYear(DateUtils.FORMAT_Y);
    private String setMonth = DateUtils.getCurMonth(DateUtils.FORMAT_M);

    private StickyHeaderGridLayoutManager mLayoutManager;
    private MonthListAdapter adapter;
    private MonthListListener monthListListener;
    private List<MonthListBean.DaylistBean> list = null;
    private SwipeRefreshLayout swipe;//下拉刷新

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawer;
    private TextView tOutcome;
    private TextView tIncome;
    private TextView tTotal;

    private View drawerHeader;
    private ImageView drawerIv;
    private TextView drawerTvAccount, drawerTvMail;

    protected static final int USERINFOACTIVITY_CODE = 0;
    protected static final int LOGINACTIVITY_CODE = 1;

    // Tab
    private FragmentManager mFragmentManager;
    private MainFragmentPagerAdapter mFragmentPagerAdapter;
    private HomeFragment homeFragment;
    private ChartFragment chartFragment;


    private MyUser currentUser;
    private LinearLayout date;
    private TextView time_year;
    private TextView time_month;
    private TextView tvallin;
    private TextView tvallout;
    private TextView tvall;

    /*****************************************************************************/

    public void changeDate(String year, String month) {
        setYear = year;
        setMonth = month;
        mPresenter.getMonthList(MyApplication.getCurrentUserId(), setYear, setMonth);
        mPresenter.getMonthList1(MyApplication.getCurrentUserId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(SyncEvent event) {
        if (event.getState() == 100)
            mPresenter.getMonthList(MyApplication.getCurrentUserId(), setYear, setMonth);
            mPresenter.getMonthList1(MyApplication.getCurrentUserId());
    }

    /*****************************************************************************/
    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment_home_list;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        //注册 EventBus
        EventBus.getDefault().register(this);

        mLayoutManager = new StickyHeaderGridLayoutManager(SPAN_SIZE);
        mLayoutManager.setHeaderBottomOverlapMargin(5);
        adapter = new MonthListAdapter(mContext, list);

        //第一次进入将默认账单分类添加到数据库
        if (SharedPUtils.isFirstStart(mContext)) {
            Log.i(TAG, "第一次进入将默认账单分类添加到数据库");
            NoteBean note = new Gson().fromJson(Constants.BILL_NOTE, NoteBean.class);
            List<BSort> sorts = note.getOutSortlis();
            sorts.addAll(note.getInSortlis());
            LocalRepository.getInstance().saveBsorts(sorts);
            LocalRepository.getInstance().saveBPays(note.getPayinfo());
        }
        homeFragment = new HomeFragment();

    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        rvList = getViewById(R.id.rv_list);
        date = getViewById(R.id.top_date);


        //顶部主题色区域（收入、支出、结余）
        time_year = getViewById(R.id.top_year);
        time_month = getViewById(R.id.top_month);
        tOutcome = getViewById(R.id.t_outcome);
        tIncome = getViewById(R.id.t_income);
        tTotal = getViewById(R.id.t_total);
        tvall =getViewById(R.id.tv_all);
        tvallout =getViewById(R.id.tv_allout);
        tvallin =getViewById(R.id.tv_allin);


        rvList.setItemAnimator(new DefaultItemAnimator() {
            @Override
            public boolean animateRemove(RecyclerView.ViewHolder holder) {
                dispatchRemoveFinished(holder);
                return false;
            }
        });
        rvList.setLayoutManager(mLayoutManager);
        rvList.setAdapter(adapter);
        swipe = getViewById(R.id.swipe_list);
        //改变加载显示的颜色
        swipe.setColorSchemeColors(getResources().getColor(R.color.text_red), getResources().getColor(R.color.text_red));
        //设置向下拉多少出现刷新
        swipe.setDistanceToTriggerSync(200);
        //设置刷新出现的位置
        swipe.setProgressViewEndTarget(false, 200);
        swipe.setOnRefreshListener(() -> {
            swipe.setRefreshing(false);
            mPresenter.getMonthList(MyApplication.getCurrentUserId(), setYear, setMonth);
            mPresenter.getMonthList1(MyApplication.getCurrentUserId());
        });
    }


    @Override
    protected void initClick() {
        super.initClick();
        //fab点击事件
//        floatBtn.setOnClickListener(v -> {
//            if (BmobUser.getCurrentUser(MyUser.class) == null)
//                SnackbarUtils.show(mContext, "请先登录");
//            else {
//                Intent intent = new Intent(getContext(), AddActivity.class);
//                startActivityForResult(intent, 0);
//            }
//        });
        //adapter的侧滑选项事件监听
        date.setOnClickListener(v -> {
            currentUser = BmobUser.getCurrentUser(MyUser.class);
            new TimePickerBuilder(mContext, (Date date, View vc) -> {
                time_year.setText(DateUtils.date2Str(date, "yyyy") + "年");
                time_month.setText(DateUtils.date2Str(date, "MM"));
                changeDate(DateUtils.date2Str(date, "yyyy"), DateUtils.date2Str(date, "MM"));
            }).setType(new boolean[]{true, true, false, false, false, false})
                    .setRangDate(null, Calendar.getInstance())
                    .isDialog(true)//是否显示为对话框样式
                    .build().show();
        });


        adapter.setOnStickyHeaderClickListener(new MonthListAdapter.OnStickyHeaderClickListener() {
            @Override
            public void OnDeleteClick(BBill item, int section, int offset) {
                item.setVersion(-1);
                //将删除的账单版本号设置为负，而非直接删除
                //便于同步删除服务器数据
                ProgressUtils.show(mContext, "正在删除...");
                mPresenter.updateBill(item);
                part = section;
                index = offset;
            }

            @Override
            public void OnEditClick(BBill item, int section, int offset) {
                Intent intent = new Intent(mContext, AddActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("id", item.getId());
                bundle.putString("rid", item.getRid());
                bundle.putString("sortName", item.getSortName());
                bundle.putString("payName", item.getPayName());
                bundle.putString("content", item.getContent());
                bundle.putDouble("cost", item.getCost());
                bundle.putLong("date", item.getCrdate());
                bundle.putBoolean("income", item.isIncome());
                bundle.putInt("version", item.getVersion());
                intent.putExtra("bundle", bundle);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        //请求当月数据
        mPresenter.getMonthList(MyApplication.getCurrentUserId(), setYear, setMonth);
        mPresenter.getMonthList1(MyApplication.getCurrentUserId());
        //初始化筛选时间
        time_year.setText(setYear + "年");
        time_month.setText(setMonth);
    }

    /*****************************************************************************/

    public void setMonthListListener(MonthListListener monthListListener) {
        this.monthListListener = monthListListener;
    }

    public interface MonthListListener {
        void OnDataChanged(String outcome, String income, String total, String time);

    }


    /*****************************************************************************/

    @Override
    protected MonthListContract.Presenter bindPresenter() {
        return new MonthListPresenter();
    }

    @Override
    public void loadDataSuccess(MonthListBean monthListBean) {
        list = monthListBean.getDaylist();
        adapter.setmDatas(list);
        adapter.notifyAllSectionsDataSetChanged();

        tOutcome.setText(monthListBean.getT_outcome());
        tIncome.setText(monthListBean.getT_income());
        tTotal.setText(monthListBean.getT_total());
    }

    @Override
    public void loadDataSuccess1(MonthListBean list) {
        tvall.setText(list.getT_income());
        tvallout.setText(list.getT_outcome());
        tvallin.setText(list.getT_total());
    }

    @Override
    public void onSuccess() {
        ProgressUtils.dismiss();
//        adapter.remove(part, index);
        //从列表中移除后需要重新计算当月总计
        mPresenter.getMonthList(MyApplication.getCurrentUserId(), setYear, setMonth);
    }

    @Override
    public void onFailure(Throwable e) {
        ProgressUtils.dismiss();
        SnackbarUtils.show(mActivity, e.getMessage());
    }

    @Override
    protected void beforeDestroy() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void setListener() {

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
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    mPresenter.getMonthList(MyApplication.getCurrentUserId(), setYear, setMonth);
                    break;
            }
        }
    }

    @Subscribe
    public void eventBusListener(String tag) {
        if (TextUtils.equals("1", tag)) {
            processLogic();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}