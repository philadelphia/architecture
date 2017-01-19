package com.delta.smt.ui.fault_processing.fault_add.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.BaseEntity;

import rx.Observable;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:37
 */


public class FaultProcessingAddModel extends BaseModel<ApiService> implements FaultProcessingAddContract.Model {

    public FaultProcessingAddModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<BaseEntity> addSolution(String ids) {


        return getService().addSolution(ids).compose(RxsRxSchedulers.<BaseEntity>io_main());
    }

}
