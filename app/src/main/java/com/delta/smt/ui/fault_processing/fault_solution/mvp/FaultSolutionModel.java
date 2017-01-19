package com.delta.smt.ui.fault_processing.fault_solution.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.BaseEntity;
import com.delta.smt.entity.FaultSolutionMessage;

import rx.Observable;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:37
 */


public class FaultSolutionModel extends BaseModel<ApiService> implements FaultSolutionContract.Model {

    public FaultSolutionModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<FaultSolutionMessage> getDetailSolutionMessage(String ids) {


        return getService().getDetailSolutionMessage(ids).compose(RxsRxSchedulers.<FaultSolutionMessage>io_main());
    }

    @Override
    public Observable<BaseEntity> resolveFault(String content) {
        return getService().resolveFault(content).compose(RxsRxSchedulers.<BaseEntity>io_main());
    }

}
