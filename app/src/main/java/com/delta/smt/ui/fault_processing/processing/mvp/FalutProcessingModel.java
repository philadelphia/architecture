package com.delta.smt.ui.fault_processing.processing.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.FalutMesage;

import java.util.List;

import rx.Observable;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:37
 */


public class FalutProcessingModel extends BaseModel<ApiService> implements FalutProcessingContract.Model {
    public FalutProcessingModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<FalutMesage>> getFalutMessages() {
        return getService().getFalutMessages().compose(RxsRxSchedulers.<List<FalutMesage>>io_main());
    }
}
