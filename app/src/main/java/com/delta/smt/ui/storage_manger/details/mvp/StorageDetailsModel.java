package com.delta.smt.ui.storage_manger.details.mvp;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.StorageDetails;

import java.util.List;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */

public class StorageDetailsModel extends BaseModel<ApiService> implements StorageDetailsContract.Model {

    public StorageDetailsModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<StorageDetails>> getStorageDetails() {
        return null;
    }
}
