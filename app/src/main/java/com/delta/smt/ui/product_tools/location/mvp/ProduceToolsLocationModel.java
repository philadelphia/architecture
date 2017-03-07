package com.delta.smt.ui.product_tools.location.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.JsonProductToolsLocationRoot;

import rx.Observable;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public class ProduceToolsLocationModel extends BaseModel<ApiService> implements ProduceToolsLocationContract.Model{
    public ProduceToolsLocationModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<JsonProductToolsLocationRoot> getLocationVerify(String param) {
        return getService().getLocationVerify(param).compose(RxsRxSchedulers.<JsonProductToolsLocationRoot>io_main());
    }

    @Override
    public Observable<JsonProductToolsLocationRoot> getLocationSubmit(String param) {
        return getService().getLocationSubmit(param).compose(RxsRxSchedulers.<JsonProductToolsLocationRoot>io_main());
    }
}
