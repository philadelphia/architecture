package com.delta.smt.ui.mantissa_warehouse.detail.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.IssureToWarehFinishResult;
import com.delta.smt.entity.MantissaWarehouseDetailsResult;
import com.delta.smt.entity.MaterialCar;
import com.delta.smt.entity.Result;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

public class MantissaWarehouseDetailsModel extends BaseModel<ApiService> implements MantissaWarehouseDetailsContract.Model {


    public MantissaWarehouseDetailsModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<MantissaWarehouseDetailsResult> getMantissaWarehouseDetails(String str) {
        return getService().getMantissaWarehouseDetails(str).compose(RxsRxSchedulers.<MantissaWarehouseDetailsResult>io_main());
    }

    @Override
    public Observable<MaterialCar> getFindCar(String str) {
        return getService().getFindCar(str).compose(RxsRxSchedulers.<MaterialCar>io_main());
    }

    @Override
    public Observable<MaterialCar> getBingingCar(String str) {
        return getService().getbingingCar(str).compose(RxsRxSchedulers.<MaterialCar>io_main());
    }

    @Override
    public Observable<MantissaWarehouseDetailsResult> getMantissaWarehouseput(String str) {
        return getService().getMantissaWarehouseput(str).compose(RxsRxSchedulers.<MantissaWarehouseDetailsResult>io_main());
    }

    @Override
    public Observable<IssureToWarehFinishResult> getMantissaWareOver(String s) {
        return getService().getMantissaWareOver(s).compose(RxsRxSchedulers.<IssureToWarehFinishResult>io_main());
    }

    @Override
    public Observable<Result> debit() {
        return getService().debit().compose(RxsRxSchedulers.<Result>io_main());
    }

}
