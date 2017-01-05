package com.delta.smt.ui.smt_module.module_down.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ModuleDownWarningItem;
import com.delta.smt.entity.ModuleUpWarningItem;
import com.delta.smt.ui.smt_module.module_up.mvp.ModuleUpContract;

import java.util.List;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleDownModel extends BaseModel<ApiService> implements ModuleDownContract.Model{
    public ModuleDownModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<ModuleDownWarningItem>> getAllModuleDownWarningItems() {
        return getService().getModuleDownWarningItems().compose(RxsRxSchedulers.<List<ModuleDownWarningItem>>io_main());
    }
}
