package com.wzq.jz_app.presenter;

import android.text.TextUtils;

import com.wzq.jz_app.base.BaseObserver;
import com.wzq.jz_app.base.RxPresenter;
import com.wzq.jz_app.model.bean.local.BBill;
import com.wzq.jz_app.model.repository.LocalRepository;
import com.wzq.jz_app.presenter.contract.MonthChartContract;
import com.wzq.jz_app.utils.BillUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：wzq
 * 邮箱：wang_love152@163.com
 * */
public class MonthChartPresenter extends RxPresenter<MonthChartContract.View> implements MonthChartContract.Presenter{

    private String TAG="MonthChartPresenter";

    @Override
    public void getMonthChart(String id, String year, String month,String type) {
        if("1".equals(type)) {
            if(TextUtils.isEmpty(year)||TextUtils.isEmpty(month)){
                LocalRepository.getInstance().getBBillByUserIdWith(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<List<BBill>>() {
                            @Override
                            protected void onSuccees(List<BBill> bBills) throws Exception {
                                mView.loadDataSuccess(BillUtils.packageChartList(bBills));
                            }

                            @Override
                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                                mView.onFailure(e);
                            }
                        });
            }else {
                LocalRepository.getInstance().getBBillByUserIdWithYM(id, year, month)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<List<BBill>>() {
                            @Override
                            protected void onSuccees(List<BBill> bBills) throws Exception {
                                mView.loadDataSuccess(BillUtils.packageChartList(bBills));
                            }

                            @Override
                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                                mView.onFailure(e);
                            }
                        });
            }
        }else {
            LocalRepository.getInstance().getBBillByUserIdWithDay(id, year, month)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<List<BBill>>() {
                        @Override
                        protected void onSuccees(List<BBill> bBills) throws Exception {
                            mView.loadDataSuccess(BillUtils.packageChartList(bBills));
                        }

                        @Override
                        protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                            mView.onFailure(e);
                        }
                    });
        }
    }


//
//    @Override
//    public void getMonthChart1(String id, String start, String end) {
//        LocalRepository.getInstance().getBBillByUserIdWithDay(id,start,end)
//                .subscribeOn(Schedulers.)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<BBill>>() {
//                    @Override
//                    protected void onSuccees(List<BBill> bBills) throws Exception {
//                        mView.loadDataSuccess(BillUtils.packageChartList(bBills));
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                        mView.onFailure(e);
//                    }
//                });
//    }
}





