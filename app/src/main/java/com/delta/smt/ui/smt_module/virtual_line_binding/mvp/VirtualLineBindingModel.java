package com.delta.smt.ui.smt_module.virtual_line_binding.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.VirtualLineItem;
import com.delta.smt.entity.VirtualModuleID;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class VirtualLineBindingModel extends BaseModel<ApiService> implements VirtualLineBindingContract.Model {
    public VirtualLineBindingModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<Result<VirtualLineItem>> getAllVirtualLineBindingItems(String str) {
        return getService().getVirtualLineBindingItems(str).compose(RxsRxSchedulers.<Result<VirtualLineItem>>io_main());
    }

    @Override
    public Observable<Result<VirtualLineItem>> getVirtualBinding(String str) {
        return getService().getVirtualBindingResult(str).compose(RxsRxSchedulers.<Result<VirtualLineItem>>io_main());
    }

    @Override
    public Observable<VirtualModuleID> getVirtualModuleID(String condition) {
        return getService().getVirtualModuleID(condition).compose(RxsRxSchedulers.<VirtualModuleID>io_main());
    }

    /*@Override
    public Observable<ModNumByMaterialResult> getModNumByMaterial(String str,String num) {
        return getService().getModNumByMaterial(str,num).compose(RxsRxSchedulers.<ModNumByMaterialResult>io_main());
    }*/
}
