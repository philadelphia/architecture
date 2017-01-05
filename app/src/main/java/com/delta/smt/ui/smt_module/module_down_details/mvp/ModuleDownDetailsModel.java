package com.delta.smt.ui.smt_module.module_down_details.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ModuleDownDetailsItem;
import com.delta.smt.entity.ModuleDownWarningItem;
import com.delta.smt.ui.smt_module.module_down.mvp.ModuleDownContract;

import java.util.List;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleDownDetailsModel extends BaseModel<ApiService> implements ModuleDownDetailsContract.Model{
    public ModuleDownDetailsModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<ModuleDownDetailsItem>> getAllModuleDownDetailsItems() {
        return getService().getModuleDownDetailsItems().compose(RxsRxSchedulers.<List<ModuleDownDetailsItem>>io_main());
    }
}
