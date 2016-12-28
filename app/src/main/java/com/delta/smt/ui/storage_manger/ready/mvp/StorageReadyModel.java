package com.delta.smt.ui.storage_manger.ready.mvp;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.StorageReady;

import java.util.List;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/22.
 */

public class StorageReadyModel extends BaseModel<ApiService> implements StorageReadyContract.Model{


    public StorageReadyModel(ApiService apiService) {
        super(apiService);
    }


    @Override
    public Observable<List<StorageReady>> getStorageReady() {
        return getService().getStorageReadyDates();
    }
}
