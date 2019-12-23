package com.wzq.jz_app.presenter.contract;

import com.wzq.jz_app.base.BaseContract;
import com.wzq.jz_app.model.bean.local.MonthChartBean;

/**
 * 作者：wzq
 * 邮箱：wang_love152@163.com
 */
public interface MonthChartContract extends BaseContract {

    interface View extends BaseView {

        void loadDataSuccess(MonthChartBean bean);

    }

    interface Presenter extends BasePresenter<View> {

        void getMonthChart(String id, String year, String month, String type);


    }


}
