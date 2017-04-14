package com.delta.smt.ui.checkstock.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.InventoryExecption;
import com.delta.smt.entity.OnGoing;
import com.delta.smt.entity.Success;

import rx.Observable;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class StartWorkAndStopWorkModel extends BaseModel<ApiService> implements StartWorkAndStopWorkContract.Model{

    public StartWorkAndStopWorkModel(ApiService service) {
        super(service);
    }


    @Override
    public Observable<Success> startWork() {
        return getService().onStartWork().compose(RxsRxSchedulers.<Success>io_main());
    }

    @Override
    public Observable<OnGoing> ongoing() {
        return getService().onGoing().compose(RxsRxSchedulers.<OnGoing>io_main());
    }

    @Override
    public Observable<InventoryExecption> getInventoryException() {
        return getService().getInventoryException().compose(RxsRxSchedulers.<InventoryExecption>io_main());
    }

    @Override
    public Observable<Success> OnEnd() {
        return getService().onEnd().compose(RxsRxSchedulers.<Success>io_main());
    }
}
