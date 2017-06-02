package com.delta.smt.ui.checkstock.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.CheckStock;
import com.delta.smt.entity.ExceptionsBean;
import com.delta.smt.entity.Success;

import rx.Observable;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class CheckStockModel extends BaseModel<ApiService> implements CheckStockContract.Model{

    public CheckStockModel(ApiService service) {
        super(service);
    }

    @Override
    public Observable<CheckStock> getCheckStock(String s) {
        return getService().getCheckStock(s).compose(RxsRxSchedulers.<CheckStock>io_main());
    }

    @Override
    public Observable<Success> getCheckStockNumber(String id) {
        return getService().getCheckNumber(id).compose(RxsRxSchedulers.<Success>io_main());
    }

    @Override
    public Observable<Success> getError(String boxSerial) {
        return getService().getError(boxSerial).compose(RxsRxSchedulers.<Success>io_main());
    }

    @Override
    public Observable<ExceptionsBean> getException(String subShelfCode) {
        return getService().getException(subShelfCode).compose(RxsRxSchedulers.<ExceptionsBean>io_main());
    }

    @Override
    public Observable<Success> getSubmit(String subShelfCode) {
        return getService().getSubmit(subShelfCode).compose(RxsRxSchedulers.<Success>io_main());
    }



    @Override
    public Observable<String> getCheckStockSuccess() {
        return getService().getCheckStockSuccess().compose(RxsRxSchedulers.<String>io_main());
    }

    @Override
    public Observable<Success> getJudgeSuceess(String s) {
        return getService().getJudgeSuccsee(s).compose(RxsRxSchedulers.<Success>io_main());
    }
}
