package com.delta.smt.ui.production_scan.binding.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.Result;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */

public class BindingModel extends BaseModel<ApiService> implements BindingContract.Model {

    public BindingModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<Result> uploadToMesFromProcessline(String value) {
        return getService().uploadToMesFromProcessline(value).compose(RxsRxSchedulers.<Result>io_main());
    }
}
