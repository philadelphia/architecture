package com.delta.smt.ui.smt_module.module_down_details.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.DebitData;
import com.delta.smt.entity.ModuleDownDebit;
import com.delta.smt.entity.ModuleDownDetailsItem;
import com.delta.smt.entity.Result;

import java.util.List;

import rx.Observable;

/**
 * Author Shufeng.Wu
 * Date   2017/1/5
 */

public interface ModuleDownDetailsContract {
    interface View extends IView {

        void onGetModuleDownItemListSuccess(List<ModuleDownDetailsItem> data);

        void onGetModuleDownItemListFailed(String message);

        void showModuleDownUnDebitedItemList(List<ModuleDownDebit> data);

        void onResult(String message);

        void onMaintainResult(String message);


        void onNetFailed(Throwable throwable);

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();

        void onEmptyResult();

    }

    interface Model extends IModel {
         Observable<Result<ModuleDownDetailsItem>> getModuleDownItemList(String str);
         Observable<Result> getModuleDownMaintainResult(String str);
//        Observable<Result<ModuleDownDetailsItem>> getDownModuleList(String condition);
        Observable<Result<ModuleDownDetailsItem>> getFeederCheckInTime(String condition);
        Observable<Result<ModuleDownDebit>> getModuleListUnDebitList(String condition);
        Observable<Result<ModuleDownDebit>> debitManually(String value);

        Observable<Result> lightOff(String argument);
    }
}
