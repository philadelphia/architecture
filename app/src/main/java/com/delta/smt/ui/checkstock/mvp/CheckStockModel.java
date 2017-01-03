package com.delta.smt.ui.checkstock.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.CheckStock;

import java.util.List;

import rx.Observable;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class CheckStockModel extends BaseModel<ApiService> implements CheckStockContract.Model{

    public CheckStockModel(ApiService service) {
        super(service);
    }

    @Override
    public Observable<List<CheckStock>> getCheckStock() {
        return getService().getCheckStock().compose(RxsRxSchedulers.<List<CheckStock>>io_main());
    }

    @Override
    public Observable<String> getCheckStockSuccess() {
        return getService().getCheckStockSuccess().compose(RxsRxSchedulers.<String>io_main());
    }
}
