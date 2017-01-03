package com.delta.smt.ui.storeroom.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;

import rx.Observable;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class StoreRoomModel extends BaseModel<ApiService> implements StoreRoomContract.Model{

    public StoreRoomModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<String> getStoreRoomSuccess() {
        return getService().getStoreRoomSuccess().compose(RxsRxSchedulers.<String>io_main());
    }
}
