package com.delta.smt.ui.smt_module.module_down.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ModuleDownWarningItem;
import com.delta.smt.entity.Result;

import rx.Observable;

/**
 * Author Shufeng.Wu
 * Date   2017/1/3
 */

public class ModuleDownModel extends BaseModel<ApiService> implements ModuleDownContract.Model{
    public ModuleDownModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<Result<ModuleDownWarningItem>> getModuleDownWarningList() {

        return getService().getModuleDownWarningList().compose(RxsRxSchedulers.<Result<ModuleDownWarningItem>>io_main());
    }
}
