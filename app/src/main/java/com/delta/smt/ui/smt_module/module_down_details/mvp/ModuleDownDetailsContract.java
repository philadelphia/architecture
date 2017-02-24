package com.delta.smt.ui.smt_module.module_down_details.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.ModuleDownDetailsItem;
import com.delta.smt.entity.ModuleDownMaintain;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/5.
 */

public interface ModuleDownDetailsContract {
    interface View extends IView {

        void onSuccess(ModuleDownDetailsItem data);

        void onFailed(String message);

        void onSuccessMaintain(ModuleDownMaintain moduleDownMaintain);

        void onFailMaintain(ModuleDownMaintain moduleDownMaintain);

        void onNetFailed(Throwable throwable);

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();

    }

    interface Model extends IModel {
         Observable<ModuleDownDetailsItem> getAllModuleDownDetailsItems(String str);
         Observable<ModuleDownMaintain> getModuleDownMaintainResult(String str);
        Observable<ModuleDownDetailsItem> getDownModuleList(String condition);
        Observable<ModuleDownDetailsItem> getFeederCheckInTime(String condition);

    }
}
