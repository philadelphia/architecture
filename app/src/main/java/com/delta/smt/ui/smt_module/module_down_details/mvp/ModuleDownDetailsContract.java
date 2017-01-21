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

        public void onSuccess(ModuleDownDetailsItem data);

        public void onFalied();

        public void onSuccessMaintain(ModuleDownMaintain maintain);

        public void onFailMaintain();

    }

    interface Model extends IModel {
        public Observable<ModuleDownDetailsItem> getAllModuleDownDetailsItems(String str);

        public Observable<ModuleDownMaintain> getModuleDownMaintainResult(String str);
    }
}
