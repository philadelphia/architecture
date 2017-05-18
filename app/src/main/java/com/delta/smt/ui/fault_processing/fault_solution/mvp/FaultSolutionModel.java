package com.delta.smt.ui.fault_processing.fault_solution.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.BaseEntity;
import com.delta.smt.entity.FaultSolutionMessage;
import com.delta.smt.entity.ResultString;
import com.delta.smt.utils.StringUtils;

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
    public Observable<BaseEntity<String>> getDetailSolutionMessage(String ids) {


        return getService().getTemplateContent(ids).compose(RxsRxSchedulers.<BaseEntity<String>>io_main());
    }

    @Override
    public Observable<BaseEntity> resolveFault(String content) {
        return getService().resolveFault(content).compose(RxsRxSchedulers.<BaseEntity>io_main());
    }

}
