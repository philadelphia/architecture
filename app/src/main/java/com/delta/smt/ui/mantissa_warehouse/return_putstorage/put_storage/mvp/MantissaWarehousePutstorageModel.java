package com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.MantissaWarehousePutstorageResult;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/30.
 */

public class MantissaWarehousePutstorageModel extends BaseModel<ApiService> implements MantissaWarehousePutstorageContract.Model{


    public MantissaWarehousePutstorageModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<MantissaWarehousePutstorageResult> getMantissaWarehousePutstorage() {
        return getService().getMantissaWarehousePutstorage().compose(RxsRxSchedulers.<MantissaWarehousePutstorageResult>io_main());
    }

    @Override
    public Observable<MantissaWarehousePutstorageResult> getMantissaWarehousePutstorageUpdate() {
        return getService().getMantissaWarehousePutstorageUpdate().compose(RxsRxSchedulers.<MantissaWarehousePutstorageResult>io_main());
    }

    @Override
    public Observable<MantissaWarehousePutstorageResult> getbeginput() {
        return getService().getbeginPut().compose(RxsRxSchedulers.<MantissaWarehousePutstorageResult>io_main());
    }

    @Override
    public Observable<MantissaWarehousePutstorageResult> getBingingLable(String str) {
        return getService().getBingingLable(str).compose(RxsRxSchedulers.<MantissaWarehousePutstorageResult>io_main());
    }

    @Override
    public Observable<MantissaWarehousePutstorageResult> getUpLocation(String str) {
        return getService().getUpLocation(str).compose(RxsRxSchedulers.<MantissaWarehousePutstorageResult>io_main());
    }

    @Override
    public Observable<MantissaWarehousePutstorageResult> getYesNext() {
        return getService().getYesNext().compose(RxsRxSchedulers.<MantissaWarehousePutstorageResult>io_main());
    }

    @Override
    public Observable<MantissaWarehousePutstorageResult> getYesok() {
        return getService().getYesok().compose(RxsRxSchedulers.<MantissaWarehousePutstorageResult>io_main());
    }

}
