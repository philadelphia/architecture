package com.delta.smt.ui.smt_module.module_up_binding.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.BaseEntity;
import com.delta.smt.entity.ModuleUpBindingItem;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.UpLoadEntity;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class ModuleUpBindingModel extends BaseModel<ApiService> implements ModuleUpBindingContract.Model {

    public ModuleUpBindingModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<Result<ModuleUpBindingItem>> getAllModuleUpBindingItems(String str) {

        return getService().getModuleUpBindingItems(str).compose(RxsRxSchedulers.<Result<ModuleUpBindingItem>>io_main());
    }

    @Override
    public Observable<Result<ModuleUpBindingItem>> getMaterialAndFeederBindingResult(String str) {
        return getService().getMaterialAndFeederBindingResult(str).compose(RxsRxSchedulers.<Result<ModuleUpBindingItem>>io_main());
    }

    @Override
    public Observable<Result> upLoadToMesManually(String value) {
        return getService().upLoadToMesManually(value).compose(RxsRxSchedulers.<Result>io_main());
    }

    @Override
    public Observable<BaseEntity<UpLoadEntity>> getneeduploadtomesmaterials(String mArgument) {
        return getService().getneeduploadtomesmaterials(mArgument).compose(RxsRxSchedulers.<BaseEntity<UpLoadEntity>>io_main());
    }
}
