package com.wzq.jz_app.presenter;


import com.wzq.jz_app.base.BaseObserver;
import com.wzq.jz_app.base.RxPresenter;
import com.wzq.jz_app.model.bean.local.BBill;
import com.wzq.jz_app.model.repository.LocalRepository;
import com.wzq.jz_app.presenter.contract.MonthListContract;
import com.wzq.jz_app.utils.BillUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 作者：wzq
 * 邮箱：wang_love152@163.com
 */
public class MonthListPresenter extends RxPresenter<MonthListContract.View> implements MonthListContract.Presenter{

    private String TAG="MonthListPresenter";

    @Override
    public void getMonthList(String id, String year, String month) {
        LocalRepository.getInstance().getBBillByUserIdWithYM(id, year, month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<BBill>>() {
                    @Override
                    protected void onSuccees(List<BBill> bBills) throws Exception {
                        mView.loadDataSuccess(BillUtils.packageDetailList(bBills));}
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mView.onFailure(e);
                    }});}

    @Override
    public void getMonthList1(String id) {
        LocalRepository.getInstance().getBBillByUserIdWith(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<BBill>>() {
                    @Override
                    protected void onSuccees(List<BBill> bBills) throws Exception {
                        mView.loadDataSuccess1(BillUtils.packageDetailList(bBills));}
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mView.onFailure(e);
                    }});
    }

    @Override
    public void deleteBill(Long id) {
        LocalRepository.getInstance().deleteBBillById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Long>() {
                    @Override
                    protected void onSuccees(Long l) throws Exception {
                        mView.onSuccess();
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mView.onFailure(e);}});}
    @Override
    public void updateBill(BBill bBill) {
        LocalRepository.getInstance()
                .updateBBill(bBill)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BBill>() {
                    @Override
                    protected void onSuccees(BBill bBill) throws Exception {
                        mView.onSuccess();}
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mView.onFailure(e);}});
    }
}
