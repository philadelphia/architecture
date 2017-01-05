package com.delta.smt.ui.smt_module.module_down_details.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.ModuleDownDetailsItem;
import com.delta.smt.entity.ModuleDownWarningItem;

import java.util.List;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/5.
 */

public interface ModuleDownDetailsContract {
    interface View extends IView {

        public void onSuccess(List<ModuleDownDetailsItem> data);

        public void onFalied();

    }

    interface Model extends IModel {
        public Observable<List<ModuleDownDetailsItem>> getAllModuleDownDetailsItems();
    }
}
