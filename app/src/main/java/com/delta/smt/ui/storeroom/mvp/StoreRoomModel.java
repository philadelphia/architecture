package com.delta.smt.ui.storeroom.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.Light;
import com.delta.smt.entity.Success;

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

    @Override
    public Observable<Light> OnLight(String s) {
        return getService().onLight(s).compose(RxsRxSchedulers.<Light>io_main());
    }

    @Override
    public Observable<Success> PutInStorage(String s) {
        return getService().putInStorage(s).compose(RxsRxSchedulers.<Success>io_main());
    }
}
