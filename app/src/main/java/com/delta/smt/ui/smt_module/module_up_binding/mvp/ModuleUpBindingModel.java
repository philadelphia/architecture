package com.delta.smt.ui.smt_module.module_up_binding.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ModuleUpBindingItem;
import com.delta.smt.entity.ModuleUpWarningItem;

import java.util.List;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class ModuleUpBindingModel extends BaseModel<ApiService> implements ModuleUpBindingContract.Model{

    public ModuleUpBindingModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<ModuleUpBindingItem>> getAllModuleUpBindingItems() {
        return getService().getModuleUpBindingItems().compose(RxsRxSchedulers.<List<ModuleUpBindingItem>>io_main());
    }
}
