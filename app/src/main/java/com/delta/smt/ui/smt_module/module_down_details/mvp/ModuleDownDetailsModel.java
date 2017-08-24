package com.delta.smt.ui.smt_module.module_down_details.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ModuleDownDebit;
import com.delta.smt.entity.ModuleDownDetailsItem;
import com.delta.smt.entity.Result;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleDownDetailsModel extends BaseModel<ApiService> implements ModuleDownDetailsContract.Model{
    public ModuleDownDetailsModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<Result<ModuleDownDetailsItem>> getAllModuleDownDetailsItems(String str) {
        return getService().getModuleDownDetailsItems(str).compose(RxsRxSchedulers.<Result<ModuleDownDetailsItem>>io_main());
    }

    @Override
    public Observable<Result> getModuleDownMaintainResult(String str) {
        return getService().getModuleDownMaintainResult(str).compose(RxsRxSchedulers.<Result>io_main());
    }

//    @Override
//    public Observable<ModuleDownDetailsItem> getDownModuleList(String condition) {
//        return getService().getDownModuleList(condition).compose(RxsRxSchedulers.<ModuleDownDetailsItem>io_main());
//    }
//
    @Override
    public Observable<Result<ModuleDownDetailsItem>> getFeederCheckInTime(String condition) {
        return getService().getFeederCheckInTime(condition).compose(RxsRxSchedulers.<Result<ModuleDownDetailsItem>>io_main());
    }

    @Override
    public Observable<Result<ModuleDownDebit>> getModuleListUnDebitList(String condition) {
        return getService().getModuleListUnDebitList(condition).compose(RxsRxSchedulers.<Result<ModuleDownDebit>>io_main());
    }

    @Override
    public Observable<Result<ModuleDownDebit>> debitManually(String value) {
        return getService().debitManually(value).compose(RxsRxSchedulers.<Result<ModuleDownDebit>>io_main());
    }

    @Override
    public Observable<Result> lightOff(String argument) {
        return getService().moduleDownlightOff(argument).compose(RxsRxSchedulers.<Result>io_main());
    }
}
