package com.delta.smt.ui.smt_module.module_up_binding.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.MaterialAndFeederBindingResult;
import com.delta.smt.entity.ModuleUpBindingItem;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public interface ModuleUpBindingContract {
    interface View extends IView {

        public void onSuccess(ModuleUpBindingItem data);

        public void onFalied();

        public void onSuccessBinding(ModuleUpBindingItem data);

        public void onFailedBinding();

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();

    }

    interface Model extends IModel {
        public Observable<ModuleUpBindingItem> getAllModuleUpBindingItems(String str);

        public Observable<ModuleUpBindingItem> getMaterialAndFeederBindingResult(String str);
    }
}
