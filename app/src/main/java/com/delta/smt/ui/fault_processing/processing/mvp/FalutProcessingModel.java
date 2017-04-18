package com.delta.smt.ui.fault_processing.processing.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.FaultFilter;
import com.delta.smt.entity.FaultMessage;
import com.delta.smt.entity.SolutionMessage;

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
    public Observable<FaultMessage> getFalutMessages(String producelines) {


        return getService().getFalutMessages(producelines).compose(RxsRxSchedulers.<FaultMessage>io_main());
    }

    @Override
    public Observable<SolutionMessage> getSolutionMessage(String faultCode) {
        return getService().getSolutionMessage(faultCode).compose(RxsRxSchedulers.<SolutionMessage>io_main());
    }

    @Override
    public Observable<FaultFilter> getFaultFilterMessage() {
        return getService().getFaultFilterMessage().compose(RxsRxSchedulers.<FaultFilter>io_main());
    }
}
