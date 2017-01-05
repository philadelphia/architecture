package com.delta.smt.ui.smt_module.virtual_line_binding.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ModuleUpBindingItem;
import com.delta.smt.entity.VirtualLineBindingItem;

import java.util.List;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class VirtualLineBindingModel extends BaseModel<ApiService> implements VirtualLineBindingContract.Model {
    public VirtualLineBindingModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<VirtualLineBindingItem>> getAllVirtualLineBindingItems() {
        return getService().getVirtualLineBindingItems().compose(RxsRxSchedulers.<List<VirtualLineBindingItem>>io_main());
    }
}
