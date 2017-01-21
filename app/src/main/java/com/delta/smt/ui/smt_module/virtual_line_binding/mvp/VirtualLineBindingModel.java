package com.delta.smt.ui.smt_module.virtual_line_binding.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ModNumByMaterialResult;
import com.delta.smt.entity.VirtualBindingResult;
import com.delta.smt.entity.VirtualLineBindingItem;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class VirtualLineBindingModel extends BaseModel<ApiService> implements VirtualLineBindingContract.Model {
    public VirtualLineBindingModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<VirtualLineBindingItem> getAllVirtualLineBindingItems(String str) {
        return getService().getVirtualLineBindingItems(str).compose(RxsRxSchedulers.<VirtualLineBindingItem>io_main());
    }

    @Override
    public Observable<VirtualBindingResult> getVirtualBinding(String id, String virtualId) {
        return getService().getVirtualBindingResult(id,virtualId).compose(RxsRxSchedulers.<VirtualBindingResult>io_main());
    }

    @Override
    public Observable<ModNumByMaterialResult> getModNumByMaterial(String str,String num) {
        return getService().getModNumByMaterial(str,num).compose(RxsRxSchedulers.<ModNumByMaterialResult>io_main());
    }
}
