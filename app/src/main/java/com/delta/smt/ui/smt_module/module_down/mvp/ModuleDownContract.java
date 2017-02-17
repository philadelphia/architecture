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

        public void onSuccess(ModuleDownWarningItem data);

        public void onFalied();

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();

    }

    interface Model extends IModel {
        public Observable<ModuleDownWarningItem> getAllModuleDownWarningItems();
    }
}
