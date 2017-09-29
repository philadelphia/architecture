package com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.schedule.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ScheduleResult;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2017/9/27.
 */

public class ScheduleModel extends BaseModel<ApiService> implements ScheduleContract.Model {
    public ScheduleModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<ScheduleResult> getSchedule() {

        return getService().getSchedule().compose(RxsRxSchedulers.<ScheduleResult>io_main());

    }
}
