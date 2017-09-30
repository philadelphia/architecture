package com.delta.smt.ui.product_tools.tools_info.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.JsonProductRequestToolsRoot;

import rx.Observable;

/**
 * Created by Shaoqiang.Zhang on 2017/1/10.
 */

public class ProduceToolsInfoModel extends BaseModel<ApiService> implements ProduceToolsInfoContract.Model{

    public ProduceToolsInfoModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<JsonProductRequestToolsRoot> getProductToolsInfoItem(String parm) {
        //TODO ZSQgetProductToolsInfoItem
        return getService().getProductToolsInfoItem(parm).compose(RxsRxSchedulers.<JsonProductRequestToolsRoot>io_main());
    }

    @Override
    public Observable<JsonProductRequestToolsRoot> getProductToolsVerfy(String parm) {
        return getService().getProductToolsVerfy(parm).compose(RxsRxSchedulers.<JsonProductRequestToolsRoot>io_main());
    }

    @Override
    public Observable<JsonProductRequestToolsRoot> getProductToolsBorrowSubmit(String parm) {
        return getService().getProductToolsBorrowSubmit(parm).compose(RxsRxSchedulers.<JsonProductRequestToolsRoot>io_main());
    }
}
