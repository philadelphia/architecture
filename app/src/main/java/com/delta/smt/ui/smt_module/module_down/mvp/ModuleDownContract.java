package com.delta.smt.ui.smt_module.module_down.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.ModuleDownWarningItem;
import com.delta.smt.entity.Result;

import java.util.List;

import rx.Observable;

/**
 * Author Shufeng.Wu
 * Date   2017/1/3
 */

public interface ModuleDownContract {
    interface View extends IView {

        void onGetModuleDownWarningListSuccess(List<ModuleDownWarningItem> data);

        void onGetModuleDownWarningFailed(String message);

        void onNetFailed(Throwable throwable);

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();

    }

    interface Model extends IModel {
        Observable<Result<ModuleDownWarningItem>> getModuleDownWarningList();
    }
}
