package com.delta.smt.ui.maintain.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.LedLight;
import com.delta.smt.entity.Success;

import rx.Observable;

/**
 * Created by Lin.Hou on 2017-03-13.
 */

public class PCBMaintainModel extends BaseModel<ApiService> implements PCBMaintainContract.Model {
    public PCBMaintainModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<LedLight> getSubshelf(String s) {
        return getService().getSubshelf(s).compose(RxsRxSchedulers.<LedLight>io_main());
    }

    @Override
    public Observable<Success> getUpdate(String id, String lightSerial) {
        return getService().getUpdate(id,lightSerial).compose(RxsRxSchedulers.<Success>io_main());
    }

    @Override
    public Observable<Success> getUnbound(String id) {
        return getService().getUnbound(id).compose(RxsRxSchedulers.<Success>io_main());
    }
}
