package com.wzq.jz_app.presenter;

import com.wzq.jz_app.base.RxPresenter;
import com.wzq.jz_app.model.bean.local.BSort;
import com.wzq.jz_app.model.repository.LocalRepository;
import com.wzq.jz_app.presenter.contract.BillNoteContract;

import java.util.List;


/**
 *作者：wzq
 * 邮箱：wang_love152@163.com
 */
public class BillNotePresenter extends RxPresenter<BillNoteContract.View> implements BillNoteContract.Presenter {

    private String TAG = "BillNotePresenter";

    @Override
    public void getBillNote() {
        //此处采用同步的方式，防止账单分类出现白块
        mView.loadDataSuccess(LocalRepository.getInstance().getBillNote());
    }

    @Override
    public void updateBBsorts(List<BSort> items) {
        LocalRepository.getInstance().updateBSoers(items);
        mView.onSuccess();
    }

    @Override
    public void addBSort(BSort bSort) {
        LocalRepository.getInstance().saveBSort(bSort);
        mView.onSuccess();
    }

    @Override
    public void deleteBSortByID(Long id) {
        LocalRepository.getInstance().deleteBSortById(id);
        mView.onSuccess();
    }
}
