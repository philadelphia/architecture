package com.delta.smt.ui.smt_module.module_up.mvp;

import android.content.Context;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.entity.ModuleUpWarningItem;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleUpModel extends BaseModel<ApiService> implements ModuleUpContract.Model{
    public ModuleUpModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<ModuleUpWarningItem> getAllModuleUpWarningItems(String str) {

        return getService().getModuleUpWarningItems(str).compose(RxsRxSchedulers.<ModuleUpWarningItem>io_main());
    }
}
