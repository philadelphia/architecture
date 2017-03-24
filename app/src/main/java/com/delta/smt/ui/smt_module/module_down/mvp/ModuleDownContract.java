package com.delta.smt.ui.smt_module.module_down.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.ModuleDownWarningItem;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public interface ModuleDownContract {
    interface View extends IView {

        void onSuccess(ModuleDownWarningItem data);

        void onFailed(ModuleDownWarningItem data);

        void onNetFailed(Throwable throwable);

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();

    }

    interface Model extends IModel {
        Observable<ModuleDownWarningItem> getAllModuleDownWarningItems();
    }
}
