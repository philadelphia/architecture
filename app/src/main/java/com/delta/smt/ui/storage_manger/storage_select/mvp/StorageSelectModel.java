package com.delta.smt.ui.storage_manger.storage_select.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.StorageSelect;

import java.util.List;

import rx.Observable;



public class StorageSelectModel extends BaseModel<ApiService> implements StorageSelectContract.Model {
    public StorageSelectModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<StorageSelect>> getStorageSelect() {
        return getService().getStorageSelect().compose(RxsRxSchedulers.<List<StorageSelect>>io_main());
    }
}
