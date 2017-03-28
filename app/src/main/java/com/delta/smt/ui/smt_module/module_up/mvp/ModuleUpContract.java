package com.delta.smt.ui.smt_module.module_up.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.ModuleUpWarningItem;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public interface ModuleUpContract {
    interface View extends IView {

        void onSuccess(ModuleUpWarningItem data);

        void onFailed(ModuleUpWarningItem data);

        void onNetFailed(Throwable throwable);

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();

    }

    interface Model extends IModel {
        Observable<ModuleUpWarningItem> getAllModuleUpWarningItems();
    }
}
