package com.wzq.jz_app.presenter.contract;

import com.wzq.jz_app.base.BaseContract;
import com.wzq.jz_app.model.bean.local.BBill;
import com.wzq.jz_app.model.bean.local.NoteBean;

/**
 * 作者：wzq
 * 邮箱：wang_love152@163.com
 */
public interface BillContract extends BaseContract {

    interface View extends BaseView {

        void loadDataSuccess(NoteBean bean);

    }

    interface Presenter extends BasePresenter<View>{
        /**
         * 获取信息
         */
        void getBillNote();

        /**
         * 添加账单
         */
        void addBill(BBill bBill);

        /**
         * 修改账单
         */
        void updateBill(BBill bBill);


        /**
         * 删除账单
         */
        void deleteBillById(Long id);
    }
}
