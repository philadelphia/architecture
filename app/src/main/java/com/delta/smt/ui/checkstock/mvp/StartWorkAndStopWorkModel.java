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

public class StartWorkAndStopWorkModel extends BaseModel<ApiService> implements StartWorkAndStopWorkContract.Model{

    public StartWorkAndStopWorkModel(ApiService service) {
        super(service);
    }



}
