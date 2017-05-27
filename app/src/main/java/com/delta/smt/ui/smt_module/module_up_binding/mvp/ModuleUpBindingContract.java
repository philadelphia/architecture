package com.delta.smt.ui.smt_module.module_up_binding.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.ModuleUpBindingItem;
import com.delta.smt.entity.Result;

import java.util.List;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public interface ModuleUpBindingContract {
    interface View extends IView {

        void onSuccess(List<ModuleUpBindingItem> dataSource);

        void onFailed(String  message);

        void onNetFailed(Throwable throwable);

        void onSuccessBinding(List<ModuleUpBindingItem> dataSource) ;

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();

    }

    interface Model extends IModel {
        Observable<Result<ModuleUpBindingItem>> getAllModuleUpBindingItems(String str);

        Observable<Result<ModuleUpBindingItem>> getMaterialAndFeederBindingResult(String str);
    }
}
