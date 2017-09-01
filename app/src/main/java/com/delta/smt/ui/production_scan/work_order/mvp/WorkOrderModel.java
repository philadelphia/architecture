package com.delta.smt.ui.production_scan.work_order.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.production_scan.ProduceWarning;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class WorkOrderModel extends BaseModel<ApiService> implements WorkOrderContract.Model {
    public WorkOrderModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<ProduceWarning> getItemWarningDatas(String condition) {
        return getService().getWorkOrderDatas(condition).compose(RxsRxSchedulers.<ProduceWarning>io_main());
    }
}
