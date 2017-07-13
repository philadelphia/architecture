package com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.put_storage.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.MantissaWarehousePutstorageBindTagResult;
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
    public Observable<MantissaWarehousePutstorageBindTagResult> getbeginput(String str) {
        return getService().getbeginPut(str).compose(RxsRxSchedulers.<MantissaWarehousePutstorageBindTagResult>io_main());
    }

    @Override
    public Observable<MantissaWarehousePutstorageBindTagResult> bindMaterialCar(String str) {
        return getService().bindMantissaWarehouseCar(str).compose(RxsRxSchedulers.<MantissaWarehousePutstorageBindTagResult>io_main());
    }

    @Override
    public Observable<MantissaWarehousePutstorageBindTagResult> getBingingLable(String str) {
        return getService().getBingingLable(str).compose(RxsRxSchedulers.<MantissaWarehousePutstorageBindTagResult>io_main());
    }

    @Override
    public Observable<MantissaWarehousePutstorageBindTagResult> getUpLocation(String str) {
        return getService().getUpLocation(str).compose(RxsRxSchedulers.<MantissaWarehousePutstorageBindTagResult>io_main());
    }

    @Override
    public Observable<MantissaWarehousePutstorageBindTagResult> onlickSubmit() {
        return getService().onlickSubmit().compose(RxsRxSchedulers.<MantissaWarehousePutstorageBindTagResult>io_main());
    }

    @Override
    public Observable<MantissaWarehousePutstorageResult> getYesNext() {
        return getService().getYesNext().compose(RxsRxSchedulers.<MantissaWarehousePutstorageResult>io_main());
    }

    @Override
    public Observable<MantissaWarehousePutstorageResult> getYesok() {
        return getService().getYesok().compose(RxsRxSchedulers.<MantissaWarehousePutstorageResult>io_main());
    }

    @Override
    public Observable<MantissaWarehousePutstorageResult> onclickBeginButton() {
        return getService().getOnclickBeginButton().compose(RxsRxSchedulers.<MantissaWarehousePutstorageResult>io_main());
    }

}
