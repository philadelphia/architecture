package com.delta.smt.ui.product_tools.back.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.JsonProductBackRoot;
import com.delta.smt.entity.JsonProductToolsLocation;
import com.delta.smt.entity.ProductToolsBack;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import rx.Observable;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public class ProduceToolsBackModel extends BaseModel<ApiService> implements ProduceToolsBackContract.Model{

    public ProduceToolsBackModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<JsonProductBackRoot> getProductToolsBack(String param) {
        //TODO ZSQgetProductToolsBack

        return getService().getProductToolsBack(param).compose(RxsRxSchedulers.<JsonProductBackRoot>io_main());
    }
}
