package com.delta.smt.ui.smt_module.module_down_details.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ModuleDownDetailsItem;
import com.delta.smt.entity.ModuleDownMaintain;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleDownDetailsModel extends BaseModel<ApiService> implements ModuleDownDetailsContract.Model{
    public ModuleDownDetailsModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<ModuleDownDetailsItem> getAllModuleDownDetailsItems(String str) {
        return getService().getModuleDownDetailsItems(str).compose(RxsRxSchedulers.<ModuleDownDetailsItem>io_main());
    }

    @Override
    public Observable<ModuleDownMaintain> getModuleDownMaintainResult(String str) {
        return getService().getModuleDownMaintainResult(str).compose(RxsRxSchedulers.<ModuleDownMaintain>io_main());
    }

    @Override
    public Observable<ModuleDownDetailsItem> getDownModuleList(String condition) {
        return getService().getDownModuleList(condition).compose(RxsRxSchedulers.<ModuleDownDetailsItem>io_main());
    }

    @Override
    public Observable<ModuleDownDetailsItem> getFeederCheckInTime(String condition) {
        return getService().getFeederCheckInTime(condition).compose(RxsRxSchedulers.<ModuleDownDetailsItem>io_main());
    }
}
