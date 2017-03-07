package com.delta.smt.ui.product_tools.tools_info.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.JsonProductRequestToolsRoot;
import com.delta.smt.entity.JsonProductToolsLocationRoot;
import com.delta.smt.entity.JsonProductToolsVerfyRoot;

import rx.Observable;

/**
 * Created by Shaoqiang.Zhang on 2017/1/10.
 */

public class ProduceToolsInfoModel extends BaseModel<ApiService> implements ProduceToolsInfoContract.Model{

    public ProduceToolsInfoModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<JsonProductRequestToolsRoot> getProductToolsInfoItem(int pageSize, int pageCurrent,String condition) {
        //TODO ZSQgetProductToolsInfoItem
        return getService().getProductToolsInfoItem(condition).compose(RxsRxSchedulers.<JsonProductRequestToolsRoot>io_main());
    }

    @Override
    public Observable<JsonProductToolsVerfyRoot> getProductToolsVerfy(String param) {
        return getService().getProductToolsVerfy(param).compose(RxsRxSchedulers.<JsonProductToolsVerfyRoot>io_main());
    }

    @Override
    public Observable<JsonProductToolsLocationRoot> getProductToolsBorrowSubmit(String param) {
        return getService().getProductToolsBorrowSubmit(param).compose(RxsRxSchedulers.<JsonProductToolsLocationRoot>io_main());
    }
}
