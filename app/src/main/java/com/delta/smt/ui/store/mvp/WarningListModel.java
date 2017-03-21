package com.delta.smt.ui.store.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.OutBound;
import com.delta.smt.entity.PcbNumber;
import com.delta.smt.entity.Success;

import rx.Observable;

/**
 * Created by Lin.Hou on 2016-12-27.
 */

public class WarningListModel extends BaseModel<ApiService> implements WarningListContract.Model {

    public WarningListModel(ApiService service) {
        super(service);
    }


    @Override
    public Observable<OutBound> getOutbound(int id,String sapWorkOrderId,String partNum,int amount) {
        return getService().outBound(id,sapWorkOrderId,partNum,amount).compose(RxsRxSchedulers.<OutBound>io_main());
    }

    @Override
    public Observable<PcbNumber> getPcbNumber(String s) {
        return getService().getPcbNumber(s).compose(RxsRxSchedulers.<PcbNumber>io_main());
    }

    @Override
    public Observable<Success> getPcbSuccess(String s) {
        return getService().getPcbSuccess(s).compose(RxsRxSchedulers.<Success>io_main());
    }
    @Override
    public Observable<OutBound> getScheduleDetailed(int id,String sapWorkOrderId, String partNum, int amount) {
        return getService().getScheduleDetailed(id,sapWorkOrderId,partNum,amount).compose(RxsRxSchedulers.<OutBound>io_main());
    }

    @Override
    public Observable<Success> getAlarmSuccessfulState(String sapWorkOrderId, int alarmId) {
       return getService().getAlarmSuccessState(sapWorkOrderId,alarmId).compose(RxsRxSchedulers.<Success>io_main());
    }

    @Override
    public Observable<Success> getScheduleSuccessState(int scheduleId) {
        return getService().getScheduleSuccessState(scheduleId).compose(RxsRxSchedulers.<Success>io_main());
    }

    @Override
    public Observable<Success> Closelighting(String subShelfCode) {
        return getService().closeLight(subShelfCode).compose(RxsRxSchedulers.<Success>io_main());
    }

    @Override
    public Observable<Success> getOutSubmit(int scheduleId) {
        return getService().getOutSubmit(scheduleId).compose(RxsRxSchedulers.<Success>io_main());
    }

    @Override
    public Observable<Success> getAlarmOutSubmit(int scheduleId) {
        return getService().getAlarmOutSubmit(scheduleId).compose(RxsRxSchedulers.<Success>io_main());
    }

    @Override
    public Observable<OutBound> getRefresh(int id, String partNum, int offset, int type) {
        return getService().getRefresh(id,partNum,offset,type).compose(RxsRxSchedulers.<OutBound>io_main());
    }
}
