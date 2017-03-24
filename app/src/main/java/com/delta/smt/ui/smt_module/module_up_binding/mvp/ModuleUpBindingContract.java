package com.delta.smt.ui.smt_module.module_up_binding.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.ModuleUpBindingItem;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public interface ModuleUpBindingContract {
    interface View extends IView {

        void onSuccess(ModuleUpBindingItem data);

        void onFailed(ModuleUpBindingItem data);

        void onNetFailed(Throwable throwable);

        void onSuccessBinding(ModuleUpBindingItem data);

        void onFailedBinding(ModuleUpBindingItem data);

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();

    }

    interface Model extends IModel {
        Observable<ModuleUpBindingItem> getAllModuleUpBindingItems(String str);

        Observable<ModuleUpBindingItem> getMaterialAndFeederBindingResult(String str);
    }
}
