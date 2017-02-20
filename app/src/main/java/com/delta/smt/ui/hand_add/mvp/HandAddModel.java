package com.delta.smt.ui.hand_add.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.Result;
import com.delta.smt.ui.hand_add.item.ItemHandAdd;

import rx.Observable;

/**
 * Created by Fuxiang.Zhang on 2016/12/27.
 */

public class HandAddModel extends BaseModel<ApiService> implements HandAddContract.Model {

    public HandAddModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<Result<ItemHandAdd>> getItemHandAddDatas(String condition) {

        return getService().getItemHandAddDatas(condition).compose(RxsRxSchedulers.<Result<ItemHandAdd>>io_main());
    }

    @Override
    public Observable<Result> getItemHandAddConfirm(String condition) {
        return getService().getItemHandAddConfirm(condition).compose(RxsRxSchedulers.<Result>io_main());
    }
}
