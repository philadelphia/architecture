package com.delta.smt.ui.smt_module.module_up_binding.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ModuleUpBindingItem;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class ModuleUpBindingModel extends BaseModel<ApiService> implements ModuleUpBindingContract.Model{

    public ModuleUpBindingModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<ModuleUpBindingItem> getAllModuleUpBindingItems(String str) {

        return getService().getModuleUpBindingItems(str).compose(RxsRxSchedulers.<ModuleUpBindingItem>io_main());
    }

    @Override
    public Observable<ModuleUpBindingItem> getMaterialAndFeederBindingResult(String str) {
        return getService().getMaterialAndFeederBindingResult(str).compose(RxsRxSchedulers.<ModuleUpBindingItem>io_main());
    }
}
