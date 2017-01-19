package com.delta.smt.ui.mantissa_warehouse.detail.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.MantissaCar;
import com.delta.smt.entity.MantissaWarehouseDetailsResult;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

public class MantissaWarehouseDetailsModel extends BaseModel<ApiService> implements MantissaWarehouseDetailsContract.Model{


    public MantissaWarehouseDetailsModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<MantissaWarehouseDetailsResult> getMantissaWarehouseDetails(String str) {
        return getService().getMantissaWarehouseDetails(str).compose(RxsRxSchedulers.<MantissaWarehouseDetailsResult>io_main());
    }

    @Override
    public Observable<MantissaCar> getFindCar(String str) {
        return getService().getFindCar(str).compose(RxsRxSchedulers.<MantissaCar>io_main());
    }

    @Override
    public Observable<MantissaCar> getBingingCar(String str) {
        return getService().getbingingCar(str).compose(RxsRxSchedulers.<MantissaCar>io_main());
    }

    @Override
    public Observable<MantissaWarehouseDetailsResult> getMantissaWarehouseput(String str) {
        return getService().getMantissaWarehouseput(str).compose(RxsRxSchedulers.<MantissaWarehouseDetailsResult>io_main());
    }

}
