package com.delta.smt.ui.product_tools.tools_info.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.JsonProductBorrowRoot;
import com.delta.smt.entity.JsonProductRequestToolsRoot;
import com.delta.smt.entity.ProductToolsInfo;

import java.util.ArrayList;
import java.util.List;

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
        return getService().getProductToolsInfoItem(pageSize,pageCurrent,condition).compose(RxsRxSchedulers.<JsonProductRequestToolsRoot>io_main());
    }
}
