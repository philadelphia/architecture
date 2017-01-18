package com.delta.smt.ui.product_tools.location.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.JsonProductToolsLocation;

import rx.Observable;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public class ProduceToolsLocationModel extends BaseModel<ApiService> implements ProduceToolsLocationContract.Model{
    public ProduceToolsLocationModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<JsonProductToolsLocation> getLocationVerify(String param) {
        return getService().getgetLocationVerify(param).compose(RxsRxSchedulers.<JsonProductToolsLocation>io_main());
    }

    @Override
    public Observable<JsonProductToolsLocation> getLocationSubmit(String param) {
        return getService().getLocationSubmit(param).compose(RxsRxSchedulers.<JsonProductToolsLocation>io_main());
    }
}
