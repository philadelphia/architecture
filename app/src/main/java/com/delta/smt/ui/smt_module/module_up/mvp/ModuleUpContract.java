package com.delta.smt.ui.smt_module.module_up.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.entity.ModuleUpWarningItem;

import java.util.List;

import dagger.Module;
import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public interface ModuleUpContract {
    interface View extends IView {

        public void onSuccess(ModuleUpWarningItem data);

        public void onFalied();

    }

    interface Model extends IModel {
        public Observable<ModuleUpWarningItem> getAllModuleUpWarningItems();
    }
}
